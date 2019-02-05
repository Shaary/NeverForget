package com.shaary.neverforget.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.shaary.neverforget.database.MyDatabase;

import java.util.List;
import java.util.UUID;

public class GratitudeList {
    private static GratitudeList sGratitudeList;
    private List<Gratitude> tasks;
    private Context context;
    private MyDatabase database;

    public static GratitudeList getInstance(Context context) {
        if (sGratitudeList == null) {
            sGratitudeList = new GratitudeList(context);
        }
        return sGratitudeList;
    }

    private GratitudeList(Context context) {
        this.context = context.getApplicationContext();
        //TODO: move to back thread
        database = Room.databaseBuilder(context, MyDatabase.class, "task-db")
                .allowMainThreadQueries()
                .build();
    }

    public void addGratitude(Gratitude gratitude) {
        database.gratitudeDao().insert(gratitude);
    }

    public List<Gratitude> getGratitudeList() {
        return database.gratitudeDao().getAll();
    }

    public Gratitude getGratitude(UUID id) {
        return database.gratitudeDao().getGrudgeById(id);
    }

    public void deleteGratitudeById(UUID id) {
        database.gratitudeDao().deleteGrudgeById(id);
    }
}
