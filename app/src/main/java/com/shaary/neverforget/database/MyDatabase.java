package com.shaary.neverforget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.shaary.neverforget.model.Gratitude;
import com.shaary.neverforget.model.Grudge;

@Database(entities = {Grudge.class, Gratitude.class}, version = 1)
@TypeConverters({UuidConverter.class, DateConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract GrudgeDao grudgeDao();
    public abstract GratitudeDao gratitudeDao();

    private static volatile MyDatabase INSTANCE;

    static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "my_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
