package com.example.cryptocurrenciesapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrenciesapp.R;
import com.example.cryptocurrenciesapp.view.adapter.CurrencyAdapter;
import com.example.cryptocurrenciesapp.data.Database;
import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.viewmodel.ListViewModel;
import com.example.cryptocurrenciesapp.viewmodel.ListViewModelFactory;

import java.io.Serializable;

public class MarketFragment extends Fragment {
    private ListViewModel viewModel;
    private CurrencyAdapter adapter;

    public MarketFragment() {
        // Required empty public constructor
    }

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(requireActivity(),
                new ListViewModelFactory(new Database(requireActivity()))).get(ListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.cryptocurrencyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getMarket().observe(getViewLifecycleOwner(), data -> {
            adapter = new CurrencyAdapter(data, new CurrencyAdapter.CurrencyClickListener() {
                @Override
                public void onCurrencyClick(Currency currency) {
                    starter(getActivity(), currency);
                }


                @Override
                public void onPortfolioClick(Currency currency, boolean checked) {
                    viewModel.handlePortfolioChange(currency, checked);
                }
            });
            viewModel.checkMarketInPortfolio();
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        });
        viewModel.getPortfolio().observe(getViewLifecycleOwner(), data -> refreshView());
        return view;
    }

    public void starter(Activity activity, Currency currency) {
        Intent intent = new Intent(activity, CurrencyActivity.class);
        intent.putExtra(CurrencyActivity.CURRENCY_INTENT_KEY, currency);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {
        viewModel.checkMarketInPortfolio();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}