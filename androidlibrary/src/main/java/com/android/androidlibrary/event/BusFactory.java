package com.android.androidlibrary.event;


public class BusFactory {
    private static IBus bus;

    public static IBus getBus() {
        if (bus == null) {
            synchronized (BusFactory.class) {
                if (bus == null) {
                    bus = new EventBusImpl();
                }
            }
        }
        return bus;
    }
}
