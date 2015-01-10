package com.tree.sstarzak.treevolume;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by sstarzak on 2014-10-26.
 */
public class ObjectDistance {

    private float pitch_angle;

    private float roll_angle;

    private float height;

    private float angle;

    private float distance;

    private Context context;

    public float getDistance() {
        return distance;
    }

    public String getDistanceAsStringUnitMeter() {
        return String.format("%.2f",getDistance()/100);
    }


    public ObjectDistance(Context context, float pitch_angle, float roll_angle, float height) {
        this.context = context;
        this.pitch_angle = pitch_angle;
        this.roll_angle = roll_angle;
        this.height = height;

        calculateAngle();

        calculateDistance();
    }

    private void calculateAngle() {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch ( display.getRotation()){
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                angle = Math.abs(roll_angle);
                break;
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                angle = Math.abs(pitch_angle);
                break;
        }
    }
    private void calculateDistance() {
        distance = ((float) (height* Math.tan(angle* (Math.PI / 180) )));
    }

}
