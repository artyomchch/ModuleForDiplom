package com.example.modulefordiplom;

import android.util.Log;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;

public class SaveLogOfApp implements PropertyChangeListener {
    // Класс для работы потоком вывода из файла
    private FileInputStream inputStream;

    // Класс для работы потоком ввода в файл
    private FileOutputStream outputStream;

    // полный путь к файлу
    private String path;

    //запоминаюющая переменная
    private static String count;
    static int d = 0;

    //list
    ArrayList<String> listObject = new ArrayList<>();


    public SaveLogOfApp(String path) {
        this.path = path;
    }





    @Override
    public void propertyChange(PropertyChangeEvent event) {
        d++;
        String dub = "";
            XposedBridge.log("true");

        if (event.getPropertyName().equals("MyTextProperty")) {
            //System.out.println(event.getNewValue().toString());
            dub = event.getNewValue().toString();
            if (!dub.equals("true")) {

                if (listObject.size() == 0) {
                    listObject.add(dub);
                } else if (!dub.equals(listObject.get(listObject.size() - 1))) {
                    listObject.add(dub);
                }//   Log.d("loadhook", "propertyChange: " + count);
            }
            else {
                for (int i = 0; i < listObject.size(); i++){
                    count += listObject.get(i);
                    XposedBridge.log(listObject.get(i));
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
        outputStream = new FileOutputStream(path);
        // записываем данные в файл, но
        // пока еще данные не попадут в файл,
        // а просто будут в памяти
        outputStream.write(st.getBytes());
        XposedBridge.log("write");

        // только после закрытия потока записи,
        // данные попадают в файл
        outputStream.close();
    }
}
