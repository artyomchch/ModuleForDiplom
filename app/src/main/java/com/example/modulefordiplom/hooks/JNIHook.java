package com.example.modulefordiplom.hooks;

import android.util.Log;

import java.io.InputStream;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;



public class JNIHook implements IXposedHookLoadPackage {
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);


        if (!lpparam.packageName.equals("com.waze"))
            return;
        // different ways to specify the resources to be replaced
        System.load("/data/data/com.waze/lib/libwaze.so");
        XposedBridge.log("Loaded native hook");
        //  lpparam.res.setReplacement("com.waze", "integer", "Variabletomodify", 1);




//        final Class<?> ArgClass= XposedHelpers.findClass("io.rong.imlib.NativeObject$ConnectAckCallback", lpparam.classLoader);
//
//        findAndHookMethod(
//                "io.rong.imlib.NativeObject",
//                lpparam.classLoader,
//                "Connect",
//                String.class,
//                String.class,
//                int.class,
//                ArgClass,
//                Boolean.class,
//                new XC_MethodHook(){
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param)
//                            throws Throwable {
//                        XposedBridge.log("Quman " + param.getResult().toString() + param.args[0] );
//                    }
//                    protected void afterHookedMethod(MethodHookParam param)
//                            throws Throwable {
//                        XposedBridge.log("Quman " + param.getResult().toString() + param.args[0] );
//                    }
//                });




        XposedHelpers.findAndHookMethod("java.lang.Runtime", lpparam.classLoader,
                "loadLibrary", String.class, new XC_MethodHook() {
    
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        XposedBridge.log("LIB Loaded: " + param.args[0]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("LIB Loaded: " + param.args[0]);
                    }
                });




//        XpsoedHelpers.findAndHookMethod("java.lang.System", lpparam.classLoader, "loadLibrary", String.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param){
//                XposedBridge.log(" Loaded library: " + param.args[0]);
////                if (param.args[0].toString().equals("test")){
////                   // System.load("/data/data/package/modified_test.so");
////                }
//            }
//        });

    }


}