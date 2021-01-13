package com.logotet.dailysmartsproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.logotet.dailysmartsproject.adapters.Quote;
import com.logotet.dailysmartsproject.adapters.QuoteAdapter;
import com.logotet.dailysmartsproject.data.local.DatabaseClient;
import com.logotet.dailysmartsproject.data.local.QuoteEntity;
import com.logotet.dailysmartsproject.data.remote.QuoteModel;
import com.logotet.dailysmartsproject.data.remote.RetrofitClient;
import com.logotet.dailysmartsproject.databinding.FragmentDailyQouteBinding;

import java.util.ArrayList;
import java.util.List;


public class DailyQouteFragment extends Fragment  {


    private OnDailyQouteInteractionListener mListener;

    FragmentDailyQouteBinding binding;

    private RetrofitClient retrofitClient;
    private List<Quote> quotes = new ArrayList<>();
    private DatabaseClient dbi;
    private Quote quote = new Quote();
    private String text = "";
    private String author = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_qoute, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofitClient = RetrofitClient.getInstance();
        dbi = DatabaseClient.getInstance(getContext());
        quotes.add(quote);

        QuoteAdapter adapter = new QuoteAdapter(quotes, getQuoteInteractionListener());
        binding.recViewDaily.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewDaily.setAdapter(adapter);

        retrofitClient.getQuote(getContext(), new RetrofitClient.DataListener() {
            @Override
            public void onDataReceived(QuoteModel quoteModel) {
                DailyQouteFragment.this.addQuote(quoteModel, adapter);
            }
        });

        binding.swiperefresh.setOnRefreshListener(() -> {
            retrofitClient.getQuote(getContext(), quoteModel -> DailyQouteFragment.this.addQuote(quoteModel, adapter));
            binding.swiperefresh.setRefreshing(false);
        });


    }

    private QuoteAdapter.QuoteHolder.OnQuoteInteractionListener getQuoteInteractionListener() {
        return new QuoteAdapter.QuoteHolder.OnQuoteInteractionListener() {
            @Override
            public void onFavButtonClicked(Quote item) {
                String text = item.getText();
                String author = item.getAuthor();
                QuoteEntity entity = new QuoteEntity(text, author);
                dbi.insertQuote(text, author);
                Toast.makeText(getContext(), "Quote inserted", Toast.LENGTH_LONG).show();
                mListener.onDailyQuotesInteraction(entity);
            }

            @Override
            public void onShareClicked(Quote item) {
                String text = item.getText();
                String author = item.getAuthor();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, author);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, null));
            }
        };
    }

    private void addQuote(QuoteModel quoteModel, QuoteAdapter adapter) {
//        text = quoteModel.getQuote().getContent();
//        author = quoteModel.getQuote().getAuthor();
        text = quoteModel.getContent();
        author = quoteModel.getAuthor();
        Quote quoteData = new Quote();
        quoteData.setText(text);
        quoteData.setAuthor(author);
        quotes.remove(0);
        quotes.add(0, quoteData);
        adapter.notifyItemChanged(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDailyQouteInteractionListener) {
            mListener = (OnDailyQouteInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDailyQouteInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnDailyQouteInteractionListener {
        void onDailyQuotesInteraction(QuoteEntity entity);
    }


}
