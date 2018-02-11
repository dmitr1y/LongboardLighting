package ru.com.konstantinov.longboardlighting.connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import ru.com.konstantinov.longboardlighting.MainActivity;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ActivityResultSubscriber;
import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ceyler on 09.02.2018.
 * Basic connector
 */

public class Finder implements DeviceFinder, ActivityResultSubscriber {

    private final static UUID FINDER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter adapter;
    private final ActionListener listener;
    private final BroadcastReceiver bluetoothStateReceiver;

    private BluetoothSocket socket = null;

    public Finder(final MainActivity activity, final ActionListener bluetoothActionsListener) {
        this.listener = bluetoothActionsListener;

        this.bluetoothStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = (intent != null ? intent.getAction() : null);

                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action) || BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                    listener.onAction(state);
                }
            }
        };

        this.adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            listener.onAction(BluetoothAdapter.ERROR);
            return;
        }

        if (adapter.isEnabled()) {
            listener.onAction(BluetoothAdapter.STATE_ON);
        } else {
            int requestCode = "LongboardActivityApp".hashCode() & 0b01111111111111111;

            activity.subscribeOnActivityResult(
                    new ActivityResultSubscriber() {
                        @Override
                        public void onActivityResult(int resultCode, Intent data) {
                            activity.unsubscribeFromActivityResult(this);
                            if (resultCode == RESULT_OK)
                                listener.onAction(BluetoothAdapter.STATE_ON);
                            else
                                listener.onAction(BluetoothAdapter.STATE_OFF);
                        }
                    },
                    requestCode + 1);
            activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), requestCode + 1);
        }

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        activity.registerReceiver(this.bluetoothStateReceiver, filter);
    }

    private void closeSocket(){
        listener.onAction(BluetoothAdapter.STATE_DISCONNECTED);
        if (this.socket != null){
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                this.socket = null;
            }
        }
    }

    @Override
    public Set<BluetoothDevice> getBondedDevices() {
        return adapter.getBondedDevices();
    }

    @Override
    public BluetoothSocket ConnectToDevice(@NotNull BluetoothDevice device) {
        this.closeSocket();

        try {
            this.socket = device.createRfcommSocketToServiceRecord(FINDER_UUID);
            this.socket.connect();
            listener.onAction(BluetoothAdapter.STATE_CONNECTED);
        } catch (IOException e) {
            e.printStackTrace();
            this.closeSocket();
        }

        return this.socket;
    }

    @Override
    public void onActivityResult(int resultCode, Intent data) {
        listener.onAction(resultCode);
    }

    public void onActivityDestroy(MainActivity activity){
        this.closeSocket();
        activity.unregisterReceiver(this.bluetoothStateReceiver);
    }
}
