package com.shaary.neverforget.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.shaary.neverforget.model.Gratitude;

import java.util.List;
import java.util.UUID;

@Dao
public interface GratitudeDao {
    @Query("select * from gratitude")
    List<Gratitude> getAll();

    @Query("select * from gratitude where id = :id")
    Gratitude getGrudgeById(UUID id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Gratitude gratitude);

    @Query("delete from gratitude where id = :id")
    void deleteGrudgeById(UUID id);
}
