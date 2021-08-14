package com.myapp.royalcounselling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IndividualSeminarActivity extends AppCompatActivity {

    TextView seminarName, seminarDescription, sStart, sEnd, rStart, rEnd, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raw_seminar_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("seminarName");
        String description = intent.getStringExtra("seminarDescription");
        String registrationStart = intent.getStringExtra("registrationStart");
        String registrationEnd = intent.getStringExtra("registrationEnd");
        String seminarType = intent.getStringExtra("seminarType");
        String seminarStart = intent.getStringExtra("seminarStart");
        String seminarEnd = intent.getStringExtra("seminarEnd");

        seminarName = findViewById(R.id.tv_seminar_name);
        seminarDescription = findViewById(R.id.tv_seminar_description);
        sStart = findViewById(R.id.tv_seminar_start);
        sEnd = findViewById(R.id.tv_seminar_end);
        rStart = findViewById(R.id.tv_registration_start);
        rEnd = findViewById(R.id.tv_registration_end);
        type = findViewById(R.id.tv_seminar_type);


        seminarName.setText(name);
        seminarDescription.setText(description);
        sStart.setText(seminarStart);
        sEnd.setText(seminarEnd);
        rStart.setText(registrationStart);
        rEnd.setText(registrationEnd);
        type.setText(seminarType);

    }
}