package ru.com.konstantinov.longboardlighting.Connector;

import android.bluetooth.BluetoothDevice;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for subscription on device finding
 */

public interface FoundDeviceHandler {
    void onDeviceFound(BluetoothDevice device);
}
