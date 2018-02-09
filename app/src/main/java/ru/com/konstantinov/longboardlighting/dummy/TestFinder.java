package ru.com.konstantinov.longboardlighting.dummy;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ru.com.konstantinov.longboardlighting.Connector.DeviceFinder;
import ru.com.konstantinov.longboardlighting.Connector.FoundDeviceHandler;

/**
 * Created by ceyler on 09.02.2018.
 * Device finder for testing
 */

public class TestFinder implements DeviceFinder {
    private List<FoundDeviceHandler> subscribers = new ArrayList<>();
    private final String tag = "TestSearcher";

    @Override
    public void startDeviceSearching() {
        for(FoundDeviceHandler handler : subscribers){
            handler.onDeviceFound(null); // can't create device, sorry
        }
    }

    @Override
    public void stopDeviceSearching() {
        Log.i(this.tag, "Ok, ok, I stopped already, calm down, please");
    }

    @Override
    public void subscribeOnDeviceFinding(@NotNull FoundDeviceHandler handler) {
        subscribers.add(handler);
    }

    @Override
    public boolean ConnectToDevice(@NotNull BluetoothDevice device) {
        Log.i(this.tag, "Let's say, I connected");
        return true;
    }
}
