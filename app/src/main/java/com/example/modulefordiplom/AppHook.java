package com.example.modulefordiplom;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.modulefordiplom.hooks.JNIHook;
import com.example.modulefordiplom.hooks.JavaIoFileHook;
import com.example.modulefordiplom.hooks.OtherHooks;
import com.example.modulefordiplom.hooks.URLHook;
import com.example.modulefordiplom.save_logs.SaveNameApp;

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

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

public class AppHook implements IXposedHookLoadPackage {

    private static   String nameApp = "s!";



    private SaveNameApp saveText = new SaveNameApp("/storage/emulated/0/Download/EdXposedManager/appText.txt");
    private URLHook urlHook = new URLHook();
    private JavaIoFileHook javaIoFileHook = new JavaIoFileHook();
    private OtherHooks otherHooks = new OtherHooks();

    JNIHook jni = new JNIHook();
    //private Strings strings = new Strings();


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
                        //    strings.setApp(param.args[0].toString());
                             nameApp = param.args[0].toString();
                        }

                        @Override
                        public void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                        //    XposedBridge.log(strings.getApp());
                            saveText.write(nameApp);
                        }
                    });

        }

        if (lpparam.packageName.equals(saveText.read())){
            XposedBridge.log("Next Stage: " + lpparam.packageName + " " + Thread.currentThread().getName());

          //  urlHook.handleLoadPackage(lpparam);
          //  javaIoFileHook.handleLoadPackage(lpparam);
          //  otherHooks.handleLoadPackage(lpparam);

            Observable.just(1)
                    .subscribeOn(Schedulers.io())
                    .doOnNext(integer -> file(lpparam))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.newThread())
                    .doOnNext(integer -> url(lpparam))
                    .observeOn(Schedulers.newThread())
                    .subscribeOn(Schedulers.computation())
                    .doOnNext(integer -> other(lpparam))
                    .observeOn(Schedulers.computation())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Integer integer) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {


                        }
                    });


        }



    }









   public void file (XC_LoadPackage.LoadPackageParam lpparam){
       XposedBridge.log("Name of thread: " + Thread.currentThread().getName());
       try {
           javaIoFileHook.handleLoadPackage(lpparam);
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }

   }

    public void url (XC_LoadPackage.LoadPackageParam lpparam){
        XposedBridge.log("Name of thread: " + Thread.currentThread().getName());
        try {
            urlHook.handleLoadPackage(lpparam);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public void other (XC_LoadPackage.LoadPackageParam lpparam){
        XposedBridge.log("Name of thread: " + Thread.currentThread().getName());
        try {
            otherHooks.handleLoadPackage(lpparam);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }


}
