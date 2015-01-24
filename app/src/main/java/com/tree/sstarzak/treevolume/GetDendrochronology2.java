package com.tree.sstarzak.treevolume;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class GetDendrochronology2 extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    int ID_RES = R.drawable.sloje6;
    SeekBar sb1;
    SeekBar sb2;
    SeekBar sb3;
    SeekBar sb4;
    SeekBar sb5;
    SeekBar sb6;
    Bitmap theOriginalImage;
    Bitmap binarizedImage;
    ImageView iv;
    TextView tv;
    Button opening,closeing,erode,dilatate,reset,confirm;
    int contrast = 0;
    int brightness = 0;
    int all_px =1;
    int white_px= 0;
    private final int SELECT_PHOTO = 1;

    private  float GALAXY_S3_CAMERA_AREA_RATION =  4f / 5f;

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

        sb6 = (SeekBar) findViewById(R.id.seekBar6);
        sb6.setOnSeekBarChangeListener(this);
        sb6.setMax(14);

        theOriginalImage = BitmapFactory.decodeResource(this.getResources(), ID_RES);
        binarizedImage = theOriginalImage.copy(theOriginalImage.getConfig(), true);

        tv = (TextView) findViewById(R.id.textView1);

        opening = (Button) findViewById(R.id.open);
        opening.setOnClickListener(this);

        closeing = (Button) findViewById(R.id.close);
        closeing.setOnClickListener(this);

        erode =  (Button) findViewById(R.id.erode);
        erode.setOnClickListener(this);

        dilatate = (Button) findViewById(R.id.dilatate);
        dilatate.setOnClickListener(this);

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);

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

       switch (seekBar.getId()) {
           case R.id.seekBar1:
           case R.id.seekBar2:
           case R.id.seekBar3:
           case R.id.seekBar4:
           case R.id.seekBar5:

               BinarizeImage();
               break;
       }
        tv.setText(String.valueOf(white_px) + '/' + String.valueOf(all_px));
        iv.setImageBitmap(binarizedImage);
    }

    public void BinarizeImage() {
        binarizedImage = changeBitmapContrastBrightness(
                theOriginalImage.copy(theOriginalImage.getConfig(), true),
                contrast,
                brightness);
        white_px = 0;
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
    }
    @Override
    public void onClick(View view) {
        System.loadLibrary("opencv_java");
        switch (view.getId()) {
            case R.id.imageView1:
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/jpg");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
            default:
                Mat src = new Mat(binarizedImage.getWidth(), binarizedImage.getHeight(), CvType.CV_8UC1);
                Utils.bitmapToMat(binarizedImage.copy(binarizedImage.getConfig(),true),src);
                Mat kernel = Imgproc.getStructuringElement( Imgproc.CV_SHAPE_ELLIPSE, new Size(sb6.getProgress()+1,sb6.getProgress()+1));
                Mat dst = new Mat();
                switch (view.getId()) {
                    case R.id.open:
                        Imgproc.morphologyEx(src,dst, Imgproc.MORPH_OPEN, kernel);
                        Utils.matToBitmap(dst, binarizedImage);
                        break;
                    case R.id.close:
                        Imgproc.morphologyEx(src,dst, Imgproc.MORPH_CLOSE, kernel);
                        Utils.matToBitmap(dst, binarizedImage);
                        break;
                    case R.id.erode:
                        Imgproc.erode(src,dst, kernel);
                        Utils.matToBitmap(dst, binarizedImage);
                        break;
                    case R.id.dilatate:
                        Imgproc.dilate(src,dst, kernel);
                        Utils.matToBitmap(dst, binarizedImage);
                        break;
                    case R.id.reset:
                        BinarizeImage();
                        break;
                    case R.id.confirm:

                        Measurement measurement = Measurement.listAll(Measurement.class).get(0);
                        if(getIntent().getIntExtra("d_type",0) == 0)
                         measurement.setVolume_first_side((float) (((float) white_px / all_px) * GALAXY_S3_CAMERA_AREA_RATION * Math.pow(measurement.getDistance_from_first_d(), 2.0)));
                        else
                            measurement.setVolume_second_side((float) (((float) white_px / all_px) * GALAXY_S3_CAMERA_AREA_RATION * Math.pow(measurement.getDistance_from_second_d(), 2.0)));

                        measurement.save();
                        finish();
                        break;
                }
                iv.setImageBitmap(binarizedImage);
                break;
        }
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
