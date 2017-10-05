package com.example.user.ictakapp;

import android.app.Application;

/**
 * Created by Ociuz on 7/11/2017.
 */
public class MyApplication extends Application {
    public static String wrkid,chatid;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}
