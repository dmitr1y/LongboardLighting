package ru.com.konstantinov.longboardlighting.connector;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import ru.com.konstantinov.longboardlighting.ControllerVariables;
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
    private volatile float voltage;

    BluetoothThread(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @NotNull ActionListener listener, @NotNull Object syncObject) {
        this.scanner = new Scanner(inputStream).useDelimiter("[@:#\\s]");
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
            } catch (IOException e) {
                listener.onAction(BluetoothAdapter.STATE_DISCONNECTED);
                break;
            }

            String data = "";
            while (data.length() == 0){
                data = scanner.next();
            }
            try {
                int code = Integer.valueOf(data);

                if (code == ControllerVariables.VOLTAGE.getCode()) {
                    this.voltage = Float.valueOf(scanner.next());
                }

                this.listener.onAction(Connector.DATA_UPDATED);
            } catch (NumberFormatException ignored) {}

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
//            if (variable.length == 2) {
//                Log.i("parserAnswer - variable", variable[0] + " = " + variable[1]);
//                switch (Integer.getInteger(variable[0])){
//                    case ControllerVariables.VOLTAGE:
//                    float value = Float.valueOf(variable[1].trim());
//                        BatteryIndicator
//                    break;
//
//                    default:
//                        break;
//                }


//            }
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
        throw new UnsupportedOperationException("Not ready yet :(");
    }

    @Override
    public float getVoltage() {
        return this.voltage;
    }
}
