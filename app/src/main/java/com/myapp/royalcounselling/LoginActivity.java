package com.myapp.royalcounselling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button signIn, signUp;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edt_login_email);
        password = findViewById(R.id.edt_login_password);
        signIn = findViewById(R.id.btn_login_sign_in);
        signUp = findViewById(R.id.btn_login_sign_up);
        forgotPassword = findViewById(R.id.tv_login_forgot);

        signIn.setOnClickListener(v -> {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("KEY_EMAIL", em);
            editor.putString("KEY_PASSWORD", pass);
            editor.apply();
            loadData(em, pass);
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loadData(String em, String pass) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String urlPost = Utils.main_url;
        urlPost = urlPost.concat("loginUser");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost, response -> {

            Log.e("TAG", "onResponse: " + response);
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(response);
                int status = jsonObject.getInt("status");

                if (status == -1) {
                    throw new ArrayIndexOutOfBoundsException();


                }
                if (status == 200) {
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                }


            } catch (Exception e) {
                SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("KEY_EMAIL");
                editor.remove("KEY_PASSWORD");
                editor.apply();
                Toast.makeText(LoginActivity.this, "Invalid Email Id or password", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> {

            Toast invalid_email_id;
            invalid_email_id = Toast.makeText(LoginActivity.this, "Invalid Email Id", Toast.LENGTH_LONG);
            invalid_email_id.show();
     //       Log.e("Invalid Email", error.getMessage());

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("emailID", em);
                map.put("password", pass);
                return map;
            }

        };
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

    }
}