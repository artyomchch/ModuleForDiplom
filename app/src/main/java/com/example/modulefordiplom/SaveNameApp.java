package com.example.modulefordiplom;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static android.content.Context.MODE_PRIVATE;


public class SaveNameApp {
    // Класс для работы потоком вывода из файла
    private FileInputStream inputStream;

    // Класс для работы потоком ввода в файл
    private FileOutputStream outputStream;
    // полный путь к файлу
    private String path;

    public SaveNameApp(String path) {
        this.path = path;
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

    public String read() throws IOException {

        // инициализируем поток вывода из файлу
        inputStream = new FileInputStream(path);
        String charToString = "";

        // читаем первый символ с потока байтов
        int data = inputStream.read();
        char content;

        // если data будет равна 0 то это значит,
        // что файл пуст
        while(data != -1) {
            // переводим байты в символ
            content = (char) data;
            String str2 = String.valueOf(content);
            charToString += str2;
            // выводим полученный символ
            System.out.print(content);

            // читаем следующий байты символа
            data = inputStream.read();
        }

        // закрываем поток чтения файла
        inputStream.close();

        return charToString;
    }

}
