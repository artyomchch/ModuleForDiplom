package com.example.modulefordiplom;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
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

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Test implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

       multuOther(lpparam);



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
                       // XposedBridge.log("Complete File");

                    }
                });
    }

    public void other(XC_LoadPackage.LoadPackageParam lpparam){
        //"java.security.MessageDigest"                  /////#1/////
        findAndHookConstructor(java.security.MessageDigest.class, String.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log(lpparam.packageName +" (constructor) javaSecurityMessageDigest: " + param.args[0]);
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

                    }
                });

//

        //	javax.crypto.Cipher  // secret key   /////#2/////
        findAndHookConstructor(SecretKeySpec.class, byte[].class, String.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log( lpparam.packageName +" SecretKey: "+ param.args[0] + ","+ "algorithm: " + param.args[1]);
            }

        });


        //	javax.crypto.Cipher  // get instance   // /////#2/////
        findAndHookMethod(Cipher.class, "getInstance", String.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                //Transformation ex AES/CBC/PKCS7Padding
               XposedBridge.log(lpparam.packageName + "  Cipher[" + (String) param.args[0] + "] ");
            }

        });


//	javax.crypto.Cipher  // init   // /////#2/////
        findAndHookMethod(Cipher.class, "init", int.class, Key.class, AlgorithmParameters.class, new XC_MethodHook() {

            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log(lpparam.packageName + " opmode: " + param.args[0] + "key: " + param.args[1]
                        + "params: " + param.args[2]);
            }

        });

    }
}
