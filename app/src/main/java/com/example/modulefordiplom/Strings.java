package com.example.modulefordiplom;

import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Strings {
    private ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5

    public Strings(ArrayList<String> androidAccountsAccount,
                   ArrayList<String> androidServiceVoiceVoiceInteractionSession,
                   ArrayList<String> androidTelephonyPhoneStateListener,
                   ArrayList<String> androidViewInputmethodBaseInputConnection,
                   ArrayList<String> javaLangReflectMethod) {
        this.androidAccountsAccount = androidAccountsAccount;
        this.androidServiceVoiceVoiceInteractionSession = androidServiceVoiceVoiceInteractionSession;
        this.androidTelephonyPhoneStateListener = androidTelephonyPhoneStateListener;
        this.androidViewInputmethodBaseInputConnection = androidViewInputmethodBaseInputConnection;
        this.javaLangReflectMethod = javaLangReflectMethod;
    }
}
