package ru.com.konstantinov.longboardlighting;

/**
 * Created by dmitriy on 12.02.18.
 */

public enum ControllerVariables {
    MODE(0),
    BRIGHTNESS(1),
    COLOR(2),
    VOLTAGE(3);

    private final int code;

    ControllerVariables(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
