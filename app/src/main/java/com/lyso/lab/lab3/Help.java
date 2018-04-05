package com.lyso.lab.lab3;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Helper utilities.
 * Ole Lysø 3.5.2018
 */
final class Help {

    /**
     * Displays the message in a pop-up alert dialog.
     *
     */
    public static void alertMessage(final String message, final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(R.string.app_name);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", (d, i) -> d.dismiss());
        dialog.show();
    }

}
