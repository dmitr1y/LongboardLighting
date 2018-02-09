package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {

    private View mode_list;
    private View device_list;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.device_list = (View) findViewById(R.id.devices_list_view);
        this.mode_list = (View) findViewById(R.id.modes_list_view);
        this.headerText = (TextView) findViewById(R.id.headerText);
        this.headerText.setText(R.string.action_devices);

        //TODO check connection state of device, if not connected then hide
        CircularProgressBar batteryProgressBar = (CircularProgressBar)findViewById(R.id.battery_progress_bar);
        batteryProgressBar.setColor(ContextCompat.getColor(this, R.color.cpb_progressbar_color));
        batteryProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cpb_background_progressbar_color));
        batteryProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.cpb_progressbar_width));
        batteryProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.cpb_background_progressbar_width));
        int animationDuration = 1500; // 2500ms = 2,5s
        batteryProgressBar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms

        CircularProgressBar connectionStatus = (CircularProgressBar)findViewById(R.id.connection_status_bar);
        connectionStatus.setColor(ContextCompat.getColor(this, R.color.connection_color));
        connectionStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.background_connection_color));
        connectionStatus.setProgressBarWidth(getResources().getDimension(R.dimen.connection_width));
        connectionStatus.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.background_connection_width));
//        int animationDuration = 1500; // 2500ms = 2,5s
        connectionStatus.setProgressWithAnimation(100, 1); // Default duration = 1500ms
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

}
