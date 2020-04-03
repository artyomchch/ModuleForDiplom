package com.example.modulefordiplom;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Strings {
    static String app;

    public static String getApp() {
        return app;
    }

    public static void setApp(String app) {
        Strings.app = app;
    }





}
