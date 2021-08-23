package com.myapp.royalcounselling.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.myapp.royalcounselling.MyRequestAdapter;
import com.myapp.royalcounselling.MySeminarAdapter;
import com.myapp.royalcounselling.PPTRequestBean;
import com.myapp.royalcounselling.R;
import com.myapp.royalcounselling.Seminar;
import com.myapp.royalcounselling.Utils;
import com.myapp.royalcounselling.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PPTRequestFragment extends Fragment {
    ArrayList<PPTRequestBean> requestList = new ArrayList<PPTRequestBean>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_display_seminar, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYAPP", MODE_PRIVATE);
        String email = sharedPreferences.getString("KEY_EMAIL", "");
        String urlPost = Utils.main_url + "powerPointRequestUserWise/" + email;

        Toast.makeText(getActivity(), urlPost, Toast.LENGTH_LONG).show();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlPost, response -> {


            Log.e("TAG", "onResponse: " + response);
            String comment;
            boolean queryOver;
            String requestQuery;
            String queryTime;

            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    PPTRequestBean request = new PPTRequestBean();
                    JSONObject object = jsonArray.getJSONObject(i);
                    comment = (String.valueOf(object.getString("comment")));

                    queryTime = (String.valueOf(object.getString("requestAt"))).replace('T',' ').replace('+',' ');
                    requestQuery =(String.valueOf(object.getString("requestQuery")));
                    String queryOverString =(String.valueOf(object.getString("queryOver")));
                    try{
                        queryOver = Boolean.parseBoolean(queryOverString);
                    }catch(Exception e){
                        queryOver = false;
                    }
                    request.setComment(comment);
                    request.setRequestQuery(requestQuery);
                    request.setQueryTime(queryTime);
                    request.setQueryOver(queryOver);
                    requestList.add(request);
                }
                //Toast.makeText(getActivity(),  String.valueOf(seminarsList.get(0).getSeminarName()), Toast.LENGTH_LONG).show();
//                gridView = rootView.findViewById(R.id.grid);
                listView = rootView.findViewById(R.id.list);
                MyRequestAdapter myRequestAdapter = new MyRequestAdapter(getContext(), requestList);
                //String value = String.valueOf(seminarsList.size());
                //Toast.makeText(getContext(), String.valueOf(seminarsList.size()), Toast.LENGTH_LONG).show();
                //gridView.setAdapter(mySeminarAdapter);
                listView.setAdapter(myRequestAdapter);

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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


        return rootView;
    }
}
