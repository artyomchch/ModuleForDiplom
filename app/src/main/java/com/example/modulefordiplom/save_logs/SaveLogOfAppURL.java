package com.example.modulefordiplom.save_logs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;

public class SaveLogOfAppURL implements PropertyChangeListener {

    // полный путь к файлу
    private String path;

    //запоминаюющая переменная
    private static String count;
    static int d = 0;

    //list
    private static ArrayList<String> listObject = new ArrayList<>();


    public SaveLogOfAppURL(String path) {
        this.path = path;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String dub = "";

        if (event.getPropertyName().equals("saveDataURL")) {

            dub = event.getNewValue().toString();
            if (!dub.equals("true")) {

                if (listObject.size() == 0) {
                    listObject.add(dub);
                } else if (!dub.equals(listObject.get(listObject.size() - 1))) {
                    listObject.add(dub);
                }
            }
            else {

                for (int i = 0; i < listObject.size(); i++) {
                    count += listObject.get(i);
                }
                try {
                    write(count);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    public void write(String st) throws IOException {

        // открываем поток ввода в файл
        // Класс для работы потоком ввода в файл
        FileOutputStream outputStream = new FileOutputStream(path);
        // записываем данные в файл, но
        // пока еще данные не попадут в файл,
        // а просто будут в памяти
        outputStream.write(st.getBytes());
        XposedBridge.log("write URL");

        // только после закрытия потока записи,
        // данные попадают в файл
        outputStream.close();
    }


}