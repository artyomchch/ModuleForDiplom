package com.example.modulefordiplom;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.modulefordiplom.hooks.JNIHook;
import com.example.modulefordiplom.hooks.JavaIoFileHook;
import com.example.modulefordiplom.hooks.OtherHooks;
import com.example.modulefordiplom.hooks.URLHook;
import com.example.modulefordiplom.save_logs.SaveLogOfAppFile;
import com.example.modulefordiplom.save_logs.SaveLogOfAppOther;
import com.example.modulefordiplom.save_logs.SaveLogOfAppURL;
import com.example.modulefordiplom.save_logs.SaveNameApp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

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
    private AppHookTransport appHookTransport = new AppHookTransport();

    private SaveNameApp saveText = new SaveNameApp("/storage/emulated/0/Download/EdXposedManager/appText.txt");
    //Hooks
    private URLHook urlHook = new URLHook();
    private JavaIoFileHook javaIoFileHook = new JavaIoFileHook();
    private OtherHooks otherHooks = new OtherHooks();
    private JNIHook jni = new JNIHook();
    //save hooks




    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.equals("com.example.diplompart2")) {
            XposedBridge.log("Loaded app: " + lpparam.packageName);

            XposedHelpers.findAndHookMethod("com.example.diplompart2.analyze_fragments.dynamic_analyze.DynamicFragment",
                    lpparam.classLoader,
                    "hookOfApp", String.class, int.class, new XC_MethodHook() {
                        @Override
                        public void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            XposedBridge.log("start hook" + " name of app: " + param.args[0] + " id app: " + param.args[1]);
                            nameApp = param.args[0].toString();
                            addListener(appHookTransport);
                            setVariable(param.args[0].toString());



                        }

                        @Override
                        public void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            saveText.write(nameApp);


                        }
                    });



        }

            //XposedBridge.log("wanna see argument " + AppHookTransport.getTransport());

       research(lpparam);



    }


    public void research(XC_LoadPackage.LoadPackageParam lpparam){
        try {
            if (lpparam.packageName.equals(saveText.read())
                    || lpparam.packageName.equals("com.example.diplompart2")) {
                XposedBridge.log("Next Stage: " + lpparam.packageName + " " + Thread.currentThread().getName());

                try {
                    urlHook.handleLoadPackage(lpparam);
                   // urlHook.handleLoadPackage2(lpparam);
                  //  javaIoFileHook.handleLoadPackage(lpparam);
                 //   otherHooks.handleLoadPackage(lpparam);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }




            }
        } catch (IOException e) {
            e.printStackTrace();
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


    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("appHook", oldValue, newValue);
    }


}
