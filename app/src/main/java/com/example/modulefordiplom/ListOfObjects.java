package com.example.modulefordiplom;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

public class ListOfObjects implements IXposedHookLoadPackage {
    String packageName = null;
    //  AppHook appHook =new AppHook();
    SaveLogOfApp saveLogOfApp = new SaveLogOfApp("/storage/emulated/0/Download/EdXposedManager/test.txt");
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //  XposedBridge.log("Loaded app: " + lpparam.packageName);
        packageName = lpparam.packageName;
        final String log = "some hook";

            //java.io.File
            XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader,
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("Java.io.File: " + packageName + " pathname: " + param.args[0]);
                            addListener(saveLogOfApp);
                            setVariable(param.args[0].toString() + "\n");
                        }
                    });

            //android.accounts.Account
            XposedHelpers.findAndHookConstructor("android.accounts.Account", lpparam.classLoader,
                    String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("android.accounts.Account: " + packageName + " name: " + param.args[0] +
                                    " type: " + param.args[1]);
                        }
                    });

            //android.service.voice.VoiceInteractionSession
            XposedHelpers.findAndHookConstructor("android.service.voice.VoiceInteractionSession", lpparam.classLoader,
                    Context.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("android.service.voice.VoiceInteractionSession: " + packageName + " context: " + param.args[0]);
                        }
                    });

            //android.telephony.PhoneStateListener
//            XposedHelpers.findAndHookConstructor("android.telephony.PhoneStateListener", lpparam.classLoader,
//                    Executor.class,  new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            XposedBridge.log("android.telephony.PhoneStateListener: " + packageName +
//                                    " executor: " + param.args[0]);
//                        }
//                    });

            //android.view.inputmethod.BaseInputConnection
            XposedHelpers.findAndHookConstructor("android.view.inputmethod.BaseInputConnection", lpparam.classLoader,
                    View.class, boolean.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("android.view.inputmethod.BaseInputConnection: "
                                    + packageName + " targetView: " + param.args[0] +
                                    " fullEditor: " + param.args[1]);
                        }
                    });

            XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                    new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log(packageName + " " + "work on stop");
                    addListener(saveLogOfApp);
                    setVariable("true");
                }
            });

            //java.net.URI
            XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("java.net.URI: "
                                    + packageName + " Constructs a URI by parsing the given string: " + param.args[0]);

                        }
                    });



            //java.net.URL
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
            findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    XposedBridge.log("URLSniffer: " + packageName + " Spec: " + param.args[0] + " " + param.args[1] + " " + param.args[2] + " "
                    );

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

            findAndHookMethod("android.net.wifi.WifiManager", lpparam.classLoader,
                    "setWifiEnabled", "boolean", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.args[0] = false;
                            XposedBridge.log("isWifiEnabled method will keep state as disabled..");
                        }
                    });


            findAndHookMethod("android.net.LinkAddress", lpparam.classLoader,
                    "getPrefixLength()", "int", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                            XposedBridge.log("URLSniffer: " + packageName + " IP address: " + param.args[0]);
                        }
                    });

            XposedHelpers.findAndHookMethod("java.net.InetAddress", lpparam.classLoader,
                    "getAllByName", String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("Quman " + param.getResult().toString() + param.args[0]);

                        }
                    });


            XposedHelpers.findAndHookMethod("NetworkInterface", lpparam.classLoader, "getHardwareAddress",
                    Byte.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.v("Quman", "：" + param.thisObject.toString() + " " + param.args[1]);
                            System.out.println("packagename: " + param.thisObject.getClass().getPackage() +
                                    " classname: " + param.thisObject.getClass().getName() + " method name: " + param.method.getName());
                        }
                    });






    }



    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("MyTextProperty", oldValue, newValue);
    }




}


