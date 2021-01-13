package com.logotet.dailysmartsproject.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuoteDao {

    @Insert
    void insertQuote(QuoteEntity quoteEntity);

    @Query("SELECT * FROM quotes")
    LiveData<List<QuoteEntity>> getAllQuotes();

    @Query("DELETE FROM quotes WHERE quote_text = :quoteText")
    void deleteByText(String quoteText);

    @Query("SELECT * FROM quotes WHERE quote_text LIKE :quoteText")
    LiveData<QuoteEntity> getQuoteByText(String quoteText);

    @Delete
    void deleteQuote(QuoteEntity quoteEntity);

    @Delete
    void deleteAll(QuoteEntity... entities);

}
