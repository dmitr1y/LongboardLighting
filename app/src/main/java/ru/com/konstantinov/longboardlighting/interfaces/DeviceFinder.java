package ru.com.konstantinov.longboardlighting.interfaces;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import ru.com.konstantinov.longboardlighting.MainActivity;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for setup bluetooth connection
 */

public interface DeviceFinder {
    /**
     *
     * @return set of bonded devices
     */
    Set<BluetoothDevice> getBondedDevices();

    /**
     * Trying to establish connection with device
     * @param device device to connect to
     * @return true, if connected successfully, false otherwise
     */
    BluetoothSocket ConnectToDevice(@NotNull BluetoothDevice device);

    void onActivityDestroy(MainActivity activity);
}
