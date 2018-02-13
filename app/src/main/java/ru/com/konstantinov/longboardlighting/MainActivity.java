package ru.com.konstantinov.longboardlighting;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import ru.com.konstantinov.longboardlighting.Indicators.BatteryIndicator;
import ru.com.konstantinov.longboardlighting.Indicators.ConnectionIndicator;
import ru.com.konstantinov.longboardlighting.connector.Connector;
import ru.com.konstantinov.longboardlighting.connector.Finder;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ActivityResultSubscriber;
import ru.com.konstantinov.longboardlighting.interfaces.ConnectionInterface;
import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSparseArrays")
    private Map<ActivityResultSubscriber, Integer> subscribers = new HashMap<>();

    private DevicesListFragment devicesListFragment;
    private ModesListFragment modesListFragment;

    private View mode_list;
    private View device_list;
    private TextView headerText;
    private DeviceFinder deviceFinder;
    private boolean isConnected = false;
    private BatteryIndicator batteryIndicator;
    private ConnectionIndicator connectionIndicator;

    BluetoothSocket bluetoothSocket;
    ConnectionInterface connector;

    private DiscreteSeekBar brightnessIndicator;
    private int brightnessValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        device_list = findViewById(R.id.devices_list_view);
        mode_list = findViewById(R.id.modes_list_view);
        headerText = findViewById(R.id.headerText);

        devicesListFragment = new DevicesListFragment();
        modesListFragment = new ModesListFragment();

        batteryIndicator = new BatteryIndicator(this);
        connectionIndicator = new ConnectionIndicator(this);

        brightnessIndicator = findViewById(R.id.brightness_indicator);

        headerText.setText(R.string.action_devices);
        batteryIndicator.hide();
        brightnessValue = 150;

//        TODO complete handler
        this.deviceFinder = new Finder(this, new ActionListener() {
            @Override
            public void onAction(int action) {
                switch (action) {
                    case BluetoothAdapter.ERROR:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    case BluetoothAdapter.STATE_OFF:
//                        TODO try to enable BT
                        connectionIndicator.setOff();
                        batteryIndicator.hide();
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        isConnected = true;
                        connectionIndicator.setOn();
                        batteryIndicator.show();
//                        TODO send empty message for receiving battery voltage
                        break;
                    case BluetoothAdapter.STATE_DISCONNECTED:
                        isConnected = false;
                        connectionIndicator.setOff();
                        batteryIndicator.hide();
                        break;
                    default:
                        break;
                }
            }
        });

        brightnessIndicator.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                brightnessValue = seekBar.getProgress();
                connector.setBrightness(brightnessValue);
            }
        });
    }

    public DeviceFinder getFinder() {
        return this.deviceFinder;
    }

    public void setBluetoothSocket(BluetoothSocket _bluetoothSocket) {
        this.bluetoothSocket = _bluetoothSocket;
        this.connect();
    }

    public void connect() {
        this.connector = new Connector(this.bluetoothSocket, new ActionListener() {
            @Override
            public void onAction(int action) {
                Log.w("MainActivity", " ConnectionInterface connector - Action: " + action);
            }
        });
    }

    public ConnectionInterface getConnector() {
        return this.connector;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isConnected)
            menu.findItem(R.id.action_modes).setEnabled(true);
        else
            menu.findItem(R.id.action_modes).setEnabled(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_devices:
                this.device_list.setVisibility(View.VISIBLE);
                this.mode_list.setVisibility(View.GONE);
                this.headerText.setText(R.string.action_devices);
                break;
            case R.id.action_modes:
                this.device_list.setVisibility(View.GONE);
                this.mode_list.setVisibility(View.VISIBLE);
                this.headerText.setText(R.string.action_modes);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void subscribeOnActivityResult(@NotNull ActivityResultSubscriber subscriber, int requestCode) {
        subscribers.put(subscriber, requestCode);
    }

    public void unsubscribeFromActivityResult(@NotNull ActivityResultSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (ActivityResultSubscriber subscriber : subscribers.keySet()) {
            if (subscribers.get(subscriber) == requestCode)
                subscriber.onActivityResult(resultCode, data); // call all subscribers with this request code
        }
    }

    @Override
    protected void onDestroy() {
        this.deviceFinder.onActivityDestroy(this);
        super.onDestroy();
    }
}
