package ru.com.konstantinov.longboardlighting.interfaces;

import android.content.Intent;

/**
 * Created by ceyler on 09.02.2018.
 * Interface for subscribing on activity result
 */

public interface ActivityResultSubscriber {
    void onActivityResult(int resultCode, Intent data);
}
