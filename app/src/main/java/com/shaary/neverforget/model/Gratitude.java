package com.shaary.neverforget.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Entity
public class Gratitude extends Event{

    private boolean isReturnGood;
    private boolean isReturned;

    //To create new
    public Gratitude() {
        super();
    }

    public boolean isReturnGood() {
        return isReturnGood;
    }

    public void setReturnGood(boolean makeGood) {
        isReturnGood = makeGood;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean goodReturned) {
        isReturned = goodReturned;
    }

}
