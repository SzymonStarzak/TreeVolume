package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button1, button2, button3, button4, button5, button6, button7, button8, button_info;

    @Override
    protected void onResume() {
        super.onResume();
        Measurement m = Measurement.listAll(Measurement.class).get(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.deleteDatabase("measurement.db");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Measurement measurement = new Measurement(0, 0, 0, 0, 0, 0, 0, 0, 0);
        measurement.save();

        button_info = (Button) findViewById(R.id.button_info);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);

        button_info.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        Intent intent = null;
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#32cd32"));
        gd.setStroke(1, Color.BLACK);

        switch (b.getId()) {
            case R.id.button_info:
                intent = new Intent(this, HowToMeasureActivity.class);
                break;
            case R.id.button:
                intent = new Intent(this, GetHeightActivity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type", 0);
                break;
            case R.id.button3:
                intent = new Intent(this, GetTreeLengthActivity.class);
                break;
            case R.id.button4:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type", 1);
                break;
            case R.id.button5:
                intent = new Intent(this, GetDendrochronology2.class);
                intent.putExtra("d_type", 0);
                break;
            case R.id.button6:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type", 2);
                break;
            case R.id.button7:
                intent = new Intent(this, GetDendrochronology2.class);
                intent.putExtra("d_type", 1);
                break;
            case R.id.button8:
                Measurement m = Measurement.listAll(Measurement.class).get(0);

                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("");
                tv.append("Device height: " + String.valueOf(m.getDevice_height()/100) + " m\n" );
                tv.append("Distance from side: " + String.valueOf(m.getDistance_from_side()/100)+ " m\n");
                tv.append("Object length: " + String.valueOf(m.getObject_length()/100)+ " m\n");
                tv.append("Distance from back: " + String.valueOf(m.getDistance_from_first_d()/100)+ " m\n");
                tv.append("Distance from front: " + String.valueOf(m.getDistance_from_secont_d()/100)+ " m\n");
                tv.append("Surface max area1: " + String.valueOf(m.getMax_volume_back_side()/100)+ " m\n");
                tv.append("Surface max area2: " + String.valueOf(m.getMax_volume_front_side()/100)+ " m\n");
                tv.append("Max Object Volume: " + String.valueOf(
                        ((m.getMax_volume_back_side() / 100 + m.getMax_volume_front_side() / 100) / 2) * (m.getObject_length() / 100)
                ) + " m^3\n");
                tv.append("Estimated Tree Volume: " + String.valueOf(
                                ((m.getVolume_back_side() / 100 + m.getVolume_front_side() / 100) / 2) * (m.getObject_length() / 100)
                        ) + " m^3\n"
                );
                break;
        }
        if (intent != null)
            startActivity(intent);
        b.setBackground(gd);
    }
}
