package fr.univ_nantes.bigfollow.app;

import com.activeandroid.ActiveAndroid;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
