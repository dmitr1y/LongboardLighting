package ru.com.konstantinov.longboardlighting.connector;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.com.konstantinov.longboardlighting.LedMode;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ConnectionInterface;

/**
 * Created by ceyler on 09.02.2018.
 * Basic connector to communicate with controller
 */

public class Connector implements ConnectionInterface {

    private final Object syncObject = new Object();
    private final BluetoothThread thread;

    public static final int DATA_UPDATED = 4200;

    /**
     *
     * @param connectedSocket connected bluetooth socket
     * @param listener actions listener
     * @throws IllegalArgumentException if socket not connected or connector can't get I/O streams
     */
    public Connector(@NotNull BluetoothSocket connectedSocket, @NotNull ActionListener listener) throws IllegalArgumentException{
        InputStream inputStream;
        OutputStream outputStream;

        if (connectedSocket.isConnected()) {
            try {
                inputStream = connectedSocket.getInputStream();
                outputStream = connectedSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Can't get I/O streams");
            }
        } else {
            throw new IllegalArgumentException("Socket isn't connected");
        }

        this.thread = new BluetoothThread(inputStream, outputStream, listener, syncObject);
        new Thread(this.thread).start();
    }

    @Override
    public void setMode(@NonNull LedMode mode) {
        this.thread.setMode(mode);

        synchronized (this.syncObject){
            this.syncObject.notify();
        }
    }

    @Override
    public void setBrightness(int value) {
        this.thread.setBrightness(value);

        synchronized (this.syncObject){
            this.syncObject.notify();
        }
    }

    @Override
    public void setColor(@NonNull Color color) {
        this.thread.setColor(color);

        synchronized (this.syncObject){
            this.syncObject.notify();
        }
    }

    @Override
    public float getVoltage() {
        return thread.getVoltage();
    }
}
