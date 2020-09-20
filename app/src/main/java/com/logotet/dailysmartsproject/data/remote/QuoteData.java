package com.logotet.dailysmartsproject.data.remote;

import androidx.annotation.NonNull;

public class QuoteData{
    private String quoteText;
    private String quoteAuthor;

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    @NonNull
    @Override
    public String toString() {
        return quoteAuthor + " " + quoteText;
    }
}
