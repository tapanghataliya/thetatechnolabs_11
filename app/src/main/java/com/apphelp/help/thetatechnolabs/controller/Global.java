package com.apphelp.help.thetatechnolabs.controller;

import android.content.Context;
import android.provider.Settings;

public class Global {

    public static final String PLAY_STORE = "https://play.google.com/store/apps/details?id=com.apphelp.help.thetatechnolabs&hl=en";

    public static final String BASE_URL = "https://reqres.in/api/";

    public static final String CURRENT_USER_LOGIN = "user_login";
    public static final String THETA_TECHNOLABS = "theta_technolabs";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS = "address";
    public static final String USER_PROFILE = "user_profile";

    public static final String L_EMAIL = "email";
    public static final String L_PASSWORD = "password";

    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "test123";
    public static String DeviceID;

    public static String getDeviceID(Context context) {

        DeviceID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return DeviceID;

    }
}
