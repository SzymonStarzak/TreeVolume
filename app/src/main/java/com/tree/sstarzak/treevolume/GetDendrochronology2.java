package com.tree.sstarzak.treevolume;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class GetDendrochronology2 extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    int ID_RES = R.drawable.sloje2;
    SeekBar sb1;
    SeekBar sb2;
    SeekBar sb3;
    SeekBar sb4;
    SeekBar sb5;
    Bitmap theOriginalImage;
    Bitmap binarizedImage;
    ImageView iv;
    TextView tv;
    int contrast = 0;
    int brightness = 0;
    int all_px;
    int white_px;
    private final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_dendrochronology2);

        sb1 = (SeekBar) findViewById(R.id.seekBar1);
        sb1.setOnSeekBarChangeListener(this);
        sb1.setMax(255);

        sb2 = (SeekBar) findViewById(R.id.seekBar2);
        sb2.setOnSeekBarChangeListener(this);
        sb2.setMax(255);

        sb3 = (SeekBar) findViewById(R.id.seekBar3);
        sb3.setOnSeekBarChangeListener(this);
        sb3.setMax(9);

        sb4 = (SeekBar) findViewById(R.id.seekBar4);
        sb4.setOnSeekBarChangeListener(this);
        sb4.setMax(255);

        sb5 = (SeekBar) findViewById(R.id.seekBar5);
        sb5.setOnSeekBarChangeListener(this);
        sb5.setMax(255);

        theOriginalImage = BitmapFactory.decodeResource(this.getResources(), ID_RES);
        binarizedImage = theOriginalImage.copy(theOriginalImage.getConfig(), true);

        tv = (TextView) findViewById(R.id.textView1);

        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), ID_RES));
        iv.setOnClickListener(this);
    }

    private boolean shouldBeBlack(int pixel) {
        int alpha = Color.alpha(pixel);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        if (redValue < 255 - sb1.getProgress() && greenValue < 255 - sb1.getProgress() ||
                Math.abs(redValue - greenValue) > 20 + sb2.getProgress() ||
                blueValue > 255 - sb5.getProgress())
            return true;
        else
            return false;
    }

    public Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekBar3:
                contrast = i + 1;
                break;
            case R.id.seekBar4:
                brightness = i;
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        binarizedImage = changeBitmapContrastBrightness(
                theOriginalImage.copy(theOriginalImage.getConfig(), true),
                contrast,
                brightness);
        all_px = white_px = 0;
        all_px = binarizedImage.getWidth() * binarizedImage.getHeight();
        for (int i = 0; i < binarizedImage.getWidth(); i++) {
            for (int c = 0; c < binarizedImage.getHeight(); c++) {
                int pixel = binarizedImage.getPixel(i, c);
                if (shouldBeBlack(pixel)) {
                    binarizedImage.setPixel(i, c, Color.BLACK);
                } else {
                    binarizedImage.setPixel(i, c, Color.WHITE);
                    white_px++;
                }
            }
        }
        tv.setText(String.valueOf(white_px) + '/' + String.valueOf(all_px));
        iv.setImageBitmap(binarizedImage);
    }

    @Override
    public void onClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/jpg");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        theOriginalImage = BitmapFactory.decodeStream(imageStream);
                        iv.setImageBitmap(theOriginalImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
