package com.example.modulefordiplom.save_logs;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SaveLogOfAppOther implements PropertyChangeListener {

    private static ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private static ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private static ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private static ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private static ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5

    //запоминающий счетчик
    private static int c = 0;

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String getInfo = "";

        if (event.getPropertyName().equals("otherHook")){
            getInfo = event.getNewValue().toString();
            if (!getInfo.equals("true")){
                StringBuilder sb = new StringBuilder(getInfo);
                sb.deleteCharAt(0);
                char firstIndex = getInfo.charAt(0);
                getInfo = sb.toString();
                if (firstIndex == 1){
                    androidAccountsAccount.add(getInfo);
                }
                else if (firstIndex == 2){
                    androidServiceVoiceVoiceInteractionSession.add(getInfo);
                }
                else if (firstIndex == 3){
                    androidTelephonyPhoneStateListener.add(getInfo);
                }
                else if (firstIndex == 4){
                    androidViewInputmethodBaseInputConnection.add(getInfo);
                }
                else if (firstIndex == 5){
                    javaLangReflectMethod.add(getInfo);
                }
            }
            else {
                multithreading();
            }
        }
    }




//    private String makeJson(){
//        c++;
//        Gson gson = new Gson();
////        Strings strings = new Strings(androidAccountsAccount, androidServiceVoiceVoiceInteractionSession,
////                androidTelephonyPhoneStateListener, androidViewInputmethodBaseInputConnection,
////                javaLangReflectMethod);
//       // return gson.toJson(strings);
//    }


    public void write(String st) throws IOException {
        if (c == 1) {


            // открываем поток ввода в файл
            // Класс для работы потоком ввода в файл
            FileOutputStream outputStreamFile = new
                    FileOutputStream("/storage/emulated/0/Download/EdXposedManager/HookFolder/otherHook.txt");
            // записываем данные в файл, но
            // пока еще данные не попадут в файл,
            // а просто будут в памяти
            outputStreamFile.write(st.getBytes());
            XposedBridge.log("write File Other Hook");

            // только после закрытия потока записи,
            // данные попадают в файл
            outputStreamFile.close();

//            FileOutputStream outputStreamFileApp = new
//                    FileOutputStream("/storage/emulated/0/Download/EdXposedManager/appText.txt");
//            // записываем данные в файл, но
//            // пока еще данные не попадут в файл,
//            // а просто будут в памяти
//            outputStreamFileApp.write("true".getBytes());
//            XposedBridge.log("write File Other Hook");
//
//            // только после закрытия потока записи,
//            // данные попадают в файл
//            outputStreamFileApp.close();




        }

    }

    public void multithreading() {


//        Observable.just(1)
//                .subscribeOn(Schedulers.newThread())
//                .doOnNext(integer -> write(makeJson()))
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        XposedBridge.log(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        XposedBridge.log("Complete URL");
//
//                    }
//                });
//
//    }
    }
}
