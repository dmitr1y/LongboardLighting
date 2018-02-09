package ru.com.konstantinov.longboardlighting.Connector;

import android.bluetooth.BluetoothDevice;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for setup bluetooth connection
 */

public interface DeviceFinder {
    /**
     * Starts async device searching
     */
    void startDeviceSearching();

    /**
     * Stops device searching
     */
    void stopDeviceSearching();

    /**
     * Subscribes on device finding result
     * @param handler found device handler
     */
    void subscribeOnDeviceFinding(@NotNull FoundDeviceHandler handler);

    /**
     * Trying to establish connection with device
     * @param device device to connect to
     * @return true, if connected successfully, false otherwise
     */
    boolean ConnectToDevice(@NotNull BluetoothDevice device);
}
