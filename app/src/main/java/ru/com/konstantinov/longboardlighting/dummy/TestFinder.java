package ru.com.konstantinov.longboardlighting.dummy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import ru.com.konstantinov.longboardlighting.MainActivity;
import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;

/**
 * Created by ceyler on 09.02.2018.
 * Device finder for testing
 */

public class TestFinder implements DeviceFinder {
    @Override
    public Set<BluetoothDevice> getBondedDevices() {
        return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
    }

    @Override
    public BluetoothSocket ConnectToDevice(@NotNull BluetoothDevice device) {
        Log.i("TestSearcher", "Let's say, I connected to " + device.getName());
        return null;
    }

    @Override
    public void onActivityDestroy(MainActivity activity) {
        Log.i("TestSearcher", "x_x");

    }
}
