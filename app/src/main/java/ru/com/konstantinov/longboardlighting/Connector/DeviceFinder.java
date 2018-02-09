package ru.com.konstantinov.longboardlighting.Connector;

import android.bluetooth.BluetoothDevice;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

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
    boolean ConnectToDevice(@NotNull BluetoothDevice device);
}
