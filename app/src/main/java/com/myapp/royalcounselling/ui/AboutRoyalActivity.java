package com.myapp.royalcounselling.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.myapp.royalcounselling.R;
import com.myapp.royalcounselling.Utils;
import com.myapp.royalcounselling.VolleySingleton;

import org.json.JSONObject;

public class AboutRoyalActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYAPP", Context.MODE_PRIVATE);

//        Toast.makeText(getContext(), "hey there", Toast.LENGTH_LONG).show();

        String counsellingID = sharedPreferences.getString("counsellingID", "");
        String email = sharedPreferences.getString("KEY_EMAIL", "");

        String urlPost = Utils.main_url + "requestForPersonalCounselling/" + email + "/" + counsellingID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlPost, response -> {


            try {
                JSONObject jsonObject = new JSONObject(response);
                int status = jsonObject.getInt("status");

                if (status == 200) {
                    Toast.makeText(getContext(), "Personal counselling session booked. You will receive notification once the admin approves.", Toast.LENGTH_LONG).show();
                } else if(status == -1){
                    Toast.makeText(getContext(), "Slot already booked by someone!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Error please try again after some time!!", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(getContext(), "Error Please try again!!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> Log.e("api error", "something went wrong" + error)
        ) {


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


        return rootView;
    }
}