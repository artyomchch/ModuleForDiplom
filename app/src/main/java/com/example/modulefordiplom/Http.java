package com.example.modulefordiplom;

import android.util.Log;

import java.net.URL;
import java.net.URLStreamHandler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class Http implements IXposedHookLoadPackage {
    String packageName = null;
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);
//        XposedBridge.log("aaaaaa" + "  " +  ClassLoader.getSystemClassLoader() + lpparam.packageName  +"\n " +
//                "packagename: "+lpparam.getClass().getPackage()+" classname: "+lpparam.getClass().getName());



        packageName = lpparam.packageName;


        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[0]);

            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[1]);

            }

        });

//        XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader,
//                String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("Java.io.File: " + packageName + " Spec: " + param.args[0] + " " );
//            }
//        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[1]);

            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " File: " + param.args[2]);

            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);

            }

        });
        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);

            }

        });





    }




}
