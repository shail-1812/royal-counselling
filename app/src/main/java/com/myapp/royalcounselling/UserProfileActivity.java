package com.myapp.royalcounselling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    EditText state, city, institutionName, board, grade;
    RadioGroup gender;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        state = findViewById(R.id.edt_user_profile_state);
        city = findViewById(R.id.edt_user_profile_city);
        grade = findViewById(R.id.edt_user_profile_grade);
        board = findViewById(R.id.edt_user_profile_board);
        institutionName = findViewById(R.id.edt_user_profile_institution);
        gender = findViewById(R.id.radio_gender);
        register = findViewById(R.id.btn_user_profile_register);

        SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
        String email = sharedPreferences.getString("KEY_EMAIL", "");


        register.setOnClickListener(v -> {
            String sta = state.getText().toString();
            String cit = city.getText().toString();
            String gra = grade.getText().toString();
            String boa = board.getText().toString();
            String institute = institutionName.getText().toString();
            String gen = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
            loadData(cit, sta, gra, boa, institute, gen, email);
        });

    }

    private void loadData(String cit, String sta, String gra, String boa, String institute, String gen, String email) {

        String urlPost = Utils.main_url;
        urlPost = urlPost.concat("signUpUserProfile");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost, response -> {

            Log.e("TAG", "onResponse: " + response);
            try {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.has("city")) {
                    String str = jsonObject.optString("city");
                    intent.putExtra("city", str);
                }

                if (jsonObject.has("state")) {
                    String str = jsonObject.optString("state");
                    intent.putExtra("state", str);
                }

                if (jsonObject.has("grade")) {
                    String str = jsonObject.optString("grade");
                    intent.putExtra("grade", str);
                }

                if (jsonObject.has("board")) {
                    String str = jsonObject.optString("board");
                    intent.putExtra("board", str);
                }

                if (jsonObject.has("institutionName")) {
                    String str = jsonObject.optString("institutionName");
                    intent.putExtra("institutionName", str);
                }

                if (jsonObject.has("institutionName")) {
                    String str = jsonObject.optString("institutionName");
                    intent.putExtra("institutionName", str);
                }

                if (jsonObject.has("gender")) {
                    String str = jsonObject.optString("gender");
                    intent.putExtra("gender", str);
                }
                if (jsonObject.has("email")) {
                    String str = jsonObject.optString("email");
                    intent.putExtra("email", str);
                }


                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.e("api error", "something went wrong" + error)) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("state", sta);
                map.put("city", cit);
                map.put("institutionName", institute);
                map.put("grade", gra);
                map.put("board", boa);
                map.put("gender", gen);
                map.put("email", email);
                return map;
            }
        };

        VolleySingleton.getInstance(UserProfileActivity.this).addToRequestQueue(stringRequest);
        Intent intent = new Intent(UserProfileActivity.this, NavigationDrawerActivity.class);
        startActivity(intent);

    }
}