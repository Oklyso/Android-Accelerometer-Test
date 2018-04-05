package com.lyso.lab.lab3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Main Activity
 * Ole Lysø 3.5.2018
 */
public class MainActivity extends AppCompatActivity {

    private InterfaceView canvas;
    private final ArrayList<Integer>  resources      = new ArrayList<>();
    private Sensor              sensorAccel;
    private SensorManager       sensorManager;
    private final AccelSensorListener sensorListener = new AccelSensorListener();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.canvas = new InterfaceView(this);
        setContentView(this.canvas);

        this.initSensors();
        Feedback.init(this);
        this.initResources();
        Sounds.init(this.resources, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this.sensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.sensorAccel == null) {
            Help.alertMessage(getString(R.string.error_no_accel), this);
            return;
        }

        this.sensorManager.registerListener(
            this.sensorListener, this.sensorAccel, SensorManager.SENSOR_DELAY_GAME
        );
    }

    /**
     * Sets up the resources needed like sound effects.
     */
    private void initResources() {
        this.resources.add(R.raw.ding);
        this.resources.add(R.raw.ping);

    }

    /**
     * Sets up the sensors and event listeners needed.
     */
    private void initSensors() {
        this.sensorManager = getSystemService(SensorManager.class);

        // TODO: Should not happen
        if (this.sensorManager == null)
            throw new NullPointerException();

        this.sensorAccel = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Accelerator Sensor Listener
     */
    private class AccelSensorListener implements SensorEventListener {

        /**
         * Handles sensor events when a sensor has changed.
         * SENSOR_DELAY_GAME: Updates roughly about 60 times a second.
         * @param sensorEvent The sensor event
         */
        @Override
        public void onSensorChanged(final SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                canvas.setSensorValues(sensorEvent.values);
                canvas.invalidate();
            }
        }

        /**
         * Not used, but must be overridden to implement abstract interface SensorEventListener.
         * @param sensor The sensor
         * @param delta The new accuracy
         */
        @Override
        public void onAccuracyChanged(final Sensor sensor, int delta) {
        }

    }

}
