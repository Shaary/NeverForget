package com.shaary.neverforget.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Grudge {

    private UUID id;
    private String title;
    private Date date;
    private int years;
    private boolean remind;
    private boolean isRevenge;
    private boolean isRevenged;
    private boolean isForgiven = false;
    private String victim;
    private String description;
    private String gender;

    public Grudge() {
        this.id = UUID.randomUUID();
        date = new Date();
    }

    public Grudge(UUID id) {
        this.id = id;
        date = new Date();
    }

    public UUID getId() {
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

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
