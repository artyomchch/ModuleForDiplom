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




//        XposedHelpers.findAndHookMethod("NetworkInterface", lpparam.classLoader, "getHardwareAddress",
//                Byte.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.v("Quman", "启动程序：" + param.thisObject.toString() + " " + param.args[1]);
//                System.out.println("packagename: "+param.thisObject.getClass().getPackage()+
//                        " classname: "+param.thisObject.getClass().getName()+" method name: "+param.method.getName());
//            }
//        });

//            //android.accounts.Account
//            XposedHelpers.findAndHookConstructor("android.accounts.Account", lpparam.classLoader,
//                    String.class, String.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("android.accounts.Account: " + packageName + " name: " + param.args[0] +
//                                    " type: " + param.args[1]);
//                        }
//                    });
//
//            //android.service.voice.VoiceInteractionSession
//            XposedHelpers.findAndHookConstructor("android.service.voice.VoiceInteractionSession", lpparam.classLoader,
//                    Context.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("android.service.voice.VoiceInteractionSession: " + packageName + " context: " + param.args[0]);
//                        }
//                    });

        //android.telephony.PhoneStateListener
//            XposedHelpers.findAndHookConstructor("android.telephony.PhoneStateListener", lpparam.classLoader,
//                    Executor.class,  new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("android.telephony.PhoneStateListener: " + packageName +
//                                    " executor: " + param.args[0]);
//                        }
//                    });

//            //android.view.inputmethod.BaseInputConnection
//            XposedHelpers.findAndHookConstructor("android.view.inputmethod.BaseInputConnection", lpparam.classLoader,
//                    View.class, boolean.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("android.view.inputmethod.BaseInputConnection: "
//                                    + packageName + " targetView: " + param.args[0] +
//                                    " fullEditor: " + param.args[1]);
//                        }
//                    });


//            //java.net.URI
//            XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
//                    String.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("java.net.URI: "
//                                    + packageName + " Constructs a URI by parsing the given string: " + param.args[0]);
//
//                        }
//                    });
//
//
//
//            //java.net.URL
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[0]);
//
//                }
//
//            });
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[1]);
//
//                }
//
//            });
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[0] + " " + param.args[1] + " " + param.args[2] + " "
//                    );
//
//                }
//            });
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " File: " + param.args[2]);
//
//                }
//
//            });
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);
//
//                }
//
//            });
//            findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);
//
//                }
//
//            });
//
//            findAndHookMethod("android.net.wifi.WifiManager", lpparam.classLoader,
//                    "setWifiEnabled", "boolean", new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            param.args[0] = false;
//                            XposedBridge.log("isWifiEnabled method will keep state as disabled..");
//                        }
//                    });
//
//
//            findAndHookMethod("android.net.LinkAddress", lpparam.classLoader,
//                    "getPrefixLength()", "int", new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//
//                            XposedBridge.log("URLSniffer: " + packageName + " IP address: " + param.args[0]);
//                        }
//                    });
//
//            XposedHelpers.findAndHookMethod("java.net.InetAddress", lpparam.classLoader,
//                    "getAllByName", String.class, new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            XposedBridge.log("Quman " + param.getResult().toString() + param.args[0]);
//
//                        }
//                    });
//
//
//            XposedHelpers.findAndHookMethod("NetworkInterface", lpparam.classLoader, "getHardwareAddress",
//                    Byte.class, new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            Log.v("Quman", "：" + param.thisObject.toString() + " " + param.args[1]);
//                            System.out.println("packagename: " + param.thisObject.getClass().getPackage() +
//                                    " classname: " + param.thisObject.getClass().getName() + " method name: " + param.method.getName());
//                        }
//                    });
//



        ///////////////////////////////URL////////////////////////////////////////
        //        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[0]);
//
//
//            }
//
//        });
//
//        findAndHookConstructor("java.net.URL", lpparam.classLoader, java.net.URL.class,
//                String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[1]);
//
//            }
//
//        });
//
////        XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader,
////                String.class, new XC_MethodHook() {
////            @Override
////            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                XposedBridge.log("Java.io.File: " + packageName + " Spec: " + param.args[0] + " " );
////            }
////        });
//
//
//
//        findAndHookConstructor("java.net.URL", lpparam.classLoader, java.net.URL.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[1]);
//
//            }
//
//        });
//
//        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " File: " + param.args[2]);
//
//            }
//
//        });
//
//        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);
//
//            }
//
//        });
//        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, String.class, int.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                XposedBridge.log("URLSniffer: " + packageName + " Protocol: " + param.args[0] + " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) + " File: " + param.args[3]);
//
//            }
//
//        });


        ////////////////////////////in the main////////////////////
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