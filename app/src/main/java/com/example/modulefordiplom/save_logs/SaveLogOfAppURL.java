package com.example.modulefordiplom.save_logs;

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

public class SaveLogOfAppURL implements PropertyChangeListener {

    // полный путь к файлу
    private String path;

    //запоминаюющая переменная
    private String count = "";
    static int d = 0;
    //запоминающий счетчик
    private static int c = 0;

    public ArrayList<String> getListObject() {
        return listObject;
    }

    //list
    private ArrayList<String> listObject = new ArrayList<>();


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
                        XposedBridge.log("app --> " +  count + "listObject: " + listObject.size());
                        multithreading();

                        c++;
            }

        }
    }

    public void write(String st) throws IOException {
        XposedBridge.log("listObject: " + listObject.size() + "count: " + c);


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

    public void multithreading(){


        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .doOnNext(integer -> write(count))
                .observeOn(Schedulers.newThread())
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
                        XposedBridge.log("Complete URL");

                    }
                });

    }
}
