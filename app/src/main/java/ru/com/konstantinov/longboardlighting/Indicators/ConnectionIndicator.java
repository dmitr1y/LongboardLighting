package ru.com.konstantinov.longboardlighting.Indicators;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import ru.com.konstantinov.longboardlighting.R;

/**
 * Created by dmitriy on 11.02.18.
 */

public final class ConnectionIndicator {
    private Activity activity;

    private CircularProgressBar connectionStatus;
    private int animationDuration = 1;

    public ConnectionIndicator(Activity _activity) {
        this.activity = _activity;

        //connection status indicator (0 - not connected, 1 - connected)
        connectionStatus = this.activity.findViewById(R.id.connection_status_bar);
        connectionStatus.setColor(ContextCompat.getColor(this.activity, R.color.connection_color));
        connectionStatus.setBackgroundColor(ContextCompat.getColor(this.activity, R.color.background_connection_color));
        connectionStatus.setProgressBarWidth(this.activity.getResources().getDimension(R.dimen.connection_width));
        connectionStatus.setBackgroundProgressBarWidth(this.activity.getResources().getDimension(R.dimen.background_connection_width));
        setOff();
    }

    public void setOn() {
        connectionStatus.setProgressWithAnimation(100, animationDuration); // set green
    }

    public void setOff() {
        connectionStatus.setProgressWithAnimation(0, animationDuration); // set red
    }

    public void hide() {
        connectionStatus.setVisibility(View.GONE);
    }

    public void show() {
        connectionStatus.setVisibility(View.VISIBLE);
    }
}
