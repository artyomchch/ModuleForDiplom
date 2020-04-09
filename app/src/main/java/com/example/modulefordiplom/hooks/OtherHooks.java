package com.example.modulefordiplom.hooks;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.modulefordiplom.save_logs.SaveLogOfAppOther;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Executor;

import de.robv.android.xposed.IXposedHookInitPackageResources;
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

public class OtherHooks implements IXposedHookLoadPackage {
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    //String
    private String packageName = null;
    //Saver
    private SaveLogOfAppOther saveLogOfAppOther = new SaveLogOfAppOther();

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        packageName = lpparam.packageName;


       // rx(lpparam);


        // android.accounts.Account               /////#1/////
        XposedHelpers.findAndHookConstructor("android.accounts.Account", lpparam.classLoader,
                String.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("android.accounts.Account: " + packageName + " name: " + param.args[0] +
                                " type: " + param.args[1]);
                        addListener(saveLogOfAppOther);
                        setVariable("1name: " + param.args[0] + " type: " + param.args[1]);
                    }
                });
        // android.service.voice.VoiceInteractionSession              /////#2/////
        XposedHelpers.findAndHookConstructor("android.service.voice.VoiceInteractionSession", lpparam.classLoader,
                Context.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("android.service.voice.VoiceInteractionSession: " + packageName + " context: " + param.args[0]);
                        addListener(saveLogOfAppOther);
                        setVariable("2context: " + param.args[0]);
                    }
                });

        //  android.telephony.PhoneStateListener              /////#3/////
        XposedHelpers.findAndHookConstructor("android.telephony.PhoneStateListener", lpparam.classLoader,
                Executor.class,  new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("android.telephony.PhoneStateListener: " + packageName +
                                " executor: " + param.args[0]);
                        addListener(saveLogOfAppOther);
                        setVariable("3executor: " + param.args[0]);
                    }
                });

        //android.view.inputmethod.BaseInputConnection             /////#4/////
        XposedHelpers.findAndHookConstructor("android.view.inputmethod.BaseInputConnection",
                lpparam.classLoader,
                View.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("android.view.inputmethod.BaseInputConnection: "
                                + packageName + " targetView: " + param.args[0] +
                                " fullEditor: " + param.args[1]);
                        addListener(saveLogOfAppOther);
                        setVariable("4targetView: " + param.args[0] + " fullEditor: " + param.args[1]);
                    }
                });

        //"java.lang.reflect.Method"                  /////#5/////
        XposedHelpers.findAndHookMethod("java.lang.reflect.Method", lpparam.classLoader,
                "getName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("java.lang.reflect.Method: " + packageName +
                                " Method: " + param.args[0]);
                        addListener(saveLogOfAppOther);
                        setVariable("5Method: " + param.args[0]);
                    }
                });



    }

    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("otherHook", oldValue, newValue);
    }

    private void otherHook (XC_LoadPackage.LoadPackageParam lpparam){
        // method Stop
        XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("work on stop other hook " );
                        addListener(saveLogOfAppOther);
                        setVariable("true");
                    }
                });



    }

    public void rx(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> otherHook(lpparam))
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
                        //  XposedBridge.log("Complete research");

                    }
                });
    }
}
