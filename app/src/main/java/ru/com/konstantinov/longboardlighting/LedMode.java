package ru.com.konstantinov.longboardlighting;

/**
 * Created by ceyler on 09.02.2018.
 * Modes enum
 */

public enum LedMode {

    RAINBOW_FADE(2), // smooth change of colors of all tape
    RAINBOW_LOOP(3), // a twirling rainbow
    RANDOM_BURST(4), // random color change
    COLOR_BOUNCE(5), // running LED
    COLOR_BOUNCE_FADE(6), // running locomotive of light-emitting diodes
    EMS_LIGHTS_ONE(7), // rotate red and blue
    EMS_LIGHTS_ALL(8), // rotates half red and half blue
    FLICKER(9), // random stroboscope
    PULSE_ONE_COLOR_ALL(10), // pulsation in one color
    PULSE_ONE_COLOR_ALL_REV(11), // pulsation with a change of colors
    FADE_VERTICAL(12), // smooth brightness change vertically (for a ring)
    RULE_30(13), // the madness of the red LEDs
    RANDOM_MARCH(14), // frenzy of random colors
    RWB_MARCH(15), // white blue red run in a circle (PATRIOTISM!)
    RADIATION (16), // the radiation icon is pulsating
    COLOR_LOOP_VAR_DELAY(17), // the red LED runs in a circle
    WHITE_TEMPS(18), // white blue gradient (?)
    SIN_BRIGHT_WAVE(19), // also some crap
    POP_HORIZONTAL(20), // red flashes go down
    QUAD_BRIGHT_CURVE(21), // crescent moon
    flame (22), // flame effect
    RAINBOW_VERTICAL(23), // a rainbow in the vertical plane (ring)
    PACMAN(24), // pacman
    RANDOM_COLOR_POP(25), // the frenzy of random flashes
    EMS_LIGHTS_STROBE(26), // police flasher
    RGB_PROPELLER(27), // RGB propeller
    KITT(28), // random flashes of red in the vertical plane
    MATRIX(29), // the greens run around in circles by accident
    NEW_RAINBOW_LOOP(30), // steep smooth rotating rainbow
    STRIP_MARCH_CCW(31), // even broke
    STRIP_MARCH_CW(32), // even broke
    COLOR_WIPE(33), // smooth filling with color
    CYLON_BOUNCE(34), // running LEDs
    FIRE(35), // Linear fire
    NEW_KITT(36), // run the sectors of the circle (does not work)
    RAINBOW_CYCLE(37), // very smooth rotating rainbow
    TWINKLE_RANDOM(38), // random multicolored inclusions (1 - all dance, 0 - random 1 diode)
    RUNNING_LIGHTS(39), // running lights
    SPARKLE(40), // random flashes of white color
    SNOW_SPARKLE(41), // random flash of white color on a white background
    THEATER_CHASE(42), // running every 3 (NUMBER OF LEDS MUST BE ODD)
    THEATER_CHASE_RAINBOW(43), // running every 3 rainbows (NUMBER OF LEDS SHOULD BE BACK 3)
    STROBE(44), // stroboscope
    BOUNCING_BALLS(45), // jumping balls
    BOUNCING_COLORED_BALLS(46), // jumping colored balls
    DEMO_MODE_A(888), // a long demo
    DEMO_MODE_B(889); // short demo

    private final int code;

    LedMode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}