package ru.com.konstantinov.longboardlighting.Connector;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;

import org.jetbrains.annotations.NotNull;

import ru.com.konstantinov.longboardlighting.LedMode;

/**
 * Created by ceyler on 09.02.2018.
 * Basic connector to communicate with controller
 */

public class Connector implements ConnectionInterface {

    public Connector(@NotNull BluetoothDevice connectedDevice) {

    }

    @Override
    public void setMode(LedMode mode) {

    }

    @Override
    public void setBrightness(int value) {

    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public float getVoltage() {
        return 0;
    }
}
