package com.logotet.dailysmartsproject.data.remote;

import androidx.annotation.NonNull;

public class QuoteModel {
    private String content;
    private String author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NonNull
    @Override
    public String toString() {
        return author + " " + content;
    }
}
