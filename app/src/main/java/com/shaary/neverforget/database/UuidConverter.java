package com.shaary.neverforget.database;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class UuidConverter {
    @TypeConverter
    public static String convertToString(UUID id) {
        return id.toString();
    }

    @TypeConverter
    public static UUID toUuid(String id) {
        return UUID.fromString(id);
    }
}
