package com.logotet.dailysmartsproject.ui.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.logotet.dailysmartsproject.data.local.DatabaseClient;
import com.logotet.dailysmartsproject.data.local.QuoteEntity;
import com.logotet.dailysmartsproject.data.remote.RetrofitClient;

public class DailyQuoteFragmentViewModel extends AndroidViewModel {
    private RetrofitClient retrofitClient;
    private DatabaseClient dbi;

    public DailyQuoteFragmentViewModel(@NonNull Application application) {
        super(application);
        retrofitClient = RetrofitClient.getInstance();
        dbi = DatabaseClient.getInstance(getApplication().getApplicationContext());
    }

    public void getRandomQuote(RetrofitClient.DataListener listener){
        retrofitClient.getQuote(getApplication().getApplicationContext(), listener);
    }

    public void insertQuote(QuoteEntity entity){
        dbi.insertQuote(entity);
    }
}
