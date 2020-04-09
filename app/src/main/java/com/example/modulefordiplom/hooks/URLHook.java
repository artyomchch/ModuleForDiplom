package com.example.modulefordiplom.hooks;

import android.app.Activity;
import android.app.AndroidAppHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modulefordiplom.save_logs.SaveLogOfAppFile;
import com.example.modulefordiplom.save_logs.SaveLogOfAppURL;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.Date;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.services.BaseService;
import de.robv.android.xposed.services.FileResult;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class  URLHook  implements IXposedHookLoadPackage  {
    private SaveLogOfAppURL saveLogOfAppURL =
            new SaveLogOfAppURL("/storage/emulated/0/Download/EdXposedManager/HookFolder/URL.txt");
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String packageName = null;

    private static ArrayList<String> appHolder = new ArrayList<String>();

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        packageName = lpparam.packageName;

        addListener(saveLogOfAppURL);
        if (lpparam.packageName.equals("com.example.diplompart2")){
            XposedHelpers.findAndHookMethod("com.example.diplompart2.analyze_fragments.dynamic_analyze.DynamicFragment",
                    lpparam.classLoader,
                    "stopAppHook", new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //    super.afterHookedMethod(param);

                            setVariable("true");
                            XposedBridge.log("well done");
                            XposedBridge.log("count of: "+ String.valueOf(appHolder.size()));

                        }

                    });
        }

        else {
             XposedHelpers.findAndHookConstructor("java.net.URI", lpparam.classLoader,
                     String.class, new XC_MethodHook() {
                         @Override
                         protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                             XposedBridge.log("java.net.URI: "
                                     + packageName + " Constructs a URI by parsing the given string: " + param.args[0]);

                             long millis = new Date().getTime();
                             setVariable(millis + " " + param.args[0].toString() + "\n");
                             appHolder = saveLogOfAppURL.getListObject();
                             XposedBridge.log("count of: "+ String.valueOf(appHolder.size()));

                            // super.afterHookedMethod(param);
                         }

                     });




        }


    }

    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("saveDataURL", oldValue, newValue);
    }




    private void url(XC_LoadPackage.LoadPackageParam lpparam){


        XposedHelpers.findAndHookMethod(Activity.class,"onStop",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log(packageName + " " + "work on stop URL");
                        addListener(saveLogOfAppURL);
                        setVariable("true");

                    }
                });
    }

    public void rx(XC_LoadPackage.LoadPackageParam lpparam){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> url(lpparam))
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
                        //  XposedBridge.log("Complete research");

                    }
                });
    }

}
