package com.example.modulefordiplom.save_logs;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.modulefordiplom.R;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.NOTIFICATION_SERVICE;

public class SaveLogOfAppFile implements PropertyChangeListener {

    // полный путь к файлу
    private String path;

    //запоминаюющая переменная
    private static String count;
    //запоминающий счетчик
    private static int c = 0;

    //list
   private static ArrayList<String> listObject = new ArrayList<>();


    public SaveLogOfAppFile(String path) {
        this.path = path;
    }





    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String dub = "";
            //XposedBridge.log("true");

        if (event.getPropertyName().equals("MyTextProperty")) {
            //System.out.println(event.getNewValue().toString());
            dub = event.getNewValue().toString();
            if (!dub.equals("true")) {

                if (listObject.size() == 0) {
                    listObject.add(dub);
                } else if (!dub.equals(listObject.get(listObject.size() - 1))) {
                    listObject.add(dub);
                }
            }
            else {
                for (int i = 0; i < listObject.size(); i++){
                    count += listObject.get(i);
                  //  XposedBridge.log(listObject.get(i));
                }
                c++;
                if (c == 1) {
                    //  write(count);
                    multithreading();
                }

            }

        }

    }


    public void multithreading(){


        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> write(count))
                .observeOn(Schedulers.io())
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


    public void write(String st) throws IOException {


            // открываем поток ввода в файл
            // Класс для работы потоком ввода в файл
            FileOutputStream outputStreamFile = new FileOutputStream(path);
            // записываем данные в файл, но
            // пока еще данные не попадут в файл,
            // а просто будут в памяти
            outputStreamFile.write(st.getBytes());
            XposedBridge.log("write File " + Thread.currentThread().getName());

            // только после закрытия потока записи,
            // данные попадают в файл
            outputStreamFile.close();

//        FileOutputStream outputStreamFileApp = new
//                FileOutputStream("/storage/emulated/0/Download/EdXposedManager/appText.txt");
//        // записываем данные в файл, но
//        // пока еще данные не попадут в файл,
//        // а просто будут в памяти
//        outputStreamFileApp.write("true".getBytes());
//        XposedBridge.log("write File Other Hook");
//
//        // только после закрытия потока записи,
//        // данные попадают в файл
//        outputStreamFileApp.close();




    }



}
