package com.example.modulefordiplom.hooks;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.modulefordiplom.save_logs.SaveLogsOfApp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.concurrent.Executor;

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
                        XposedBridge.log("work on stop hook " );
                        addListener(saveLogsOfApp);
                        setVariable("true");
                    }
                });

    ///////////////////////////////////////////java.io.File/////////////////////////////////////////
        multuFile(lpparam);
    ///////////////////////////////////////////java.net.uri/////////////////////////////////////////
        multuUri(lpparam);
    ///////////////////////////////////////////other classes////////////////////////////////////////
        multuOther(lpparam);
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
                .subscribeOn(Schedulers.io())
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
                        addListener(saveLogsOfApp);
                        setVariable("f"+param.args[0]);
                    }
                });
    }

    private void multuUri(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> uri(lpparam))
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

    private void uri(XC_LoadPackage.LoadPackageParam lpparam){
        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("java.net.URI: "
//                                + lpparam.packageName + " Constructs a URI by parsing the given string: " + param.args[0]);
                        long millis = new Date().getTime();
                        addListener(saveLogsOfApp);
                        setVariable("u" + millis + " " + param.args[0]);
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

    private void other(XC_LoadPackage.LoadPackageParam lpparam){
    //     android.accounts.Account               /////#1/////
        XposedHelpers.findAndHookConstructor("android.accounts.Account", lpparam.classLoader,
                String.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("android.accounts.Account: " + lpparam.packageName + " name: " + param.args[0] +
//                                " type: " + param.args[1]);
                        setVariable("1name: " + param.args[0] + " type: " + param.args[1]);
                    }
                });
        // android.service.voice.VoiceInteractionSession              /////#2/////
        XposedHelpers.findAndHookConstructor("android.service.voice.VoiceInteractionSession", lpparam.classLoader,
                Context.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                      //  XposedBridge.log("android.service.voice.VoiceInteractionSession: " + lpparam.packageName + " context: " + param.args[0]);
                        setVariable("2context: " + param.args[0]);
                    }
                });

        //  android.telephony.PhoneStateListener              /////#3/////
        XposedHelpers.findAndHookConstructor("android.telephony.PhoneStateListener", lpparam.classLoader,
                Executor.class,  new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("android.telephony.PhoneStateListener: " + lpparam.packageName +
//                                " executor: " + param.args[0]);
                        setVariable("3executor: " + param.args[0]);
                    }
                });

        //android.view.inputmethod.BaseInputConnection             /////#4/////
        XposedHelpers.findAndHookConstructor("android.view.inputmethod.BaseInputConnection",
                lpparam.classLoader,
                View.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("android.view.inputmethod.BaseInputConnection: "
//                                + lpparam.packageName + " targetView: " + param.args[0] +
//                                " fullEditor: " + param.args[1]);
                        setVariable("4targetView: " + param.args[0] + " fullEditor: " + param.args[1]);
                    }
                });

        //"java.lang.reflect.Method"                  /////#5/////
        XposedHelpers.findAndHookMethod("java.lang.reflect.Method", lpparam.classLoader,
                "getName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        XposedBridge.log("java.lang.reflect.Method: " + lpparam.packageName +
//                                " Method: " + param.args[0]);
                        setVariable("5Method: " + param.args[0]);
                    }
                });
    }
}
