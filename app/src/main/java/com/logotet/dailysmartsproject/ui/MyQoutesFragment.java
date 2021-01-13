package com.logotet.dailysmartsproject.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.logotet.dailysmartsproject.R;
import com.logotet.dailysmartsproject.adapters.Quote;
import com.logotet.dailysmartsproject.adapters.QuoteAdapter;
import com.logotet.dailysmartsproject.data.local.DatabaseClient;
import com.logotet.dailysmartsproject.data.local.QuoteEntity;
import com.logotet.dailysmartsproject.databinding.FragmentMyQoutesBinding;
import com.logotet.dailysmartsproject.ui.models.DailyQuoteFragmentViewModel;
import com.logotet.dailysmartsproject.ui.models.MyQuotesFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyQoutesFragment extends Fragment {

    private MyQuotesFragmentViewModel viewModel;
    private OnMyQouteInteractionListener mListener;
    private FragmentMyQoutesBinding binding;
    private List<Quote> quotes = new ArrayList<>();
//    private List<QuoteEntity> quoteEntitiesList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_qoutes, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MyQuotesFragmentViewModel.class);

        QuoteAdapter adapter = new QuoteAdapter(quotes, getQuoteInteractionListener());

        viewModel.getQuotesFromLocalDB().observe(getViewLifecycleOwner(), quoteEntities -> {
            for (int i = 0; i < quoteEntities.size(); i++) {
                QuoteEntity entity = quoteEntities.get(i);
                Quote quote = new Quote();
                quote.setText(entity.getQuoteText());
                quote.setAuthor(entity.getAuthor());
                quotes.add(0, quote);
            }
            adapter.updateData(quotes);
        });

        binding.recViewMyQuotes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewMyQuotes.setAdapter(adapter);
//        binding.recViewMyQuotes.scrollToPosition(0);
    }

    private QuoteAdapter.QuoteHolder.OnQuoteInteractionListener getQuoteInteractionListener() {
        return new QuoteAdapter.QuoteHolder.OnQuoteInteractionListener() {
            @Override
            public void onFavButtonClicked(Quote item) {
                deleteQuoteFromList(item);
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


    private void deleteQuoteFromList(Quote item) {
        viewModel.deleteQuoteFromDb(item.getText());
        int position = quotes.indexOf(item);
        //TODO: The following 3 lines keep the app from crashing but will delete not the right quote!
        if (position < 0) {
            position += 1;
        }
        quotes.remove(position);
        binding.recViewMyQuotes.getAdapter().notifyItemRemoved(position);
    }


    public void updateMyQuotesList(QuoteEntity entity) {
        if (entity != null) {
            Quote quote = new Quote();
            quote.setText(entity.getQuoteText());
            quote.setAuthor(entity.getAuthor());
            quotes.add(quote);
            binding.recViewMyQuotes.getAdapter().notifyItemInserted(0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyQouteInteractionListener) {
            mListener = (OnMyQouteInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMyQouteInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMyQouteInteractionListener {
        // TODO: Update argument type and name
        void onMyQoutesInteraction(Uri uri);
    }
}
