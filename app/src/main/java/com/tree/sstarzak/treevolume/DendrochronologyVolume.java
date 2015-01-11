package com.tree.sstarzak.treevolume;

/**
 * Created by sstarzak on 2015-01-11.
 */
public class DendrochronologyVolume {
    private float x1; //A
    private float y1; //A
    private float x2; //B
    private float y2; //B
    private float x3; //D
    private float y3; //D
    private float x4; //C
    private float y4; //C
    private float distance;

    //czworokąt ABCD
    public DendrochronologyVolume(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float distance) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        this.distance = distance;
    }

    public float getAB() {
        return getXLengh(x2, x1);
    }

    public float getDC() {
        return getXLengh(x3, x4);
    }

    public float getAD() {
        return getYLength(y1,y4);
    }

    public float getBC() {
        return getYLength(y2,y3);
    }

    public float getXLengh(float x_2, float x_1) {
        float angle;
        if (Math.abs(x_2 - x_1) > 180) {
            if (x_1 > x_2)
                angle = 360 - x_1 + x_2;
            else
                angle = 360 - x_2 + x_1;
        } else {
            angle = Math.abs(x_2 - x_1);
        }
        return (float) (2 * distance * Math.tan(0.5 * Math.abs(angle * (Math.PI / 180))));
    }

    public float getYLength(float y_2, float y_1) {
        float angle = Math.abs(y_2 - y_1);
        return (float) (2 * distance * Math.tan(0.5 * Math.abs(angle * (Math.PI / 180))));
    }

    public float getVolume() {
        return ((getAB() + getDC()))/2 * ((getAD()+getBC())/2);
    }
}
