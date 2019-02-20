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
public class Gratitude {
    @PrimaryKey(autoGenerate = true) private long id;
    private String title;
    private Date date;
    private int years;
    private boolean remind;
    private boolean isRevenge;
    private boolean isRevenged;
    private boolean isForgiven = false;
    private String person;
    private String description;
    private String gender;
    private String time;

    //To create new
    public Gratitude() {
        date = new Date();
        time = getTimeNow();
    }

    private String getTimeNow() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getFormat(), Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getTime() {
        return time;
    }

    public void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDay(), hour, minute);
        long t = calendar.getTimeInMillis();

        String pattern = getFormat();
        Time tme = new Time(t);
        Format formatter = new SimpleDateFormat(pattern);

        this.time = formatter.format(tme);
    }

    @NonNull
    private String getFormat() {
        Locale locale = Locale.getDefault();

        String pattern = "";
        // check language
        if ("ru".equals(locale.getLanguage())) {
            // russian
            pattern = "HH:mm";
        } else {
            pattern = "h:mm a";
        }
        return pattern;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFormattedTime(String time) {
        this.time = time;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
