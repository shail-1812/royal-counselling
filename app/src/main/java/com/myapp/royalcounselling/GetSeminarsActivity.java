package com.myapp.royalcounselling;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.myapp.royalcounselling.ui.DisplaySeminarFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetSeminarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_seminars);
        String urlPost = Utils.main_url;
        urlPost = urlPost.concat("getActiveSeminarList");
        Seminar seminar = new Seminar();
        Bundle bundle = new Bundle();
        DisplaySeminarFragment displaySeminarFragment = new DisplaySeminarFragment();

        ArrayList<Seminar> seminarsList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlPost, response -> {

            Log.e("TAG", "onResponse: " + response);
            String seminarID;
            String seminarName;
            String seminarRegistrationEnd;
            String seminarRegistrationStart;
            String seminarStart;
            String seminarEnd;
            String seminarType;
            String seminarZoomLink;
            String whatsappLink;
            String seminarFees;
            String seminarDescription;


            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    seminarID = object.getString("seminarID");
                    seminarName = object.getString("seminarName");
                    seminarRegistrationEnd = object.getString("seminarRegistrationEnd");
                    seminarRegistrationStart = object.getString("seminarRegistrationStart");
                    seminarStart = object.getString("seminarStart");
                    seminarEnd = object.getString("seminarEnd");
                    seminarType = object.getString("seminarType");
                    seminarZoomLink = object.getString("seminarZoomLink");
                    whatsappLink = object.getString("whatsappLink");
                    seminarFees = object.getString("seminarFees");
                    seminarDescription = object.getString("seminarDescription");
                    seminar.setSeminarId(seminarID);
                    seminar.setSeminarName(seminarName);
                    seminar.setSeminarRegistrationEnd(seminarRegistrationEnd);
                    seminar.setSeminarRegistrationStart(seminarRegistrationStart);
                    seminar.setSeminarStart(seminarStart);
                    seminar.setSeminarEnd(seminarEnd);
                    seminar.setSeminarType(seminarType);
                    seminar.setSeminarZoomLink(seminarZoomLink);
                    seminar.setWhatsappLink(whatsappLink);
                    seminar.setSeminarFees(seminarFees);
                    seminar.setSeminarDescription(seminarDescription);
                    seminarsList.add(seminar);
                }
                //Toast.makeText(GetSeminarsActivity.this, seminarDescription, Toast.LENGTH_LONG).show();
                Log.e("seminars", String.valueOf(seminarsList));
                bundle.putSerializable("seminarList", seminarsList);
                displaySeminarFragment.setArguments(bundle);
                //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.replace(R.id.frame, displaySeminarFragment);
                //fragmentTransaction.commit();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.e("api error", "something went wrong" + error)) {


        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(GetSeminarsActivity.this).addToRequestQueue(stringRequest);
    }
}