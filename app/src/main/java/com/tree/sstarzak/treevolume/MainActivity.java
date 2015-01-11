package com.tree.sstarzak.treevolume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button1, button2, button3, button4, button5,button6, button7, button8;

    @Override
    protected void onResume() {
        super.onResume();
        Measurement m = Measurement.listAll(Measurement.class).get(0);
        TextView tv = (TextView) findViewById(R.id.textView);

        tv.setText(String .valueOf(m.getVolume_back_side()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Measurement measurement = new Measurement(0, 0, 0, 0, 0, 0, 0, 0, 0);
        measurement.save();

        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);

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
        switch (b.getId()) {
            case R.id.button:  intent = new Intent(this, GetHeightActivity.class); break;
            case R.id.button2:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type",0);
                break;
            case R.id.button3: intent = new Intent(this, GetTreeLengthActivity.class);break;
            case R.id.button4:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type",1);
                break;
            case R.id.button5:
                intent = new Intent(this, GetDendrochronology.class);
                intent.putExtra("m_type",0);
                break;
            case R.id.button6:
                intent = new Intent(this, GetDistanceActivity.class);
                intent.putExtra("m_type",2);
                break;
            case R.id.button7:
                intent = new Intent(this, GetDendrochronology.class);
                intent.putExtra("m_type",1);
                break;
            case R.id.button8: intent = new Intent(this, TakingPhotoActivity2.class); break;
        }
        startActivity(intent);
    }
}
