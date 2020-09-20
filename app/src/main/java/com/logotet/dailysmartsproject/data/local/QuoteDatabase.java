package com.logotet.dailysmartsproject.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {QuoteEntity.class}, version = 2, exportSchema = false)
public abstract class QuoteDatabase extends RoomDatabase {

    public abstract QuoteDao quoteDao();
}
