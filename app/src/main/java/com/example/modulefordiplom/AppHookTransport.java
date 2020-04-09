package com.example.modulefordiplom;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AppHookTransport implements PropertyChangeListener {
    private static String transport;

    public static String getTransport() {
        return transport;
    }



    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("MyTextProperty")) {
            transport = event.getNewValue().toString();
        }
    }
}
