package com.example.modulefordiplom.save_logs;

import com.example.modulefordiplom.Strings;
import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;

public class SaveLogOfAppOther implements PropertyChangeListener {

    private ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5



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
                try {
                    write(makeJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private String makeJson(){
        Gson gson = new Gson();
        Strings strings = new Strings(androidAccountsAccount, androidServiceVoiceVoiceInteractionSession,
                androidTelephonyPhoneStateListener, androidViewInputmethodBaseInputConnection,
                javaLangReflectMethod);
        return gson.toJson(strings);
    }


    public void write(String st) throws IOException {

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
    }

}
