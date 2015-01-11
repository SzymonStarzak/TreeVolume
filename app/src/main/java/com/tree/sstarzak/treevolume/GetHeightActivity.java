package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class GetHeightActivity extends Activity implements View.OnClickListener {

    Button next;

    SeekBar seekBar;

    TextView height_value_tv;

    Integer height_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_height);

        this.deleteDatabase("measurement.db");

        height_value = 150;

        next = (Button) findViewById(R.id.next_button);
        seekBar = (SeekBar) findViewById(R.id.height_seekbar);

        next.setOnClickListener(this);

        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                height_value = 100 + i;
                height_value_tv.setText(height_value.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        height_value_tv = (TextView) findViewById(R.id.height_value);
        height_value_tv.setText(String.valueOf(seekBar.getProgress() + 100));
    }


    @Override
    public void onClick(View view) {

        Measurement measurement = Measurement.listAll(Measurement.class).get(0);
        measurement.setDevice_height(height_value);
        measurement.save();
        finish();
    }
}
