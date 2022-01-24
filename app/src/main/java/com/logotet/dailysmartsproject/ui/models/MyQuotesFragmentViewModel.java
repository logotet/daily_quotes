package com.logotet.dailysmartsproject.ui.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logotet.dailysmartsproject.adapters.Quote;
import com.logotet.dailysmartsproject.data.local.DatabaseClient;
import com.logotet.dailysmartsproject.data.local.QuoteEntity;

import java.util.List;

public class MyQuotesFragmentViewModel extends AndroidViewModel {

    private DatabaseClient dbi;

    public MyQuotesFragmentViewModel(@NonNull Application application) {
        super(application);
        dbi = DatabaseClient.getInstance(getApplication().getApplicationContext());
    }

    public LiveData<List<QuoteEntity>> getQuotesFromLocalDB() {
        return dbi.getAllQuotes();
    }

    public void deleteQuoteFromDb(String text){
        dbi.deleteSingleQuote(text);
    }


}
