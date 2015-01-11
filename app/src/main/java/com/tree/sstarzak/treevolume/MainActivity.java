package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Measurement measurement = new Measurement(0, 0, 0, 0, 0);
        measurement.save();

        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        Intent intent = null;
        switch (b.getId()) {
            case R.id.button:  intent = new Intent(this, GetHeightActivity.class); break;
            case R.id.button2: intent = new Intent(this, GetDistanceActivity.class);break;
            case R.id.button3: intent = new Intent(this, GetTreeLengthActivity.class);break;
            case R.id.button4: intent = new Intent(this, TakingPhotoActivity1.class);break;
            case R.id.button5: intent = new Intent(this, TakingPhotoActivity2.class); break;
        }
        startActivity(intent);
    }
}
