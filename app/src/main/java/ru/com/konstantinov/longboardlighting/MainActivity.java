package ru.com.konstantinov.longboardlighting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import ru.com.konstantinov.longboardlighting.Indicators.BatteryIndicator;
import ru.com.konstantinov.longboardlighting.Indicators.ConnectionIndicator;
import ru.com.konstantinov.longboardlighting.connector.Finder;
import ru.com.konstantinov.longboardlighting.interfaces.ActionListener;
import ru.com.konstantinov.longboardlighting.interfaces.ActivityResultSubscriber;
import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSparseArrays")
    private Map<ActivityResultSubscriber, Integer> subscribers = new HashMap<>();

    private DevicesListFragment devicesListFragment;
    private ModesListFragment modesListFragment;

    private AlertDialog brightnessMenu;
    private View mode_list;
    private View device_list;
    private TextView headerText;
    private DeviceFinder deviceFinder;
    private boolean isConnected = false;

    private BatteryIndicator batteryIndicator;
    private ConnectionIndicator connectionIndicator;

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

        headerText.setText(R.string.action_devices);

//        brightnessMenu = new Dialog(this);
//
//        WindowManager.LayoutParams lp = brightnessMenu.getWindow().getAttributes();
//        lp.dimAmount = 0.6f; // уровень затемнения от 1.0 до 0.0
//        brightnessMenu.getWindow().setAttributes(lp);
//        brightnessMenu.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//// Установите заголовок
//        brightnessMenu.setTitle("Заголовок диалога");
//        // Передайте ссылку на разметку
//        brightnessMenu.setContentView(R.layout.dialog_brightness);
//        // Найдите элемент TextView внутри вашей разметки
//        // и установите ему соответствующий текст

        brightnessMenu = new AlertDialog.Builder(this)
                .setTitle("Яркость")
                .setView(R.layout.dialog_brightness)
                .create();



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
    }

    public DeviceFinder getFinder() {
        return this.deviceFinder;
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
            case R.id.action_brightness:
                brightnessMenu.show();
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
