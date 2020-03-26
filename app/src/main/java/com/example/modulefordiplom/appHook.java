package com.example.modulefordiplom;

import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class appHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.example.diplompart2"))
            return;
        XposedBridge.log("Loaded app: " + lpparam.packageName);

        XposedHelpers.findAndHookMethod("com.example.diplompart2.analyze_fragments.dynamic_analyze.DynamicFragment",
                lpparam.classLoader,
                "hookOfApp", String.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("start hook" + " name of app: " + param.args[0] + " id app: " + param.args[1]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("start hook");
                    }
                });
    }
}
