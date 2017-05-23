# Big Follow

Projet de développement d'une **application Android**, dans le cadre du module de **Processus de développement** en **Master 2 MIAGE** parcours ISI.

**Groupe A** composé de :
- Boisteux Denis
- Bouslama Alae-Eddine
- Megnic Cedric
- Stiti Celia

## Installation de l'environnement de développement

Télécharger [Android Studio].

Depuis Android Studio installer Android SDK Platform 4.4 (KitKat).

Et installer les SDK Tools :
- Android SDK Platforms-Tools
- Android SDK Tools 25.1.0
- Google Play services

## Installation de l'APK

### Pré-requis

Disposer d'un terminal Android doté au minimum de la **version 4.4 (KitKat)** d'Android, dont le mode développeur est activé.

### Installation depuis Android Studio

Brancher le terminal Android via un câble USB à l'ordinateur.

Cloner le projet, et l'ouvrir dans Android Studio.

Appuyer sur le bouton **Run** pour le lancer sur le terminal Android.

### Installation à l'aide de l'APK

Placer l'APK de l'application dans un répertoire du terminal Android.

A l'aide d'un explorateur de fichier sur le terminal, cliquer sur le fichier de l'APK pour l'installer.

### Génération de l'APK signée

Rendez vous à la racine du projet, il existe deux fichiers qui permettent de construire l'application à l'aide de Gradle, `gradlew` pour Linux, et `gradlew.bat` pour Windows.  

> **Note :** Pour pouvoir utiliser ces fichiers vous devez au préalable avoir  accepté les conditions d'utilisation de la licence du Android SDK utilisé pour le build Gradle.

Pour démarrer le build, lancez dans votre terminal :
```bash
> ./gradlew assembleRelease
```
Vous trouverez le fichier APK venant d'être créé dans le répertoire `/app/build/outputs/apk` du projet.  

Puis utilisez l'outil [zipalign], qui se trouve dans le répertoire de votre Android SDK, `/build-tools/[versionAndroidSDKTools]/zipalign` pour aligner l'APK,
```bash
> ./zipalign -v -p 4 [cheminDuProjet]/app/build/outputs/apk/app-release-unsigned.apk [cheminDuProjet]/app/build/outputs/apk/app-release-unsigned-aligned.apk
```

Ensuite signez l'APK à l'aide de l'outil [apksigner] qui se trouve dans le même répertoire que [zipalign] en précisant le chemin du keystore contenant la clé privée qui servira à signer l'application,
```bash
> ./apksigner sign --ks [cheminDuProjet]/app/keystores/bigfollow.keystore --out [cheminDuProjet]/app/build/outputs/apk/app-release.apk [cheminDuProjet]/app/build/outputs/apk/app-release-unsigned-aligned.apk
```
Enfin vérifiez que l'APK a bien été signée,
```bash
> ./apksigner verify [cheminDuProjet]/app/build/outputs/apk/app-release.apk  
```
Une fois signée, votre APK est prête à être installée sur un terminal Android disposant de la version du SDK minimum requise.  

*Pour plus d'infos* :  
- [Android Studio - Build an unsigned APK and sign it manually]

###Déployer l'APK sur le Google Play Store

Vous avez besoin de vous [inscrire] sur le Google Play Store en tant que développeur.


[Android Studio]: <https://developer.android.com/studio/index.html>
[Android Studio - Build an unsigned APK and sign it manually]: <https://developer.android.com/studio/publish/app-signing.html#sign-manually>
[zipalign]: <https://developer.android.com/studio/command-line/zipalign.html>
[apksigner]: <https://developer.android.com/studio/command-line/apksigner.html>
[inscrire]: https://play.google.com/apps/publish/signup/
