package com.shaary.neverforget.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.shaary.neverforget.database.MyDatabase;

import java.io.File;
import java.util.List;

public class GrudgePit {

    private static GrudgePit sGrudgePit;
    private Context context;
    private MyDatabase database;

    public static GrudgePit getInstance(Context context) {
        if (sGrudgePit == null) {
            sGrudgePit = new GrudgePit(context);
        }
        return sGrudgePit;
    }

    private GrudgePit(Context context) {
        this.context = context.getApplicationContext();
        //TODO: move to back thread
        database = Room.databaseBuilder(context, MyDatabase.class, "task-db")
                .allowMainThreadQueries()
                .build();
    }

    public long addGrudge(Grudge grudge) {
        return database.grudgeDao().insert(grudge);
    }

    public void updateGrudge(Grudge grudge) {
        database.grudgeDao().update(grudge);
    }

    public List<Grudge> getGrudgeList() {
        return database.grudgeDao().getAll();
    }

    public Grudge getGrudge(long id) {
        return database.grudgeDao().getGrudgeById(id);
    }

    public void deleteGrudgeById(long id) {
        database.grudgeDao().deleteGrudgeById(id);
    }

    public File getPhotoFile(Grudge grudge) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, grudge.getPhotoFilename());
    }
}
