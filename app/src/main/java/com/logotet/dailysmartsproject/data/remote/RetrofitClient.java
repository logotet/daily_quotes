package com.logotet.dailysmartsproject.data.remote;

import android.content.Context;
import android.widget.Toast;

import com.logotet.dailysmartsproject.adapters.Quote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient client;
    private Retrofit retrofit;
    private QuoteModel model;
    private Quote quote;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quotable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (client == null) {
            client = new RetrofitClient();
        }
        return client;
    }

    public void getQuote(Context context, DataListener listener) {
        QuoteService service = retrofit.create(QuoteService.class);
        Call<QuoteModel> call = service.getRandomQuote();
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    listener.onDataReceived(model);
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuoteModel> call, Throwable t) {
                Toast.makeText(context, "ERROR FAILURE", Toast.LENGTH_LONG).show();
            }
        });
    }
    
    public interface DataListener {
        void onDataReceived(QuoteModel quoteModel);
    }

}
