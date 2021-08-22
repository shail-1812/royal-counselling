package com.myapp.royalcounselling.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.myapp.royalcounselling.R;
import com.myapp.royalcounselling.Utils;


public class PersonalCounsellingFragment extends Fragment {


    CalendarView calendar;
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_counselling, container, false);
        calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(
                        (view, year, month, dayOfMonth) -> {


                            date
                                    = dayOfMonth + "-"
                                    + (month + 1) + "-" + year;

                            Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
                        });

        String urlGet = Utils.main_url;
        urlGet = urlGet.concat("something");


        return rootView;
    }
}