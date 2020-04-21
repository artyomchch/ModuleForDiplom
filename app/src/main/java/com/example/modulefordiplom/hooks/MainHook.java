package com.example.modulefordiplom.hooks;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.CancellationSignal;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;

import com.example.modulefordiplom.save_logs.SaveLogsOfApp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLStreamHandler;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedBridge.hookAllMethods;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findMethodBestMatch;

public class MainHook implements IXposedHookLoadPackage {
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    //Logger
    SaveLogsOfApp saveLogsOfApp = new SaveLogsOfApp();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        // method Stop
        XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("work on destroy hook " );
                      //  addListener(saveLogsOfApp);
                       // setVariable("true");
                         saveLogsOfApp.multithreading();
                    }
                });

    ///////////////////////////////////////////java.io.File/////////////////////////////////////////
        multuFile(lpparam);
    ///////////////////////////////////////////java.net.uri/////////////////////////////////////////
        multuUri(lpparam);
    ///////////////////////////////////////////other classes////////////////////////////////////////
        multuOther(lpparam);
     ///////////////////////////////////////////other classes 2/////////////////////////////////////
        multuOther2(lpparam);
    ///////////////////////////////////////////java net url/////////////////////////////////////////
        multuURL(lpparam);
    ///////////////////////////////////////////java net socket//////////////////////////////////////
        multuSocket(lpparam);
     //////////////////////////////////////////stop logger//////////////////////////////////////////

    }


    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("onStop", oldValue, newValue);
    }

    private void multuFile(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> file(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                       //XposedBridge.log("Complete File");

                    }
                });
    }

    private void file(XC_LoadPackage.LoadPackageParam lpparam){
        XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader,
                String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        //  XposedBridge.log("Java.io.File: " + lpparam.packageName + " pathname: " + param.args[0]);
                       // addListener(saveLogsOfApp);
                        SaveLogsOfApp.setAllMethodsOfHooks("f"+param.args[0]);
                       // setVariable();
                    }
                });


        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, String.class, String.class, int.class,
                String.class, String.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        long millis = new Date().getTime();
                       // addListener(saveLogsOfApp);
                        SaveLogsOfApp.setAllMethodsOfHooks("u" + millis + " Scheme: "+ param.args[0] + " userInfo: " + param.args[1] +
                                " host: " + param.args[2] + " port: " + param.args[3] + " path: " + param.args[4] +
                                " query: " + param.args[5] + " fragment: " + param.args[6]);
                        XposedBridge.log("u" + millis + " Scheme: "+ param.args[0] + " userInfo: " + param.args[1] +
                                " host: " + param.args[2] + " port: " + param.args[3] + " path: " + param.args[4] +
                                " query: " + param.args[5] + " fragment: " + param.args[6]);
                    }
                });

        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, String.class, String.class,
                String.class, String.class,  new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                       // addListener(saveLogsOfApp);
                        SaveLogsOfApp.setAllMethodsOfHooks("u" + millis + " Scheme: "+ param.args[0] + " userInfo: " + param.args[1] +
                                " host: " + param.args[2] + " port: " + param.args[3] + " path: " + param.args[4] +
                                " query: " + param.args[5] + " fragment: " + param.args[6]);
                        XposedBridge.log("u" + millis + " Scheme: "+ param.args[0] + " authority: " + param.args[1] +
                                " path: " + param.args[2] + " query: " + param.args[3] + " fragment: " + param.args[4]);
                    }
                });


        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, String.class, String.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                      //  addListener(saveLogsOfApp);
                        SaveLogsOfApp.setAllMethodsOfHooks("u" + millis + " Scheme: "+ param.args[0] + " userInfo: " + param.args[1] +
                                " host: " + param.args[2] + " port: " + param.args[3] + " path: " + param.args[4] +
                                " query: " + param.args[5] + " fragment: " + param.args[6]);
                        XposedBridge.log("u" + millis + " Scheme: "+ param.args[0] + " host: " + param.args[1] +
                                " path: " + param.args[2] + " fragment: " + param.args[3]);
                    }
                });

        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, String.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        //addListener(saveLogsOfApp);
                        SaveLogsOfApp.setAllMethodsOfHooks("u" + millis + " Scheme: "+ param.args[0] + " userInfo: " + param.args[1] +
                                " host: " + param.args[2] + " port: " + param.args[3] + " path: " + param.args[4] +
                                " query: " + param.args[5] + " fragment: " + param.args[6]);
                        XposedBridge.log("u" + millis + " Scheme: "+ param.args[0] + " ssp: " + param.args[1] +
                                " fragment: " + param.args[2] );
                    }
                });




    }

    private void multuUri(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> uri(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        XposedBridge.log("onNext"   +integer.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        XposedBridge.log("Complete File");

                    }
                });
    }

    private void uri(XC_LoadPackage.LoadPackageParam lpparam){
        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("java.net.URI: "
//                                + lpparam.packageName + " Constructs a URI by parsing the given string: " + param.args[0]);
                        long millis = new Date().getTime();
                        //addListener(saveLogsOfApp);
                        //setVariable();
                        SaveLogsOfApp.setAllMethodsOfHooks("u" + millis + " " + param.args[0]);
                    }
                });


        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log( "l" + millis +" Spec: " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis +" Spec: " + param.args[0]);

            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log(  "l" + millis +" Spec: " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis +" Spec: " + param.args[0]);
            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, URL.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log("lSpec: " + param.args[1]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis +" Spec: " + param.args[1]);
            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class,
                String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log("URLSniffer: "  + " Protocol: " + param.args[0] + " Host: "
                        + param.args[1] + " File: " + param.args[2]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis +" Protocol: " + param.args[0] +
                        " Host: " + param.args[1] + " File: " + param.args[2]);
            }

        });

        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class,
                String.class, int.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log("URLSniffer: "  + " Protocol: " + param.args[0] +
                        " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2])
                        + " File: " + param.args[3]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis + " Protocol: " + param.args[0] +
                        " Host: " + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2])
                        + " File: " + param.args[3]);

            }

        });
        findAndHookConstructor("java.net.URL", lpparam.classLoader, String.class,
                String.class, int.class, String.class, URLStreamHandler.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                XposedBridge.log("URLSniffer: "  + " Protocol: " + param.args[0] + " Host: "
                        + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) +
                        " File: " + param.args[3]);
                SaveLogsOfApp.setAllMethodsOfHooks("l" + millis +" Protocol: " + param.args[0] + " Host: "
                        + param.args[1] + " Port: " + Integer.toString((Integer) param.args[2]) +
                        " File: " + param.args[3]);

            }

        });


    }

    private void multuOther(XC_LoadPackage.LoadPackageParam lpparam) {
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> other(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        XposedBridge.log("Complete File");

                    }
                });
    }

    private void other(XC_LoadPackage.LoadPackageParam lpparam) {
        //"java.security.MessageDigest"                  /////#1/////
        findAndHookConstructor(java.security.MessageDigest.class, String.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log(lpparam.packageName + " (constructor) javaSecurityMessageDigest: " + param.args[0]);
               // setVariable("1Constructor: " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("1Constructor: " + param.args[0]);
            }

        });

        //"java.security.MessageDigest"                  /////#1/////
        XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader,
                "getInstance", String.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log(lpparam.packageName + " java.security.MessageDigest: " +
                                " algorithm: " + param.args[0] + "provider: " + param.args[1]);
                        //setVariable("1algorithm: " + param.args[0] + "provider: " + param.args[1]);
                        SaveLogsOfApp.setAllMethodsOfHooks("1algorithm: " + param.args[0] + "provider: " + param.args[1]);
                    }
                });

//

        //	javax.crypto.Cipher  // secret key   /////#2/////
        findAndHookConstructor(SecretKeySpec.class, byte[].class, String.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log(lpparam.packageName + " SecretKey: " + param.args[0] + ","
                        + "algorithm: " + param.args[1]);

              //  setVariable("2Constructor: " + " SecretKey: " + param.args[0] + ","
              //          + "algorithm: " + param.args[1]);
                SaveLogsOfApp.setAllMethodsOfHooks("2Constructor: " + " SecretKey: " + param.args[0] + " ,"
                                 + "algorithm: " + param.args[1]);
            }

        });


        //	javax.crypto.Cipher  // get instance   // /////#2/////
        findAndHookMethod(Cipher.class, "getInstance", String.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //Transformation ex AES/CBC/PKCS7Padding
                XposedBridge.log(lpparam.packageName + "  Cipher[" + (String) param.args[0] + "] ");
             //   setVariable("2Cipher[" + (String) param.args[0] + "] ");
                SaveLogsOfApp.setAllMethodsOfHooks("2Cipher[" + (String) param.args[0] + "] ");
            }

        });


//	javax.crypto.Cipher  // init   // /////#2/////
        findAndHookMethod(Cipher.class, "init", int.class, Key.class, AlgorithmParameters.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log(lpparam.packageName + " opmode: " + param.args[0] + "key: " + param.args[1]
                        + "params: " + param.args[2]);
               // setVariable("2opmode: " + param.args[0] + "key: " + param.args[1]
                      //  + "params: " + param.args[2]);
                SaveLogsOfApp.setAllMethodsOfHooks("2opmode: " + param.args[0] + "key: " + param.args[1]
                          + "params: " + param.args[2]);
            }

        });

//	java.lang.System  // loadLibrary // /////#3/////
        findAndHookMethod(System.class, "loadLibrary", String.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log(lpparam.packageName + " libname: " + param.args[0]);
               // setVariable("3libname: " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("3libname: " + param.args[0]);
            }


        });

	//java.lang.System  // loadLibrary // /////#3/////
        findAndHookMethod("java.lang.System", lpparam.classLoader, "loadLibrary", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ", lpparam.packageName + " java.lang.System" + "loadLibrary()" +
                        "trying to load shared library \"" + param.args[0] );
                SaveLogsOfApp.setAllMethodsOfHooks("3libname: " + param.args[0]);
            }
        });
        //	java.lang.System  // loadLibrary // /////#3/////
        findAndHookMethod("java.lang.System", lpparam.classLoader, "load", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ", lpparam.packageName + "java.lang.System" + "loadLibrary()"
                        + "trying to load shared library \"" + param.args[0] + "\"");
//                setVariable("3loadLibrary()"
//                        + "trying to load shared library " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("3load"
                        + "trying to load shared library " + param.args[0]);
            }
        });
        //	java.lang.System  // loadLibrary // /////#3/////
        findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "loadLibrary", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ", "beforeHookedMethod: " + lpparam.packageName + "java.lang.Runtime"+
                        "loadLibrary()" + "trying to load shared library \"" + param.args[0] + "\"");
             //   setVariable("3loadLibrary()"
             //           + "trying to load shared library " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("3loadLibrary"
                        + "trying to load shared library " + param.args[0]);
            }
        });
        //	java.lang.System  // loadLibrary // /////#3/////
        findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "load", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ", "beforeHookedMethod: " + lpparam + "java.lang.Runtime" + "load()" +
                        "trying to load shared library at \"" + param.args[0] + "\"");
              //  setVariable("3load()"
                //        + "trying to load shared library " + param.args[0]);
                SaveLogsOfApp.setAllMethodsOfHooks("3load"
                        + "trying to load shared library " + param.args[0]);
            }
        });


        // android.hardware.Camera             //////#4//////
        final Class<?> classFinder = findClass("android.hardware.Camera", lpparam.classLoader);
        hookAllConstructors(classFinder, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log(" trying to access camera.");
              //  setVariable("4trying to access camera");
                SaveLogsOfApp.setAllMethodsOfHooks("4trying to access camera");
            }
        });

        // android.telephony.SmsManager ///// #5////////
        findAndHookMethod("android.telephony.SmsManager", lpparam.classLoader, "sendTextMessage", String.class,
                String.class, String.class,
                PendingIntent.class, PendingIntent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("I/EdXposed-Bridge:", lpparam.packageName + "android.telephony.SmsManager" + "sendTextMessage()" +
                                "To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);
                      //  setVariable("5android.telephony.SmsManager" + " sendTextMessage()" +
                           //     " To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);
                        SaveLogsOfApp.setAllMethodsOfHooks("5android.telephony.SmsManager" + " sendTextMessage()" +
                                " To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);

                    }
                });

        // android.telephony.SmsManager ///// #5////////
        findAndHookMethod("android.telephony.SmsManager", lpparam.classLoader, "getDefault", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: lpparam, " + " android.telephony.SmsManager" + " getDefault()" +
                        " trying to get SMS instance");
           //     setVariable("5android.telephony.SmsManager" + " android.telephony.SmsManager" + " getDefault()" +
                    //    " trying to get SMS instance");
                SaveLogsOfApp.setAllMethodsOfHooks("5android.telephony.SmsManager" + " android.telephony.SmsManager" + " getDefault()" +
                        " trying to get SMS instance");
            }
        });


        // android.telephony.SmsManager ///// #5////////
        findAndHookMethod("android.telephony.gsm.SmsManager", lpparam.classLoader, "sendTextMessage", String.class,
                String.class, String.class,
                PendingIntent.class, PendingIntent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(TAG, "beforeHookedMethod: lpparam, " + "android.telephony.gsm.SmsManager" +
                                "sendTextMessage()" +
                                "To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);
                    //    setVariable("5android.telephony.SmsManager" + "sendTextMessage()" +
                       //         "To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);
                        SaveLogsOfApp.setAllMethodsOfHooks("5android.telephony.SmsManager" + "sendTextMessage()" +
                                "To: " + param.args[0] + ", From: " + param.args[1] + "Text:" + param.args[2]);
                    }
                });


    }

    private void javaNetServerSocket(XC_LoadPackage.LoadPackageParam lpparam){
        Log.d("I/EdXposed-Bridge", "beforeHookedMethod: work!!!!!1");
        findAndHookConstructor("java.net.ServerSocket", lpparam.classLoader, Integer.TYPE, Integer.TYPE, InetAddress.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                Log.d( "I/EdXposed-Bridge", " port:" + param.args[0] + " backlog:" + param.args[1]
                        + " localAddress:" + param.args[2]);
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " port:" + param.args[0] + " backlog:" + param.args[1]
                        + " localAddress:" + param.args[2]);

            }
        });

        findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "connect",
                SocketAddress.class, Integer.TYPE, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        Log.d( "I/EdXposed-Bridge"," connect " + "addr:" + param.args[0] +
                                " timeout:" + param.args[1]);
                        SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " connect " + "addr:" + param.args[0] +
                                " timeout:" + param.args[1]);
                    }
                });
        Log.d("I/EdXposed-Bridge", "beforeHookedMethod: work!!!!!2");
        findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "close",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                        Log.d( "I/EdXposed-Bridge"," close "+  "addr:" + thisObj.getRemoteSocketAddress());
                        SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " close "+
                                "addr:" + thisObj.getRemoteSocketAddress());
                    }
                });

        findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "isConnected",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        Log.d( "I/EdXposed-Bridge"," isConnected " + param.getResult());
                        SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " isConnected " + param.getResult());
                    }
                });

        findAndHookMethod("java.net.ServerSocket", lpparam.classLoader,
                "getInputStream",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                        Log.d( "I/EdXposed-Bridge"," getInputStream " + "addr:" +
                                thisObj.getRemoteSocketAddress());
                        SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " getInputStream "
                                + "addr:" + thisObj.getRemoteSocketAddress());
                    }
                });

        Log.d("I/EdXposed-Bridge", "beforeHookedMethod: work!!!!!6");
        findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "getOutputStream",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        long millis = new Date().getTime();
                        java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

                        Log.d( "I/EdXposed-Bridge"," getOutputStream" +" addr:" +
                                thisObj.getRemoteSocketAddress());
                        SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " getOutputStream" +" addr:" +
                                thisObj.getRemoteSocketAddress());
                    }
                });



    }

    private void javaNetUrl(XC_LoadPackage.LoadPackageParam lpparam){
        findAndHookMethod("java.net.Socket", lpparam.classLoader, "startupSocket", InetAddress.class, Integer.TYPE, InetAddress.class, Integer.TYPE, Boolean.TYPE, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // java.net.Socket thisObj = ((java.net.Socket)
                // param.thisObject);
                long millis = new Date().getTime();
                Log.d("I/EdXposed-Bridge", " startupSocket" + "rAddr:" + param.args[0] + " rPort:" + param.args[1] + "lAddr:" + param.args[2] + " lPort:" + param.args[3] + " streaming:" + param.args[4]);
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " startupSocket" + "rAddr:" + param.args[0]
                        + " rPort:" + param.args[1] + "lAddr:" + param.args[2] + " lPort:" + param.args[3]
                        + " streaming:" + param.args[4]);
            }
        });

        findAndHookMethod("java.net.Socket", lpparam.classLoader, "connect", SocketAddress.class, Integer.TYPE, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                long millis = new Date().getTime();
                Log.d("I/EdXposed-Bridge", " connect " + "addr:" + param.args[0] +
                        " timeout:" + param.args[1]);
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " connect " + "addr:" + param.args[0] +
                        " timeout:" + param.args[1]);
            }
        });

        findAndHookMethod("java.net.Socket", lpparam.classLoader, "close", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                Log.d("I/EdXposed-Bridge ", " close "+ "addr: " + thisObj.getRemoteSocketAddress());
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " close "+ "addr: " +
                        thisObj.getRemoteSocketAddress());
            }
        });

        findAndHookMethod("java.net.Socket", lpparam.classLoader, "isConnected", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                Log.d("I/EdXposed-Bridge " , " isConnected" + " " + param.getResult());
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " isConnected" + " " + param.getResult());
            }
        });

        findAndHookMethod("java.net.Socket", lpparam.classLoader, "getInputStream", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                long millis = new Date().getTime();
                Log.d("I/EdXposed-Bridge ", " getInputStream " + "addr: " +
                        thisObj.getRemoteSocketAddress());
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " getOutputStream" +" addr:" +
                        thisObj.getRemoteSocketAddress());
            }
        });

        findAndHookMethod("java.net.Socket", lpparam.classLoader, "getOutputStream", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                long millis = new Date().getTime();
                java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
                Log.d("I/EdXposed-Bridge ", "getOutputStream "+ "addr:" +
                        thisObj.getRemoteSocketAddress());
                SaveLogsOfApp.setAllMethodsOfHooks("s"+ millis + " getOutputStream" +" addr:" +
                        thisObj.getRemoteSocketAddress());
            }
        });
    }

    private void multuURL(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> javaNetUrl(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        XposedBridge.log("Complete File");

                    }
                });
    }

    private void multuSocket(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> javaNetServerSocket(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        XposedBridge.log("Complete File");

                    }
                });
    }

    private void other2(XC_LoadPackage.LoadPackageParam lpparam){

        /////////calendar///////// #6
        final Class<?> classFinder = findClass("android.content.ContentResolver", lpparam.classLoader);
        XposedBridge.hookMethod(findMethodBestMatch(classFinder,"query", Uri.class,
                String[].class, String.class, String[].class,
                String.class, CancellationSignal.class ),
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Object uri = param.args[0];
                        if(uri.equals(CalendarContract.Calendars.CONTENT_URI))
                            Log.d("I/EdXposed-Bridge ", "ContentResolver " + "query()" + " "+uri);
                            SaveLogsOfApp.setAllMethodsOfHooks("6"+"ContentResolver " + "query()" + " "+uri);

                    }
                });

        /////////gpshook///////// #7
        final Class<?> classFinder1 = findClass("android.location.LocationManager", lpparam.classLoader);

        hookAllMethods(classFinder1,"getLastLocation", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
               Log.d("I/EdXposed-Bridge ", "LocationManager" + "getLastKnownLocation()" + " ");
                SaveLogsOfApp.setAllMethodsOfHooks("7"+"LocationManager" + "getLastKnownLocation()" );
            }
        });


        hookAllMethods(classFinder1,"requestLocationUpdates", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ", "LocationManager" + "requestLocationUpdates()");
                SaveLogsOfApp.setAllMethodsOfHooks("7"+"LocationManager"  + "requestLocationUpdates()" );
            }
        });

        ////context///////// #8
        final String targetClass = "android.content.Context";
        final String classShorthand = "content.Context";
        final String methodConstructor = "Constructor";
        final String methodGetSystemService = "getSystemService";
        final String methodGetSharedPreferences = "getSharedPreferences";
        final String methodOpenFileOutput = "openFileOutput";
        final String methodGetConnectivityService = "CONNECTIVITY_SERVICE";
        final Class<?> classFinderContext = findClass(targetClass, lpparam.classLoader);
        //Constructors
        hookAllConstructors(classFinderContext, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("I/EdXposed-Bridge ",lpparam +" "+ targetClass+" " + methodConstructor+" " +
                        classShorthand + " object initialized. "+
                                "appInfo = "+lpparam.appInfo);
            }
        });

        findAndHookMethod(targetClass, lpparam.classLoader,
                methodGetConnectivityService, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

                       Log.d("I/EdXposed-Bridge ",lpparam+" "+ targetClass+" "+
                               methodGetSystemService +" "+ "HELLO WORLD!!!");
                    }
                });

        //Context.getSystemService(String)
        findAndHookMethod(targetClass, lpparam.classLoader,
                methodGetSystemService, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

                        Log.d("I/EdXposed-Bridge ",lpparam+" "+targetClass+" "+
                                        methodGetSystemService+" "+sb.toString() + " Result Class Name = "
                                + param.getResult().getClass().getName());
                    }
                });

        //Context.getSharedPreferences(String, int)
        findAndHookMethod(targetClass, lpparam.classLoader,
                methodGetSharedPreferences, String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

                        Log.d("I/EdXposed-Bridge ",lpparam+" "+ targetClass+" "+ methodGetSharedPreferences+" "+
                                sb.toString() + " Result Class Name = " + param.getResult().getClass().getName());
                    }
                });

        //Context.openFileOutput(String, int)
        findAndHookMethod(targetClass, lpparam.classLoader,
                methodOpenFileOutput, String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

                        Log.d("I/EdXposed-Bridge ", lpparam+" "+targetClass+" "+
                                        methodGetSharedPreferences+" "+
                                sb.toString() + " Result Class Name = " + param.getResult().getClass().getName());
                    }
                });

    }

    private void multuOther2(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer -> other2(lpparam))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        XposedBridge.log("Complete File");

                    }
                });
    }

}
