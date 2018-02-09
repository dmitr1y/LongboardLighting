package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View mode_list;
    private View device_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.device_list= (View) findViewById(R.id.devices_list_view);
        this.mode_list= (View) findViewById(R.id.modes_list_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_devices:
                this.device_list.setVisibility(View.VISIBLE);
                this.mode_list.setVisibility(View.GONE);
                break;
            case R.id.action_modes:
                this.device_list.setVisibility(View.GONE);
                this.mode_list.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
