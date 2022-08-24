package com.example.cryptocurrenciesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cryptocurrenciesapp.adapters.CurrencyAdapter;
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
            adapter = new CurrencyAdapter( data, new CurrencyAdapter.CurrencyClickListener() {
                @Override
                public void onCurrencyClick(Currency currency) {
                            Intent intent = new Intent(getActivity(), CurrencyActivity.class);
                            intent.putExtra(CurrencyActivity.CURRENCY_INTENT_KEY, currency);
                            startActivity(intent);
                    //starter(getActivity(), 1, currency);
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

    public static void starter(Activity activity, int code, Currency currency) {
        Intent intent = new Intent(activity, CurrencyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CurrencyActivity.CURRENCY_INTENT_KEY, (Serializable) currency);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, code);
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