package ru.com.konstantinov.longboardlighting;

/**
 * Created by dmitriy on 12.02.18.
 * Variables of controller enum
 */

public enum ControllerVariables {
    MODE(0), // led mode
    BRIGHTNESS(1), // led brightness
    COLOR(2), // led color
    VOLTAGE(3); // battery voltage

    private final int code;

    ControllerVariables(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
