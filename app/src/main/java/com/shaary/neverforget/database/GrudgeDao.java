package com.shaary.neverforget.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.shaary.neverforget.model.Grudge;

import java.util.List;
import java.util.UUID;

@Dao
public interface GrudgeDao {
    @Query("select * from grudge")
    LiveData<List<Grudge>> getAll();

    @Query("select * from grudge where id = :id")
    Grudge getGrudgeById(UUID id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Grudge grudge);

    @Query("delete from grudge where id = :id")
    void deleteGrudgeById(UUID id);

    @Delete
    void delete(Grudge grudge);
}
