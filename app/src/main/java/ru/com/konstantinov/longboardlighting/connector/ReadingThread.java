package ru.com.konstantinov.longboardlighting.connector;

import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.com.konstantinov.longboardlighting.ControllerVariables;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;

/**
 * Created by ceyler on 13.02.2018.
 * Thread for reading data via bluetooth
 */

class ReadingThread extends AsyncTask<Void, Integer, Void> {
    private final Scanner scanner;
    private final ActionListener listener;

    private volatile float voltage;
    private volatile int mode;
    private volatile int brightness;
    private volatile int speed;
    private volatile String color;

    ReadingThread(@NotNull InputStream inputStream, @NotNull ActionListener listener) {
        this.scanner = new Scanner(inputStream).useDelimiter("[#:@\\s]");
        this.listener = listener;
        Log.w("LBReading", "ReadingThread: constructed");
    }

    float getVoltage(){
        return this.voltage;
    }

    @Override
    protected Void doInBackground(Void... input) {
        while (true) {
            Log.w("LBReading", "Start reading");

            String data = "";
            try {

                while (data.length() == 0) { // waiting for data
                    data = scanner.next();
                }

                try {
                    int code = Integer.valueOf(data);
                    ControllerVariables var_code = ControllerVariables.getControllerVariablesFromId(code);
//                    if (code == ControllerVariables.VOLTAGE.getCode()) {
//                        this.voltage = Float.valueOf(scanner.next());
//                    }
                    switch (var_code) {
                        case MODE:
                            this.mode = Integer.valueOf(scanner.next());
                            break;
                        case BRIGHTNESS:
                            this.brightness = Integer.valueOf(scanner.next());
                            break;
                        case COLOR:
                            this.color = scanner.next();
                            break;
                        case SPEED:
                            this.speed = Integer.valueOf(scanner.next());
                            break;
                        case UNKNOWN:
                        default:
                            break;
                    }

                    this.publishProgress(Connector.DATA_UPDATED);
                } catch (NumberFormatException ignored) {}

            } catch (NoSuchElementException e) {
                this.publishProgress(BluetoothAdapter.STATE_DISCONNECTED);
                break;
            } catch (IllegalStateException e) {
                this.publishProgress(BluetoothAdapter.STATE_DISCONNECTED);
                break;
            }
            Log.w("LBReading", "Finish reading");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onAction(values[0]);
    }
}
