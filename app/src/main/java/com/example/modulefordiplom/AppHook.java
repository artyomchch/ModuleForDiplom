package com.example.modulefordiplom;

import android.app.Activity;
import android.app.AndroidAppHelper;
import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

public class AppHook implements IXposedHookLoadPackage {

    private static   String nameApp = "shit!!!";



    SaveNameApp saveText = new SaveNameApp("/storage/emulated/0/Download/EdXposedManager/appText.txt");
    Http http = new Http();
    JNIHook jni = new JNIHook();
    ListOfObjects listOfObjects = new ListOfObjects();
    Strings strings = new Strings();
    Context context = (Context) AndroidAppHelper.currentApplication();


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.example.diplompart2")){
            XposedBridge.log("Loaded app: " + lpparam.packageName);

            XposedHelpers.findAndHookMethod("com.example.diplompart2.analyze_fragments.dynamic_analyze.DynamicFragment",
                    lpparam.classLoader,
                    "hookOfApp", String.class, int.class, new XC_MethodHook() {
                        @Override
                        public void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("start hook" + " name of app: " + param.args[0] + " id app: " + param.args[1]);
                            strings.setApp(param.args[0].toString());
                             nameApp = param.args[0].toString();
                        }

                        @Override
                        public void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log(strings.getApp());
                            saveText.write(nameApp);
                        }
                    });

        }

        if (lpparam.packageName.equals(saveText.read())){
            XposedBridge.log("Next Stage: " + lpparam.packageName);
            http.handleLoadPackage(lpparam);
            listOfObjects.handleLoadPackage(lpparam);



        }





        //XposedBridge.log("Loaded app 2: " + lpparam.packageName + " " + saveText.openText(););




        //Метод информирования об остановки жизненого цикла Activity (onDestroy)
//        XposedHelpers.findAndHookMethod(Activity.class, "onStart", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log( lpparam.packageName + " " +"work on start");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                //   XposedBridge.log("work on stop");
//            }
//        });
//
//
//        XposedHelpers.findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log(lpparam.packageName + " " + "work on onResume");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                //   XposedBridge.log("work on stop");
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(Activity.class, "onPause", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log(lpparam.packageName + " " + "work on onPause");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                //   XposedBridge.log("work on stop");
//            }
//        });



//        XposedHelpers.findAndHookMethod(Activity.class, "onDestroy", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                XposedBridge.log( lpparam.packageName + " " + "work on destroy");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                //   XposedBridge.log("work on stop");
//            }
//        });


    }




}
