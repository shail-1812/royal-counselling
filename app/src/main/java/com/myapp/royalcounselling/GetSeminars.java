package com.myapp.royalcounselling;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class GetSeminars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String urlPost = Utils.main_url;
        urlPost = urlPost.concat("getActiveSeminarList");
        Seminar seminar = new Seminar();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlPost, response -> {

            Log.e("TAG", "onResponse: " + response);
            try {


                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.has("seminarDescription")) {
                    String str = jsonObject.optString("seminarDescription");
                    seminar.setSeminarDescription(str);
                }

                if (jsonObject.has("seminarEnd")) {
                    String str = jsonObject.optString("seminarEnd");
                    seminar.setSeminarEnd(str);

                }

                if (jsonObject.has("seminarFees")) {
                    String str = jsonObject.optString("seminarFees");
                    seminar.setSeminarFees(str);

                }

                if (jsonObject.has("seminarID")) {
                    String str = jsonObject.optString("seminarID");
                    seminar.setSeminarId(str);

                }

                if (jsonObject.has("seminarName")) {
                    String str = jsonObject.optString("seminarName");
                    seminar.setSeminarName(str);

                }

                if (jsonObject.has("seminarRegistrationEnd")) {
                    String str = jsonObject.optString("seminarRegistrationEnd");
                    seminar.setSeminarRegistrationEnd(str);

                }

                if (jsonObject.has("seminarRegistrationStart")) {
                    String str = jsonObject.optString("seminarRegistrationStart");
                    seminar.setSeminarRegistrationStart(str);

                }

                if (jsonObject.has("seminarStart")) {
                    String str = jsonObject.optString("seminarStart");
                    seminar.setSeminarStart(str);

                }


                if (jsonObject.has("seminarType")) {
                    String str = jsonObject.optString("seminarType");
                    seminar.setSeminarType(str);

                }


                if (jsonObject.has("seminarZoomLink")) {
                    String str = jsonObject.optString("seminarZoomLink");
                    seminar.setSeminarZoomLink(str);

                }


                if (jsonObject.has("whatsappLink")) {
                    String str = jsonObject.optString("whatsappLink");
                    seminar.setWhatsappLink(str);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.e("api error", "something went wrong" + error)) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}


