package com.myapp.royalcounselling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class IndividualRegisteredActivity extends AppCompatActivity {
    TextView seminarName, seminarDescription, sStart, sEnd, type;
    Button viewPPTButton;
    TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raw_register_seminar_info);
        Intent intent = getIntent();
        String name = intent.getStringExtra("seminarName");
        String description = intent.getStringExtra("seminarDescription");
        String seminarType = intent.getStringExtra("seminarType");
        String seminarStart = intent.getStringExtra("seminarStart");
        String seminarEnd = intent.getStringExtra("seminarEnd");
        String seminarId = intent.getStringExtra("seminarId");
        seminarName = findViewById(R.id.tv_seminar_name);
        seminarDescription = findViewById(R.id.tv_seminar_description);
        sStart = findViewById(R.id.tv_seminar_start);
        sEnd = findViewById(R.id.tv_seminar_end);
        type = findViewById(R.id.tv_seminar_type);
        notice = findViewById(R.id.tv_notice);
        viewPPTButton = findViewById(R.id.btn_view_ppt);

        seminarName.setText(name);
        seminarDescription.setText(description);
        sStart.setText(seminarStart);
        sEnd.setText(seminarEnd);
        type.setText(seminarType);

        java.sql.Timestamp timeStamp = java.sql.Timestamp.valueOf( seminarEnd );
        java.sql.Timestamp cTime = new java.sql.Timestamp(System.currentTimeMillis());

        if(cTime.before(timeStamp)){
            viewPPTButton.setVisibility(View.GONE);
        }
        else{
            viewPPTButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            notice.setVisibility(View.GONE);
        }
    }
}