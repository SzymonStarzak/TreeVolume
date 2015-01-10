package com.tree.sstarzak.treevolume;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by sstarzak on 2014-10-25.
 */
public class MySensors  implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mOrientation;

    private float azimuth_angle;
    private float pitch_angle;
    private float roll_angle;

    public float getRoll_angle() {
        return roll_angle;
    }

    public float getPitch_angle() {
        return pitch_angle;
    }

    public float getAzimuth_angle() {
        return azimuth_angle;
    }


    MySensors(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    public void registerSensors() {
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void releaseSensors() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        azimuth_angle = sensorEvent.values[0];
        pitch_angle = sensorEvent.values[1];
        roll_angle = sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
