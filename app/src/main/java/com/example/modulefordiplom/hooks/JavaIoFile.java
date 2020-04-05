package com.example.modulefordiplom.hooks;

import android.app.Activity;

import com.example.modulefordiplom.save_logs.SaveLogOfAppFile;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class JavaIoFile implements IXposedHookLoadPackage {
    private String packageName = null;
    private SaveLogOfAppFile saveLogOfAppFile =
            new SaveLogOfAppFile("/storage/emulated/0/Download/EdXposedManager/HookFolder/javaIoFile.txt");
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //  XposedBridge.log("Loaded app: " + lpparam.packageName);
        packageName = lpparam.packageName;

            //java.io.File
            XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader,
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("Java.io.File: " + packageName + " pathname: " + param.args[0]);
                            addListener(saveLogOfAppFile);
                            setVariable(param.args[0].toString() + "\n");
                        }
                    });

        XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log(packageName + " " + "work on stop File");
                        addListener(saveLogOfAppFile);
                        setVariable("true");
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


