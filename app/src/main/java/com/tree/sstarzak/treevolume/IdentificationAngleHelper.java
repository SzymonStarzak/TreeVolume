package com.tree.sstarzak.treevolume;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by sstarzak on 2015-01-11.
 */
public class IdentificationAngleHelper {
    static float getAngle(Context context, float pitch_angle, float roll_angle) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float angle = 0;
        switch (display.getRotation()) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                angle = roll_angle;
                break;
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                angle = pitch_angle;
                break;
        }
        return angle;
    }
}
