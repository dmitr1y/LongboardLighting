package ru.com.konstantinov.longboardlighting.dummy;

import android.graphics.Color;
import android.util.Log;

import ru.com.konstantinov.longboardlighting.Connector.ConnectionInterface;
import ru.com.konstantinov.longboardlighting.LedMode;

/**
 * Created by ceyler on 09.02.2018.
 * Class for connection interface testing
 */

public class TestConnector implements ConnectionInterface {
    private final static String testTag = "Connector";

    @Override
    public void setMode(LedMode mode) {
        Log.i(testTag, "New mode is " + mode.toString());
    }

    @Override
    public void setBrightness(int value) {
        Log.i(testTag, "New brightness is " + value);
    }

    @Override
    public void setColor(Color color) {
        Log.i(testTag, "New color is " + color.toString());
    }

    @Override
    public float getVoltage() {
        return 42;
    }
}
