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

    private static final float rangeMAX = 4.2f;
    private static final float rangeMIN = 3.2f;

    private float voltageLog[];
    private int voltageLogIndex=0;

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

    private float getCorrectVoltage(float inVolt) {
        float k = 1f;
        if (inVolt > 3.5f)
            k = 1.2f;
        else if (inVolt >= 3.4f && inVolt < 3.5f)
            k = 1.21f;
        else if (inVolt >= 3.2f && inVolt < 3.4f)
            k = 1.29f;
        else if (inVolt >= 3.17f && inVolt < 3.2f)
            k = 1.32f;
        else if (inVolt >= 3.06f && inVolt < 3.17f)
            k = 1.36f;
        else if (inVolt >= 3.0f && inVolt < 3.06f)
            k = 1.39f;
        else if (inVolt >= 2.95f && inVolt < 3f)
            k = 1.32f;
        else if (inVolt >= 2.90f && inVolt < 2.95f)
            k = 1.41f;
        else if (inVolt >= 2.85f && inVolt < 2.90f)
            k = 1.46f;

        return inVolt * k;
    }


    private void logVoltage(float voltage){
        if (voltageLogIndex>9 || voltageLogIndex<0)
            voltageLogIndex=0;
        voltageLog[voltageLogIndex]=voltage;
    }

    private float getAverageVoltage(){
        float average=0f;
        for (int i=0; i<voltageLog.length;i++)
            average+=voltageLog[voltageLogIndex];
        return average/voltageLog.length;
    }

    public void setVoltageView(float voltage) {
        Log.w("VOLTAGE: ", "setVoltageView: " + Float.toString(voltage));
        float level = (voltage - rangeMIN) * 100f;
        int percentLevel = Math.round(level);
        batteryProgressBar.setProgressWithAnimation(percentLevel, animationDuration); // Default is 0
        batteryText.setText(Integer.toString(percentLevel));
    }
}
