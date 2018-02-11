package ru.com.konstantinov.longboardlighting;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import ru.com.konstantinov.longboardlighting.connector.Finder;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ActivityResultSubscriber;
import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSparseArrays")
    private Map<ActivityResultSubscriber, Integer> subscribers = new HashMap<>();

    private DevicesListFragment devicesListFragment;
    private ModesListFragment modesListFragment;

    private CircularProgressBar batteryProgressBar;
    private CircularProgressBar connectionStatus;
    private int animationDuration = 1500; // 2500ms = 2,5s

    private View mode_list;
    private View device_list;
    private TextView headerText;
    private TextView batteryText;
    private DeviceFinder deviceFinder;
    private RelativeLayout batteryView;
    private boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        device_list = findViewById(R.id.devices_list_view);
        mode_list = findViewById(R.id.modes_list_view);
        headerText = findViewById(R.id.headerText);
        batteryView = findViewById(R.id.battery_view);
        batteryText = findViewById(R.id.battery_progress_text);
        devicesListFragment = new DevicesListFragment();
        modesListFragment = new ModesListFragment();

        headerText.setText(R.string.action_devices);

        //battery progress bar
        batteryProgressBar = findViewById(R.id.battery_progress_bar);
        batteryProgressBar.setColor(ContextCompat.getColor(this, R.color.cpb_progressbar_color));
        batteryProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cpb_background_progressbar_color));
        batteryProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.cpb_progressbar_width));
        batteryProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.cpb_background_progressbar_width));


        //connection status indicator (0 - not connected, 1 - connected)
        connectionStatus = findViewById(R.id.connection_status_bar);
        connectionStatus.setColor(ContextCompat.getColor(this, R.color.connection_color));
        connectionStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.background_connection_color));
        connectionStatus.setProgressBarWidth(getResources().getDimension(R.dimen.connection_width));
        connectionStatus.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.background_connection_width));

        if (!isConnected) {
            batteryView.setVisibility(View.GONE); // Default is hidden
            batteryProgressBar.setProgressWithAnimation(0, animationDuration); // Default is 0
            connectionStatus.setProgressWithAnimation(0, 1); // Default is 0 (red)
        }
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
                        connectionStatus.setProgressWithAnimation(0, 1); // Set indicator red
                        batteryView.setVisibility(View.GONE);
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        isConnected = true;
                        connectionStatus.setProgressWithAnimation(100, 1); // Set indicator green
                        Toast.makeText(getApplicationContext(), getString(R.string.bt_status_on), Toast.LENGTH_SHORT).show();
                        batteryView.setVisibility(View.VISIBLE);
                        break;
                    case BluetoothAdapter.STATE_DISCONNECTED:
                        isConnected = false;
                        connectionStatus.setProgressWithAnimation(0, 1); // Set indicator red
                        batteryView.setVisibility(View.GONE);
                        break;
                    default:
                        connectionStatus.setProgressWithAnimation(0, 1); // Set indicator red
                        break;
                }
            }
        });
    }

    public DeviceFinder getFinder() {
        return this.deviceFinder;
    }

    public void setVoltageView(float voltage) {
        float level = (voltage - 3.2f) * 100f;
        int percentLevel = Math.round(level);
        batteryProgressBar.setProgressWithAnimation(percentLevel, animationDuration); // Default is 0
        batteryText.setText(Integer.toString(percentLevel));
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
