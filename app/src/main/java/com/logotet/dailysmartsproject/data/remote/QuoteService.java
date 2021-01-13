package com.logotet.dailysmartsproject.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteService {

    @GET("random")
    Call<QuoteModel> getRandomQuote();
}
