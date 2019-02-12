package com.shaary.neverforget.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shaary.neverforget.model.Grudge;

import java.util.List;
import java.util.UUID;

@Dao
public interface GrudgeDao {
    @Query("select * from grudge")
    List<Grudge> getAll();

    @Query("select * from grudge where id = :id")
    Grudge getGrudgeById(long id);

    @Insert
    long insert(Grudge grudge);

    @Update
    void update(Grudge grudge);

    @Query("delete from grudge where id = :id")
    void deleteGrudgeById(long id);

}
