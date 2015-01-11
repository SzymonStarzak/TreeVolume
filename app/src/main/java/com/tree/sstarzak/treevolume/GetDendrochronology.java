package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GetDendrochronology extends Activity {
    private float distance;

    private Camera mCamera = null;

    private CameraPreview mPreview;

    private MySensors mySensors;

    private Handler customHandler;

    private ObjectLength objectLength;

    private float azimuth_angle1;

    private float azimuth_angle2;

    private float azimuth_angle4;

    private float azimuth_angle3;

    private float y_angle1;

    private float y_angle2;

    private float y_angle3;

    private float y_angle4;

    private int touch_counter;

    private int measurement_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_dendrochronology);

        measurement_type = getIntent().getIntExtra("d_type", 0);

        Measurement measurement = Measurement.listAll(Measurement.class).get(0);
        if (measurement_type == 0) {
            distance = measurement.getDistance_from_first_d();
        } else {
            distance = measurement.getDistance_from_secont_d();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCamera.release();
        mySensors.releaseSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.release();
        mySensors.releaseSensors();
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            TextView tf = (TextView) findViewById(R.id.textView);
            tf.setTextColor(Color.RED);


            tf.setText(String.valueOf(touch_counter/2 + 1));
            customHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Measurement measurement = Measurement.listAll(Measurement.class).get(0);

        DendrochronologyVolume dendrochronologyVolume = null;
        switch (touch_counter) {
            case 0:
                azimuth_angle1 = mySensors.getAzimuth_angle();
                y_angle1 = IdentificationAngleHelper.getAngle(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle());
                break;
            case 2:
                azimuth_angle2 = mySensors.getAzimuth_angle();
                y_angle2 = IdentificationAngleHelper.getAngle(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle());
                break;
            case 4:
                azimuth_angle3 = mySensors.getAzimuth_angle();
                y_angle3 = IdentificationAngleHelper.getAngle(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle());
                break;
            case 6:
                azimuth_angle4 = mySensors.getAzimuth_angle();
                y_angle4 = IdentificationAngleHelper.getAngle(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle());
                dendrochronologyVolume = new DendrochronologyVolume(azimuth_angle1, y_angle1,
                        azimuth_angle2, y_angle2,
                        azimuth_angle3, y_angle3,
                        azimuth_angle4, y_angle4,
                        distance);

                if (measurement_type == 0) {
                    measurement.setVolume_back_side(dendrochronologyVolume.getVolume());
                } else {
                    measurement.setVolume_front_side(dendrochronologyVolume.getVolume());
                }
                measurement.save();
                finish();
                break;
        }
        touch_counter++;
        return super.onTouchEvent(event);
    }

    @Override
    public void onResume() {

        touch_counter = 0;

        mCamera = null;

        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            finish();
        }

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                mCamera.setDisplayOrientation(0);
                break;
            case Surface.ROTATION_0:
                mCamera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_180:
                mCamera.setDisplayOrientation(360);
                break;
        }
        mPreview = new CameraPreview(this, mCamera);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        mySensors = new MySensors(this);
        mySensors.registerSensors();

        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);
        super.onResume();
    }
}
