package com.myapp.royalcounselling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MySeminarAdapter extends BaseAdapter {

    Context context;
    ArrayList<Seminar> seminarArrayList;
    int[] blurImages = {R.drawable.image_blur};

    public MySeminarAdapter(Context context, ArrayList<Seminar> seminarArrayList) {
        this.context = context;
        this.seminarArrayList = seminarArrayList;

    }

    @Override
    public int getCount() {
        return seminarArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return seminarArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.raw_seminar_list, null);
        TextView seminarName = convertView.findViewById(R.id.tv_seminar_name_card);
        TextView name = convertView.findViewById(R.id.tv_seminar_name);
        TextView description = convertView.findViewById(R.id.tv_seminar_description);
        TextView type = convertView.findViewById(R.id.tv_seminar_type);

        ImageView seminarImage = convertView.findViewById(R.id.img_seminar);
        seminarName.setText(seminarArrayList.get(position).getSeminarName());
        seminarImage.setImageResource(blurImages[0]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seminar seminar = seminarArrayList.get(position);
                String seminarName = seminar.getSeminarName();
                String seminarDescription = seminar.getSeminarDescription();
                String registrationStart = seminar.getSeminarRegistrationStart();
                String registrationEnd = seminar.getSeminarRegistrationEnd();
                String seminarType = seminar.getSeminarType();
                String seminarStart = seminar.getSeminarStart();
                String seminarEnd = seminar.getSeminarEnd();
                String seminarID = seminar.getSeminarId();
                Intent i = new Intent(context, IndividualSeminarActivity.class);
                i.putExtra("seminarName", seminarName);
                i.putExtra("seminarDescription", seminarDescription);
                i.putExtra("registrationStart", registrationStart);
                i.putExtra("registrationEnd", registrationEnd);
                i.putExtra("seminarType", seminarType);
                i.putExtra("seminarStart", seminarStart);
                i.putExtra("seminarEnd", seminarEnd);
                context.startActivity(i);
            }
        });
        return convertView;

    }
}
