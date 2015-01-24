package com.tree.sstarzak.treevolume;

import com.orm.SugarRecord;

/**
 * Created by sstarzak on 2015-01-09.
 */
public class Measurement extends SugarRecord<Measurement> {

    private float device_height; //[cm]

    private float distance_from_side;

    private float object_length;

    private float distance_from_first_d;

    private float distance_from_second_d;

    private float volume_second_side;

    private float volume_first_side;

    public float getDevice_height() {
        return device_height;
    }

    public void setDevice_height(float device_height) {
        this.device_height = device_height;
    }

    public float getDistance_from_side() {
        return distance_from_side;
    }

    public void setDistance_from_side(float distance_from_side) {
        this.distance_from_side = distance_from_side;
    }

    public float getObject_length() {
        return object_length;
    }

    public void setObject_length(float object_length) {
        this.object_length = object_length;
    }

    public float getDistance_from_first_d() {
        return distance_from_first_d;
    }

    public void setDistance_from_first_d(float distance_from_first_d) {
        this.distance_from_first_d = distance_from_first_d;
    }

    public float getDistance_from_second_d() {
        return distance_from_second_d;
    }

    public void setDistance_from_second_d(float distance_from_second_d) {
        this.distance_from_second_d = distance_from_second_d;
    }

    public float getVolume_second_side() {
        return volume_second_side;
    }

    public void setVolume_second_side(float volume_second_side) {
        this.volume_second_side = volume_second_side;
    }

    public float getVolume_first_side() {
        return volume_first_side;
    }

    public void setVolume_first_side(float volume_first_side) {
        this.volume_first_side = volume_first_side;
    }

    public Measurement() {
    }

    public Measurement(float device_height, float distance_from_side, float object_length, float distance_from_first_d, float distance_from_second_d, float volume_second_side, float volume_first_side, float max_volume_front_side, float max_volume_back_side) {
        this.device_height = device_height;
        this.distance_from_side = distance_from_side;
        this.object_length = object_length;
        this.distance_from_first_d = distance_from_first_d;
        this.distance_from_second_d = distance_from_second_d;
        this.volume_second_side = volume_second_side;
        this.volume_first_side = volume_first_side;
    }
}
