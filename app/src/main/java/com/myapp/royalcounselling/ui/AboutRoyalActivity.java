package com.myapp.royalcounselling.ui;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.royalcounselling.R;

import org.w3c.dom.Text;

public class AboutRoyalActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView aboutRoyal;
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        return rootView;
    }
}