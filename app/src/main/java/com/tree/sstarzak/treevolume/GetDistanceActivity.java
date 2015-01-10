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


public class GetDistanceActivity extends Activity  {

    private Camera mCamera = null;

    private CameraPreview mPreview;

    MySensors mySensors;

    Handler customHandler;

    Integer measure_device_height;

    ObjectDistance objectDistance;

    @Override
    protected void onStop() {
        super.onStop();
        /*mCamera.release();
        mySensors.releaseSensors();*/
    }

    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            objectDistance = new ObjectDistance(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle(),measure_device_height);
            TextView tf = (TextView) findViewById(R.id.textView);
            tf.setTextColor(Color.RED);
            tf.setText(objectDistance.getDistanceAsStringUnitMeter() + " m");
            customHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Measurement measurement = Measurement.listAll(Measurement.class).get(0);
        measurement.setDistance_from_side(objectDistance.getDistance());
        measurement.save();

        mCamera.release();
        mySensors.releaseSensors();
        objectDistance = new ObjectDistance(getApplicationContext(), mySensors.getPitch_angle(), mySensors.getRoll_angle(),measure_device_height);
        Intent intent = new Intent(getApplicationContext(), GetTreeLengthActivity.class);
        intent.putExtra("distance", objectDistance.getDistance());
        startActivity(intent);

        return super.onTouchEvent(event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_prev);

        measure_device_height = getIntent().getIntExtra("height",150);

        mCamera = Camera.open();
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch ( display.getRotation()){
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
        customHandler.postDelayed(updateTimerThread,0);

    }

}
