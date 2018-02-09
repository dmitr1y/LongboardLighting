package ru.com.konstantinov.longboardlighting;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by ceyler on 09.02.2018.
 * Modes enum
 */

public enum LedMode {
    ALL_WHITE(1, "Вся лента белая"), // all leds is white
    RAINBOW_FADE(2, "Плавная смена цветов всей ленты"), // smooth change of colors of all tape
    RAINBOW_LOOP(3, "Крутящаяся радуга"), // a twirling rainbow
    RANDOM_BURST(4, "Случайная смена цветов"), // random color change
    COLOR_BOUNCE(5, "Бегающий светодиод"), // running LED
    COLOR_BOUNCE_FADE(6, "Бегающий паровозик светодиодов"), // running locomotive of light-emitting diodes
    EMS_LIGHTS_ONE(7, "Вращаются красный и синий"), // rotate red and blue
    EMS_LIGHTS_ALL(8, "Вращается половина красных и половина синих"), // rotates half red and half blue
    FLICKER(9, "Случайный стробоскоп"), // random stroboscope
    PULSE_ONE_COLOR_ALL(10, "Пульсация одним цветом"), // pulsation in one color
    PULSE_ONE_COLOR_ALL_REV(11, "Пульсация со сменой цветов"), // pulsation with a change of colors
    FADE_VERTICAL(12, "Плавная смена яркости по вертикали (для кольца)"), // smooth brightness change vertically (for a ring)
    RULE_30(13, "Безумие красных светодиодов"), // the madness of the red LEDs
    RANDOM_MARCH(14, "Безумие случайных цветов"), // frenzy of random colors
    RWB_MARCH(15, "Белый синий красный бегут по кругу (ПАТРИОТИЗМ!)"), // white blue red run in a circle (PATRIOTISM!)
    RADIATION(16, "Пульсирует значок радиации"), // the radiation icon is pulsating
    COLOR_LOOP_VAR_DELAY(17, "Красный светодиод бегает по кругу"), // the red LED runs in a circle
    WHITE_TEMPS(18, "Бело-синий градиент (?)"), // white blue gradient (?)
    SIN_BRIGHT_WAVE(19, "Тоже хрень какая то"), // also some crap
    POP_HORIZONTAL(20, "Красные вспышки спускаются вниз"), // red flashes go down
    QUAD_BRIGHT_CURVE(21, "Полумесяц"), // crescent moon
    flame(22, "Эффект пламени"), // flame effect
    RAINBOW_VERTICAL(23, "Радуга в вертикаьной плоскости (кольцо)"), // a rainbow in the vertical plane (ring)
    PACMAN(24, "Пакман"), // pacman
    RANDOM_COLOR_POP(25, "Безумие случайных вспышек"), // the frenzy of random flashes
    EMS_LIGHTS_STROBE(26, "Полицейская мигалка"), // police flasher
    RGB_PROPELLER(27, "RGB пропеллер"), // RGB propeller
    KITT(28, "Случайные вспышки красного в вертикаьной плоскости"), // random flashes of red in the vertical plane
    MATRIX(29, "Зелёненькие бегают по кругу случайно"), // the greens run around in circles by accident
    NEW_RAINBOW_LOOP(30, "Крутая плавная вращающаяся радуга"), // steep smooth rotating rainbow
    STRIP_MARCH_CCW(31, "Чёт сломалось 1"), // even broke
    STRIP_MARCH_CW(32, "Чёт сломалось 2"), // even broke
    COLOR_WIPE(33, "Плавное заполнение цветом"), // smooth filling with color
    CYLON_BOUNCE(34, "Бегающие светодиоды"), // running LEDs
    FIRE(35, "Линейный огонь"), // Linear fire
    NEW_KITT(36, "Беготня секторов круга (не работает)"), // run the sectors of the circle (does not work)
    RAINBOW_CYCLE(37, "Очень плавная вращающаяся радуга\n"), // very smooth rotating rainbow
    TWINKLE_RANDOM(38, "Случайные разноцветные включения"), // random multicolored inclusions (1 - all dance, 0 - random 1 diode)
    RUNNING_LIGHTS(39, "Бегущие огни"), // running lights
    SPARKLE(40, "Случайные вспышки белого цвета"), // random flashes of white color
    SNOW_SPARKLE(41, "Случайные вспышки белого цвета на белом фоне"), // random flash of white color on a white background
    THEATER_CHASE(42, "Бегущие каждые 3"), // running every 3 (NUMBER OF LEDS MUST BE ODD)
    THEATER_CHASE_RAINBOW(43, "Бегущие каждые 3 радуга"), // running every 3 rainbows (NUMBER OF LEDS SHOULD BE BACK 3)
    STROBE(44, "Стробоскоп"), // stroboscope
    BOUNCING_BALLS(45, "Прыгающие мячики"), // jumping balls
    BOUNCING_COLORED_BALLS(46, "Прыгающие мячики цветные"), // jumping colored balls
    DEMO_MODE_A(888, "Плинное демо"), // a long demo
    DEMO_MODE_B(889, "Короткое демо"), // short demo
    PAUSE(999, "Пауза"); // pause

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