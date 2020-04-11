package com.example.modulefordiplom.save_logs;

import android.graphics.Paint;
import android.util.Log;

import com.example.modulefordiplom.Strings;
import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class SaveLogsOfApp implements PropertyChangeListener {
    private static ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private static ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private static ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private static ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private static ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5
    private static ArrayList<String> javaIoFile = new ArrayList<>(); //#f
    private static ArrayList<String> javaNetUri = new ArrayList<>(); //#u

    private static ArrayList<String> allMethodsOfHooks = new ArrayList<>();

    static String  getReload = "";
    private static int c = 0;


    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String getInfo = "";
        if (event.getPropertyName().equals("onStop")){
            getInfo = event.getNewValue().toString();
            if (!(getInfo.equals("true")|| getInfo.equals(""))) {
               if (!getInfo.equals(getReload)){
                   getReload = getInfo;
                   allMethodsOfHooks.add(getInfo);
                   Log.d("I/EdXposed-Bridge:",   " count: " +String.valueOf(allMethodsOfHooks.size()));
               }
            }
            else {
             //   XposedBridge.log(String.valueOf(javaNetUri.size()));
              //  XposedBridge.log("get get get get get get get get get get");
                if (c == 0) {
                   multithreading();
                    c++;
                }
            }
        }
    }

    private String makeJson(){
//        String param = "";
//        for (int i = 0; i < allMethodsOfHooks.size(); i++){
//            StringBuilder sb = new StringBuilder(allMethodsOfHooks.get(i));
//            sb.deleteCharAt(0);
//            char firstIndex = allMethodsOfHooks.get(i).charAt(0);
//            String mainIndex = String.valueOf(firstIndex);
//            param = sb.toString();
//          //  Log.d("I/EdXposed-Bridge:", param);
//            //Log.d("I/EdXposed-Bridge:", String.valueOf(firstIndex));
//
//            if (mainIndex.equals("1")) {
//                androidAccountsAccount.add(param);
//            } else if (mainIndex.equals("2")) {
//                androidServiceVoiceVoiceInteractionSession.add(param);
//            } else if (mainIndex.equals("3")) {
//                androidTelephonyPhoneStateListener.add(param);
//            } else if (mainIndex.equals("4")) {
//                androidViewInputmethodBaseInputConnection.add(param);
//            } else if (mainIndex.equals("5")) {
//                javaLangReflectMethod.add(param);
//            } else if (mainIndex.equals("f")) {
//                javaIoFile.add(param);
//            } else if (mainIndex.equals("u")) {
//                Log.d("I/EdXposed-Bridge:", javaNetUri.get(5));
//                javaNetUri.add(param);
//            }
//        }
//        Log.d("I/EdXposed-Bridge:", String.valueOf(javaNetUri.size()));
//        Log.d("I/EdXposed-Bridge:", String.valueOf(javaIoFile.size()));

        Gson gson = new Gson();
        Strings strings = new Strings(allMethodsOfHooks);
        return gson.toJson(strings);
    }


    private void write(String st) throws IOException {

        // открываем поток ввода в файл
        // Класс для работы потоком ввода в файл
        FileOutputStream outputStreamFile = new
                FileOutputStream("/storage/emulated/0/Download/EdXposedManager/HookFolder/hookOfApp.txt");
        // записываем данные в файл, но
        // пока еще данные не попадут в файл,
        // а просто будут в памяти
        outputStreamFile.write(st.getBytes());
        XposedBridge.log("write File All Hook");

        // только после закрытия потока записи,
        // данные попадают в файл
        outputStreamFile.close();
    }

    private void multithreading(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> write(makeJson()))
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


}
