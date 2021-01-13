package com.logotet.dailysmartsproject.data.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

public class DatabaseClient {

    private static DatabaseClient client;
    private static QuoteDatabase db;

    private DatabaseClient(Context context) {
        db = Room.databaseBuilder(context, QuoteDatabase.class, "quote-database.db")
                //TODO: make the methods below asynchornous to remove them from the main thread
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {

                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    public static DatabaseClient getInstance(Context context) {
        if (client == null) {
            client = new DatabaseClient(context);
        }
        return client;
    }

    public void insertQuote(QuoteEntity entity) {
        db.quoteDao().insertQuote(entity);
    }

    public LiveData<QuoteEntity> displayQuote(String quoteText) {
        return db.quoteDao().getQuoteByText(quoteText);
    }

    public void deleteAll() {
        db.quoteDao().deleteAll();
    }

    public void deleteSingleQuote(String text){
        db.quoteDao().deleteByText(text);
    }


    public LiveData<List<QuoteEntity>> getAllQuotes() {
        return db.quoteDao().getAllQuotes();
    }


}
