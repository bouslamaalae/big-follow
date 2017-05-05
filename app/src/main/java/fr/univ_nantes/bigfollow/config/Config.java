package fr.univ_nantes.bigfollow.config;

import android.util.SparseArray;

public class Config {
    public static String EXTRA_ID_PROJECT = "id_project";

    public static int TYPE_SHEET_PROJECT_ACTION_LIST = 1;
    public static int TYPE_SHEET_PLANNING = 2;
    public static int TYPE_SHEET_TRAINING_PLAN = 3;
    public static int TYPE_SHEET_TRAINING_INDICATORS = 4;
    public static int TYPE_SHEET_DC_AND_CONSUMPTION_DETAILS = 5;
    public static int TYPE_SHEET_PROJECT_MONITORING_AND_DOMAIN = 6;
    public static int TYPE_SHEET_INDICATORS = 7;
    public static int TYPE_SHEET_RESOURCES = 8;
    public static int TYPE_SHEET_TIME_ENTERED = 9;
    public static int TYPE_SHEET_MONTH_COURSE = 10;
    public static int TYPE_SHEET_INPUT_AND_CHARGE_INDICATORS = 11;
    public static int TYPE_SHEET_INPUT_AND_CHARGE_MEASURES = 12;
    public static int TYPE_SHEET_IMPORTANT_INFORMATION_AND_DECISIONS = 13;
    public static int TYPE_SHEET_GENERAL_INFORMATION = 14;
    public static int TYPE_SHEET_PROJECT_FUTURE = 15;

    public static String SHEET_PROJECT_ACTION_LIST = "Liste des actions projet";
    public static String SHEET_PLANNING = "Planning";
    public static String SHEET_TRAINING_PLAN = "Plan de formation";
    public static String SHEET_TRAINING_INDICATORS = "Indicateurs formation";
    public static String SHEET_DC_AND_CONSUMPTION_DETAILS = "DC et détails conso";
    public static String SHEET_PROJECT_MONITORING_AND_DOMAIN = "Suivi projet - Domaine";
    public static String SHEET_INDICATORS = "Indicateurs";
    public static String SHEET_RESOURCES = "Ressources";
    public static String SHEET_TIME_ENTERED = "Temps saisis";
    public static String SHEET_MONTH_COURSE = "Le Cours de Mois";
    public static String SHEET_INPUT_AND_CHARGE_INDICATORS = "Indicateurs de saisie/charge";
    public static String SHEET_INPUT_AND_CHARGE_MEASURES = "Mesures de saisie/charge";
    public static String SHEET_IMPORTANT_INFORMATION_AND_DECISIONS = "Informations / décisions importantes";
    public static String SHEET_GENERAL_INFORMATION = "Informations générales";
    public static String SHEET_PROJECT_FUTURE = "Suite du projet";

    public static final SparseArray<String> SHEETS = new SparseArray<String>() {{
        put(TYPE_SHEET_PROJECT_ACTION_LIST, SHEET_PROJECT_ACTION_LIST);
        put(TYPE_SHEET_PLANNING, SHEET_PLANNING);
        put(TYPE_SHEET_TRAINING_PLAN, SHEET_TRAINING_PLAN);
        put(TYPE_SHEET_TRAINING_INDICATORS, SHEET_TRAINING_INDICATORS);
        put(TYPE_SHEET_DC_AND_CONSUMPTION_DETAILS, SHEET_DC_AND_CONSUMPTION_DETAILS);
        put(TYPE_SHEET_PROJECT_MONITORING_AND_DOMAIN, SHEET_PROJECT_MONITORING_AND_DOMAIN);
        put(TYPE_SHEET_INDICATORS, SHEET_INDICATORS);
        put(TYPE_SHEET_RESOURCES, SHEET_RESOURCES);
        put(TYPE_SHEET_TIME_ENTERED, SHEET_TIME_ENTERED);
        put(TYPE_SHEET_MONTH_COURSE, SHEET_MONTH_COURSE);
        put(TYPE_SHEET_INPUT_AND_CHARGE_INDICATORS, SHEET_INPUT_AND_CHARGE_INDICATORS);
        put(TYPE_SHEET_INPUT_AND_CHARGE_MEASURES, SHEET_INPUT_AND_CHARGE_MEASURES);
        put(TYPE_SHEET_IMPORTANT_INFORMATION_AND_DECISIONS, SHEET_IMPORTANT_INFORMATION_AND_DECISIONS);
        put(TYPE_SHEET_GENERAL_INFORMATION, SHEET_GENERAL_INFORMATION);
        put(TYPE_SHEET_PROJECT_FUTURE, SHEET_PROJECT_FUTURE);
    }};
}
