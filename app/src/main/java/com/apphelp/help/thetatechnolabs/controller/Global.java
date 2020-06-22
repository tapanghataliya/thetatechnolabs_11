package com.apphelp.help.thetatechnolabs.controller;

import android.content.Context;
import android.provider.Settings;

public class Global {

    public static final String PLAY_STORE = "https://play.google.com/store/apps/details?id=com.apphelp.help.thetatechnolabs&hl=en";

    public static final String BASE_URL = "https://reqres.in/api/";

    public static final String CURRENT_USER_LOGIN = "user_login";
    public static final String HELPMATE = "HELPMATE";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String USER_NAME = "user_name";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS = "address";
    public static String DeviceID;

    public static String getDeviceID(Context context) {

        DeviceID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return DeviceID;

    }
}
