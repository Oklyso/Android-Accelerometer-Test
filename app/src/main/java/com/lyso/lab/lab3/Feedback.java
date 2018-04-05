package com.lyso.lab.lab3;

import android.content.Context;
import android.os.Vibrator;

/**
 * Feedback Manager
 * Ole Lysø 3.5.2018
 */
final class Feedback {

    private static Vibrator vibrator;

    /**
     * Sets up haptic feedback using the vibrator if supported by the device.
     */
    public static void init(final Context context) {
        Feedback.vibrator = context.getSystemService(Vibrator.class);

        // Tell the user if the device does not support a vibrator
        if ((Feedback.vibrator == null) || !Feedback.vibrator.hasVibrator())
            Help.alertMessage(context.getString(R.string.error_no_vibrator), context);
    }

    /**
     * Vibrates for the specified duration.
     * NB! Must first be initialized with Feedback.init(Context)
     */
    public static void vibrate(final long duration) {
        if (Feedback.vibrator != null)
            Feedback.vibrator.vibrate(duration);
    }
}
