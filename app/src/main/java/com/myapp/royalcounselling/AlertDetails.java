package com.myapp.royalcounselling;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDetails extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        try{
            String data = i.getExtras().getString("activityToDirect");
            Toast.makeText(this,"Data "+data,Toast.LENGTH_SHORT).show();
            Intent n = new Intent(this,NavigationDrawerActivity.class);
            n.putExtra("activityToDirect",data);
            startActivity(n);
            finish();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
