package com.logotet.dailysmartsproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.dailysmartsproject.R;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteHolder> {

    List<Quote> quotes;
    QuoteHolder.OnQuoteInteractionListener listener;

    public QuoteAdapter(List<Quote> quotes, QuoteHolder.OnQuoteInteractionListener listener) {
        this.quotes = quotes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_quote_view, parent, false);
        return new QuoteHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.txtAuthor.setText(quote.getAuthor());
        holder.txtQuote.setText(quote.getText());
        holder.setQuoteItem(quote);
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }


    public static class QuoteHolder extends RecyclerView.ViewHolder {

        TextView txtQuote;
        TextView txtAuthor;
        ImageView btnFav;
        ImageView btnShare;
        Quote item;


        public QuoteHolder(@NonNull View itemView, OnQuoteInteractionListener listener) {
            super(itemView);
            txtQuote = itemView.findViewById(R.id.txt_qoute);
            txtAuthor = itemView.findViewById(R.id.txt_author);
            btnFav = itemView.findViewById(R.id.btn_fav);
            btnShare = itemView.findViewById(R.id.btn_share);
            item = new Quote();

            btnFav.setOnClickListener(v -> {
                //TODO: Some boolean value should be passed to the fragment in order to know the state of the button.
                listener.onFavButtonClicked(item);
                changeFavBtnIcon();
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onShareClicked(item);
                }
            });
        }

        public void setQuoteItem(Quote item) {
            this.item = item;
        }

        public interface OnQuoteInteractionListener {
            void onFavButtonClicked(Quote item);
            void onShareClicked(Quote item);
        }

        public void changeFavBtnIcon() {
            if (btnFav.isPressed()) {
                btnFav.setImageResource(R.drawable.ic_favorite_black_24px);
            }else{
                btnFav.setImageResource(R.drawable.ic_favorite_border_black_24px);
            }
        }

    }


}
