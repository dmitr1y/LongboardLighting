package ru.com.konstantinov.longboardlighting.interfaces;

import android.graphics.Color;

import org.jetbrains.annotations.NotNull;

import ru.com.konstantinov.longboardlighting.LedMode;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for controller connector
 */


public interface ConnectionInterface {
    /**
     *
     * @param mode - mode for led
     */
    void setMode(@NotNull LedMode mode);

    /**
     *
     * @param value from 0 to 255
     */
    void setBrightness(int value);

    /**
     *
     * @param color color value
     */
    void setColor(@NotNull Color color);

    /**
     *
     * @return current voltage on controller with accuracy 2 decimal places
     */
    float getVoltage();
}
