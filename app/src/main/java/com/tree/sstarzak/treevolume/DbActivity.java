package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class DbActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        Measurement measurement = Measurement.listAll(Measurement.class).get(0);

        TextView tv = (TextView) findViewById(R.id.db_tv);

        tv.setText( String.valueOf(measurement.getDevice_height()) + '\n' +
       String.valueOf(measurement.getDistance_from_side()) + '\n' +
        String.valueOf(measurement.getObject_length()));


    }
}
