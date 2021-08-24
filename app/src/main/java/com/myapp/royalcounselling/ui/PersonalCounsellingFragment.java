package com.myapp.royalcounselling.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.myapp.royalcounselling.R;
import com.myapp.royalcounselling.Utils;
import com.myapp.royalcounselling.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PersonalCounsellingFragment extends Fragment {


    CalendarView calendar;
    String date;
    Spinner spinner;
    ArrayList<String> counsellingTime;
    Button counsellingBook;
    String strData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_counselling, container, false);
        spinner = rootView.findViewById(R.id.spinner_time);
        calendar = rootView.findViewById(R.id.calendar);
        counsellingBook = rootView.findViewById(R.id.btn_register_personal);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        calendar.setOnDateChangeListener(
                (view, year, month, dayOfMonth) -> {
                    date
                            = dayOfMonth + "-"
                            + (month + 1) + "-" + year;

                    //Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
                    String urlGet = Utils.main_url + "getAllActiveCounsellingSlots/" + date;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGet, response -> {

                        Log.e("TAG", "onResponse: " + response);
                        try {
                            counsellingTime = new ArrayList<>();
                            counsellingTime.add("Time Slots");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String counsellingStart = (object.getString("startTime"));
                                String counsellingId = (object.getString("timeSlotID"));
                                String[] parts = counsellingStart.split("T");
                                counsellingStart = parts[1];
                                counsellingTime.add(counsellingId + ") " + counsellingStart);
                            }

                            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, counsellingTime) {
                                @Override
                                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                                    TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);

                                    if (position == 0) {
                                        tvData.setTextColor(Color.GRAY);
                                    } else {
                                        tvData.setTextColor(Color.WHITE);


                                    }

                                    return tvData;
                                }
                            };
                            spinner.setAdapter(stringArrayAdapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {
                                        strData = "";
                                        strData = parent.getItemAtPosition(position).toString();
//                                        Toast.makeText(getActivity(), strData, Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //Toast.makeText(getContext(), counsellingTime.toString(), Toast.LENGTH_LONG).show();
                            counsellingBook.setOnClickListener(v -> {
                                String[] couID = strData.split("\\)");
                                String ID = couID[0];
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYAPP", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("counsellingID", ID);
                                editor.putInt("flag", 1);
                                editor.apply();

                                Fragment aboutUs = new AboutRoyalActivity();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame, aboutUs);
                                fragmentTransaction.commit();
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }, error -> {

                    });

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                });

        return rootView;
    }
}