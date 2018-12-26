package ru.com.konstantinov.longboardlighting;

/**
 * Created by dmitriy on 12.02.18.
 * Variables of controller enum
 */

public enum ControllerVariables {
    MODE(0), // led mode
    BRIGHTNESS(1), // led brightness
    COLOR(2), // led color
    SPEED(3),
    UNKNOWN(999); // battery voltage

    private final int code;

    ControllerVariables(int code) {
        this.code = code;
    }

    public static ControllerVariables getControllerVariablesFromId(int id) {
        for (ControllerVariables type : values()) {
            if (type.code == id) return type;
        }
        return UNKNOWN;
    }
    public int getCode() {
        return code;
    }
}
