package ru.com.konstantinov.longboardlighting.interfaces;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for reaction on some random action
 */

public interface ActionListener {
    /**
     *
     * @param action action code from BluetoothAdapter
     */
    void onAction(int action);
}
