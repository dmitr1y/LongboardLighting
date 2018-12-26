package ru.com.konstantinov.longboardlighting;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by ceyler on 09.02.2018.
 * Modes enum
 */

public enum LedMode {
    ALL_OFF(999, "Выключить"), // all leds is off
    BOUNCING_COLORED_BALLS(1, "Прыгающие цветные шарики"),
    BOUNCING_BALLS(2, "Прыгающие шарики"),
    COLOR_WIPE(3, "Заполнение цветом"),
    CYLON_BOUNCE(4, "Cylon Bounce"),
    FADE_RWB(5, "FadeInOut RED WHITE BLUE"),
    RGB_LOOP(6, "Плавная смена цветов"),
    FIRE(7, "Эффект огня"),
    HALLOWEEN_EYES(8, "Halloween Eyes"),
    NEW_KITT(9, "NEW_KITT"),
    RAINBOW_CYCLE(10, "Вращающаяся радуга"),
    TWINKLE_RANDOM(11, "Цветные блестки"),
    RUNNING_LIGHTS(12, "Бегущие огни"),
    SNOW_SPARKLE(13, "SNOW_SPARKLE"),
    SPARKLE(14, "SPARKLE"),
    STROBE(15, "Стробоскоп"),
    THEATER_CHASE_RAINBOW(16, "THEATER_CHASE_RAINBOW"),
    THEATER_CHASE(17, "THEATER_CHASE"),
    TWINKLE(18, "Красные блетски"),
    RGB_ROTATION(19, "RGB ROTATION"),
    EMS_LIGHTS_STROBE(20, "Полицейский стробоскоп"),
    SOUND_REACTIVE(21, "Светомузыка"),
    FILL_GRADIENT(22, "Заполнение градиентом");

    private final int code;
    private final String name;

    LedMode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static LedMode getModeByName(@NotNull String name) {
        for (LedMode mode : LedMode.values()) {
            if (Objects.equals(mode.toString(), name))
                return mode;
        }
        return null; //Can't find mode with this name
    }
}