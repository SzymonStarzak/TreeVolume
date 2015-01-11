package com.tree.sstarzak.treevolume;

import android.content.Context;

/**
 * Created by sstarzak on 2015-01-09.
 */
public class ObjectLength {

    private Context context;
    private float azimuth_angle1;
    private float azimuth_angle2;
    private float distance;
    private float angle;

    public ObjectLength(Context context, float azimuth_angle1, float azimuth_angle2, float distance) {
        this.context = context;
        this.azimuth_angle1 = azimuth_angle1;
        this.azimuth_angle2 = azimuth_angle2;
        this.distance = distance;
        this.angle = 0;
    }

    public float getLengh() {
        if(Math.abs(azimuth_angle2 - azimuth_angle1) > 180) {
            if(azimuth_angle1 > azimuth_angle2)
                angle = 360-azimuth_angle1 + azimuth_angle2;
            else
                angle = 360 - azimuth_angle2 + azimuth_angle1;
        } else{
            angle =  Math.abs(azimuth_angle2 - azimuth_angle1);
        }
        return (float)(2 * distance * Math.tan(0.5 * Math.abs(angle* (Math.PI / 180))));
    }

    public float getAngle() {
        return angle;
    }

    public String getLengthAsStringUnitMeter() {
        return String.format("%.2f",getLengh()/100);
    }
}
