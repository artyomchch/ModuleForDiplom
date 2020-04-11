package com.example.modulefordiplom;

import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Strings {
    private ArrayList<String> allMethodsOfHooks = new ArrayList<>();   //#1

    public Strings(ArrayList<String> allMethodsOfHooks) {
        this.allMethodsOfHooks = allMethodsOfHooks;
    }
}
