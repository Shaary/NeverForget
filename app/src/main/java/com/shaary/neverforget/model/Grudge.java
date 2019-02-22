package com.shaary.neverforget.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Entity
public class Grudge extends Event{

    private boolean isRevenge;
    private boolean isRevenged;
    private boolean isForgiven = false;

    //To create new
    public Grudge() {
        super();
    }

    public boolean isRevenge() {
        return isRevenge;
    }

    public void setRevenge(boolean revenge) {
        isRevenge = revenge;
    }

    public boolean isRevenged() {
        return isRevenged;
    }

    public void setRevenged(boolean revenged) {
        isRevenged = revenged;
    }

    public boolean isForgiven() {
        return isForgiven;
    }

    public void setForgiven(boolean forgiven) {
        isForgiven = forgiven;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }

}
