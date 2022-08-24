package com.example.cryptocurrenciesapp.view;

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
import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.viewmodel.ListViewModel;


public class PortfolioFragment extends Fragment {
    private ListViewModel viewModel;
    private CurrencyAdapter adapter;
    private RecyclerView recyclerView;

    public PortfolioFragment() {
        // Required empty public constructor
    }

    public static PortfolioFragment newInstance() {
        return new PortfolioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        recyclerView = view.findViewById(R.id.cryptocurrencyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getPortfolio().observe(getViewLifecycleOwner(), data -> {
            adapter = new CurrencyAdapter(data, new CurrencyAdapter.CurrencyClickListener() {
                @Override
                public void onCurrencyClick(Currency currency) {
                    Intent intent = new Intent(getActivity(), CurrencyActivity.class);
                    intent.putExtra(CurrencyActivity.CURRENCY_INTENT_KEY, currency);
                    startActivity(intent);
                }

                @Override
                public void onPortfolioClick(Currency currency, boolean checked) {
                    viewModel.handlePortfolioChange(currency, checked);
                }
            });
            recyclerView.setAdapter(adapter);
        });
        return view;
    }

}