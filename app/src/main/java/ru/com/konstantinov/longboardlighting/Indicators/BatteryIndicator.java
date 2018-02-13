package ru.com.konstantinov.longboardlighting.Indicators;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import ru.com.konstantinov.longboardlighting.R;

/**
 * Created by dmitriy on 11.02.18.
 */

public final class BatteryIndicator {

    private TextView batteryText;
    private RelativeLayout batteryView;

    private static final float rangeMAX=4.2f;
    private static final float rangeMIN=3.2f;

    private Activity activity;
    private CircularProgressBar batteryProgressBar;
    private int animationDuration = 1500; // 1500ms = 1,5s

    public BatteryIndicator(Activity _activity) {
        this.activity = _activity;
        batteryView = this.activity.findViewById(R.id.battery_view);
        batteryText = this.activity.findViewById(R.id.battery_progress_text);
        //battery progress bar
        batteryProgressBar = this.activity.findViewById(R.id.battery_progress_bar);
        batteryProgressBar.setColor(ContextCompat.getColor(this.activity, R.color.cpb_progressbar_color));
        batteryProgressBar.setBackgroundColor(ContextCompat.getColor(this.activity, R.color.cpb_background_progressbar_color));
        batteryProgressBar.setProgressBarWidth(this.activity.getResources().getDimension(R.dimen.cpb_progressbar_width));
        batteryProgressBar.setBackgroundProgressBarWidth(this.activity.getResources().getDimension(R.dimen.cpb_background_progressbar_width));
        setUnknownState();
    }

    public void hide() {
        this.batteryView.setVisibility(View.GONE);
    }

    public void show() {
        this.batteryView.setVisibility(View.VISIBLE);

    }

    private void setUnknownState() {
        batteryText.setText("?");
    }

    public void setVoltageView(float voltage) {
        float level = (voltage - rangeMIN) * 100f;
        int percentLevel = Math.round(level);
        batteryProgressBar.setProgressWithAnimation(percentLevel, animationDuration); // Default is 0
        batteryText.setText(Integer.toString(percentLevel));
    }
}
