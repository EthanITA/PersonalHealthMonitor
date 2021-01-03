package com.dong.personalhealthmonitor.fragments;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.generic.CalendarManager;
import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.dong.personalhealthmonitor.java_classes.adapters.CardsAdapter;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.textview.MaterialTextView;

public class DayCardFragment extends Fragment {

    ListView lvCards;
    CardsAdapter adapter;
    ReportDB rps;
    SettingsOperations prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_day, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout refresh = view.findViewById(R.id.refresh);
        rps = new ReportDB(view.getContext());
        prefs = new SettingsOperations(view.getContext());
        rps.db.open();
        ((MaterialTextView)view.findViewById(R.id.selected_day_fragment_card)).setText(new CalendarManager().getMillisToDate(prefs.getSelectedDayMillis()));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCards(view);
                refresh.setRefreshing(false);
            }
        });
        updateCards(view);
    }

    void updateCards(View view) {
        lvCards = view.findViewById(R.id.list_cards);
        adapter = new CardsAdapter(view.getContext());
        lvCards.setAdapter(adapter);
        for (Report rp :
                rps.getReportsDay(prefs.getSelectedDayMillis())) {
            adapter.add(rp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rps.db.close();
    }
}