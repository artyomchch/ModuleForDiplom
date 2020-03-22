package com.example.modulefordiplom;


import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class TestHookMethod implements IXposedHookLoadPackage {

    /**
     * The following can be found by decompiling an apk into java
     *   OR
     * Decompile an apk into smali code
     * Note: Not all method names found in decompiled java can be hooked.
     *       Use: "XposedHelpers.findClass(classToHook, lpparam.classLoader).getDeclaredMethods();"
     *         to find all methods that can be hooked.
     */
    final String packageName = "com.android.chrome";


    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable{
        if (lpparam.packageName.equals(packageName)) return;
      //  XposedBridge.log("Quman " + XposedHelpers.findClass(classToHook, lpparam.classLoader).getDeclaredMethods().toString());
//        XposedHelpers.findClass("java.net.Inet4Address", lpparam.classLoader).getDeclaredMethods();
//        XposedHelpers.findClass("ssssss" +"java.net.NetworkInterface", lpparam.classLoader).getDeclaredMethods();
//        XposedHelpers.findClass("ssssss" +"java.lang.reflect.Method", lpparam.classLoader).getDeclaredMethods();
 //         XposedBridge.log("Quman " + XposedHelpers.findClass("java.net.Inet4Address", lpparam.classLoader).getDeclaredMethods());
   //       XposedBridge.log("Quman " + XposedHelpers.findClass("java.net.NetworkInterface", lpparam.classLoader).getDeclaredMethods());
  //        XposedBridge.log("Quman " + XposedHelpers.findClass("java.lang.reflect.Method", lpparam.classLoader).getDeclaredMethods());

      //   Parameters below are full class names of parameter types of the method that is being hooked
        // The five class parameters below are examples, do not use them
        XposedHelpers.findAndHookMethod("java.net.InetAddress", lpparam.classLoader,
                "getAllByName", String.class ,new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("Quman " + param.getResult().toString() + param.args[0] );

                    }
                });




        XposedHelpers.findAndHookMethod("NetworkInterface", lpparam.classLoader, "getHardwareAddress",
                Byte.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v("Quman", "启动程序：" + param.thisObject.toString() + " " + param.args[1]);
                System.out.println("packagename: "+param.thisObject.getClass().getPackage()+
                        " classname: "+param.thisObject.getClass().getName()+" method name: "+param.method.getName());
            }
        });





    }
}