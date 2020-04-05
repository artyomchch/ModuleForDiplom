package com.example.modulefordiplom.hooks;

import android.app.Activity;

import com.example.modulefordiplom.save_logs.SaveLogOfAppFile;
import com.example.modulefordiplom.save_logs.SaveLogOfAppURL;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URLStreamHandler;
import java.util.Date;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class URL_Hook implements IXposedHookLoadPackage {
    SaveLogOfAppURL saveLogOfAppURL =
            new SaveLogOfAppURL("/storage/emulated/0/Download/EdXposedManager/HookFolder/URL.txt");
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    String packageName = null;

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        packageName = lpparam.packageName;

        XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("java.net.URI: "
                                + packageName + " Constructs a URI by parsing the given string: " + param.args[0]);
                        addListener(saveLogOfAppURL);
                        long millis = new Date().getTime();
                        setVariable(millis + " " + param.args[0].toString() + "\n");
                    }
                });

        XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log(packageName + " " + "work on stop URL");
                        addListener(saveLogOfAppURL);
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
        support.firePropertyChange("saveDataURL", oldValue, newValue);
    }

}
