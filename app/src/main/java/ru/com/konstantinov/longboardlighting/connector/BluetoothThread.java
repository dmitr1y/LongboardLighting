package ru.com.konstantinov.longboardlighting.connector;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

import ru.com.konstantinov.longboardlighting.LedMode;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ConnectionInterface;

/**
 * Created by ceyler on 11.02.2018.
 * Thread for bluetooth communication
 */

public class BluetoothThread extends Thread implements ConnectionInterface {
    private final Scanner scanner;
    private final Writer writer;
    private final ActionListener listener;
    private final Object syncObject;

    private volatile LedMode mode = LedMode.RAINBOW_FADE;
    private volatile int brightness = 100;
    private volatile Color color;
    private volatile float voltage;

    BluetoothThread(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @NotNull ActionListener listener, @NotNull Object syncObject) {
        this.scanner = new Scanner(inputStream).useDelimiter("@").useDelimiter("#").useDelimiter(":");
        this.writer = new OutputStreamWriter(outputStream);
        this.listener = listener;
        this.syncObject = syncObject;
    }

    @Override
    public void run() {
        while (true) {
            String output = "#0:" + Integer.toString(this.mode.getCode()) + "#1:" + Integer.toString(this.brightness) + "@";

            try {
                writer.write(output);
                writer.flush();
                Log.w("Finder", "Writing: " + output);
            } catch (IOException e) {
                listener.onAction(BluetoothAdapter.STATE_DISCONNECTED);
                break;
            }

            Log.w("Finder", "Start reading");

            try{
                int code = Integer.valueOf(scanner.next());
                if(code == 3) {
                    this.voltage = Float.valueOf(scanner.next());
                }else {
                    Log.w("Finder", "Unknown code: " + code);
                }
            } catch (NumberFormatException ex){
                Log.w("Finder", "Something went wrong!");
            }

            Log.w("Finder", "Finish reading");

            try {
                synchronized (syncObject) {
                    syncObject.wait(1000); // wait for a second
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void parserAnswer(String answer) {
        String[] parsed = answer.split("#");
        for (String aParsed : parsed) {

            String[] variable = aParsed.split(":", 2);
            if (variable.length == 2) {
                Log.i("parserAnswer - variable", variable[0] + " = " + variable[1]);
//                switch (Integer.getInteger(variable[0])){
//                    case ControllerVariables.VOLTAGE:
//                    float value = Float.valueOf(variable[1].trim());
//                        BatteryIndicator
//                    break;
//
//                    default:
//                        break;
//                }


            }
        }
    }


    @Override
    public void setMode(@NonNull LedMode mode) {
        this.mode = mode;
    }

    @Override
    public void setBrightness(int value) {
        this.brightness = value;
    }

    @Override
    public void setColor(@NonNull Color color) {
        this.color = color;
    }

    @Override
    public float getVoltage() {
        return this.voltage;
    }
}
