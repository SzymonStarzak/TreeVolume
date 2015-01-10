package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class GetTreeLengthActivity extends Activity {

    private float distance;

    private Camera mCamera = null;

    private CameraPreview mPreview;

    MySensors mySensors;

    Handler customHandler;

    ObjectLength objectLength;

    float azimuth_angle1;

    float azimuth_angle2;

    int touch_counter;

    @Override
    protected void onStop() {
        super.onStop();
        mCamera.release();
        mySensors.releaseSensors();
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            TextView tf = (TextView) findViewById(R.id.textView);
            tf.setTextColor(Color.RED);

            if (touch_counter == 0) {
                tf.setText("0");
                azimuth_angle1 = mySensors.getAzimuth_angle();
            } else {
                azimuth_angle2 = mySensors.getAzimuth_angle();

                objectLength = new ObjectLength(getApplicationContext(), azimuth_angle1, azimuth_angle2, distance);
                tf.setText(objectLength.getLengthAsStringUnitMeter() + " m");
            }
            customHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (touch_counter == 2) {
            Measurement measurement = Measurement.listAll(Measurement.class).get(0);
            measurement.setObject_length(objectLength.getLengh());
            measurement.save();

            mCamera.release();
            mySensors.releaseSensors();

            Intent intent = new Intent(getApplicationContext(), DbActivity.class);
            startActivity(intent);
        }
        touch_counter++;
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* mySensors.releaseSensors();
        mCamera.release();
        finish();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tree_length);

        distance = getIntent().getFloatExtra("distance", 0);

        touch_counter = 0;

        mCamera = Camera.open();

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

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        mySensors = new MySensors(this);
        mySensors.registerSensors();

        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);

    }


}