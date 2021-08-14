///*package com.myapp.royalcounselling;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.ar.autopartwarehousesystem.R;
//import com.ar.autopartwarehousesystem.adapter.AdminSupplierAdapter;
//import com.ar.autopartwarehousesystem.adapter.CarModelAdapter;
//import com.ar.autopartwarehousesystem.adminModel.CarModel;
//import com.ar.autopartwarehousesystem.adminModel.SupplierModel;
//import com.ar.autopartwarehousesystem.spinner.CarModelSpinner;
//import com.ar.autopartwarehousesystem.spinner.SupplierSpinner;
//import com.ar.autopartwarehousesystem.webservice.Utils;
//import com.ar.autopartwarehousesystem.webservice.VolleyMultipartRequest;
//import com.ar.autopartwarehousesystem.webservice.VolleySingleton;
//import com.google.android.material.snackbar.Snackbar;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.app.Activity.RESULT_OK;
//import static com.ar.autopartwarehousesystem.fragment.admin.Admin_AddserviceStaff.PERMISSIONS_MULTIPLE_REQUEST;
//import static com.ar.autopartwarehousesystem.webservice.Netconnetion.isconnected;
//
//public class Admin_AddAutoParts extends Fragment {
//
//
//    EditText edtCarName, edtCarModel, edtCarDescription, edtPrice, edtAvailableQuantity;
//    Button btnAddCar;
//    Spinner spiCarData, spiSupplierData, spiQNT;
//    String strCarModelSpinn;
//    String strsupplierNameId;
//    String minQty;
//
//    Button imgDpset;
//    ImageView circleImageView;
//    private Bitmap thumbnail;
//    private File destination;
//    String qty[] = {"-Qty-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View rootview = inflater.inflate(R.layout.fragment_add_parts, container, false);
//        loadCarData();
//        loadSupplierData();
//
//        imgDpset = rootview.findViewById(R.id.img_camera);
//        circleImageView = rootview.findViewById(R.id.profile_image);
//
//        edtPrice = rootview.findViewById(R.id.edt_price);
//        edtAvailableQuantity = rootview.findViewById(R.id.edt_available_quantity);
//        edtCarName = rootview.findViewById(R.id.edt_carName);
//        edtCarModel = rootview.findViewById(R.id.edt_carmodel);
//        edtCarDescription = rootview.findViewById(R.id.edt_textArea_information);
//        btnAddCar = rootview.findViewById(R.id.btn_login);
//        spiCarData = rootview.findViewById(R.id.spinner_carModel);
//        spiSupplierData = rootview.findViewById(R.id.spinner_Supplername);
//        spiQNT = rootview.findViewById(R.id.spinner_qnty);
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, qty);
//        spiQNT.setAdapter(arrayAdapter);
//        spiQNT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                minQty = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        imgDpset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkAndroidVersion();
//            }
//
//        });
//        btnAddCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String strCar = edtCarName.getText().toString();
//                String strCarModel = edtCarModel.getText().toString();
//                String strCarDescription = edtCarDescription.getText().toString();
//                String strPrice = edtPrice.getText().toString();
//                String strAvaliableQuntity = edtAvailableQuantity.getText().toString();
//
//                if (strPrice.equals("") && strPrice.isEmpty()) {
//
//                    edtPrice.setError("Please Enter Price Of Part");
//                } else if (strAvaliableQuntity.equals("") && strAvaliableQuntity.isEmpty()) {
//
//                    edtAvailableQuantity.setError("Please Enter Available Quantity");
//                } else if (strCar.equals("")) {
//
//                    edtCarName.setError("Please Enter AutoPart");
//                } else if (strCarModel.equals("") && strCarModel.isEmpty()) {
//
//                    edtCarModel.setError("Please Enter AutoPart Model");
//                } else if (strCarDescription.equals("") && strCarDescription.isEmpty()) {
//
//                    edtCarDescription.setError("Please Enter AutoPart Description");
//                } else {
//                    if (!isconnected(getActivity())) {
//                        Toast.makeText(getActivity(), "Connect your internet", Toast.LENGTH_SHORT).show();
//                    } else {
//
//
//                        if (thumbnail == null) {
//                            loadDataSimple(strPrice, strAvaliableQuntity, strCar, strCarModel, strCarDescription, strCarModelSpinn, strsupplierNameId, minQty);
//
//                        } else {
//                            loadData(strPrice, strAvaliableQuntity, strCar, strCarModel, strCarDescription, strCarModelSpinn, strsupplierNameId, minQty);
//
//                        }
//
//                    }
//                }
//
//            }
//        });
//
//        return rootview;
//    }
//
//    private void loadDataSimple(final String strPrice, final String strAvaliableQuntity, final String strCar, final String strCarModel, final String strCarDescription, final String strCarModelSpinn, final String strsupplierNameId, final String minQty) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.URL_PART_ADD, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                JSONObject jsonObject = null;
//
//                try {
//                    jsonObject = new JSONObject(response);
//                    String strData = jsonObject.getString("message");
//                    //  Toast.makeText(getActivity(), ""+strData, Toast.LENGTH_SHORT).show();
//                    Admin_ManageParts admin_manageParts = new Admin_ManageParts();
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, admin_manageParts);
//                    fragmentTransaction.commit();
//                    //  Admin_ManageServiceStaff admin_manageServiceStaff = new Admin_ManageServiceStaff();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("car_model_id", strCarModelSpinn);
//                params.put("parts_name", strCar);
//                params.put("description", strCarDescription);
//                params.put("price", strPrice);
//                params.put("model_number", strCarModel);
//                params.put("min_quantity", minQty);
//                params.put("available_quantity", strAvaliableQuntity);
//                params.put("supplier_id", strsupplierNameId);
//                return params;
//
//            }
//        };
//
//        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
//    }
//
//    private void checkAndroidVersion() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkPermission();
//
//        } else {
//            selectImage();
//            // write your logic here
//        }
//
//    }
//
//    private void selectImage() {
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, 1);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//
//                thumbnail = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//                destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//                Log.e("path from Camera", destination + "");
//                //Toast.makeText(getActivity(), ""+destination, Toast.LENGTH_SHORT).show();
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                circleImageView.setImageBitmap(thumbnail);
//
//
//            } else if (requestCode == 2) {
//                Uri selectedImage = data.getData();
//                String[] filePath = {MediaStore.Images.Media.DATA};
//                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                destination = new File(selectedImage.getPath());
//               /* try {
//                    destination = new File(new URI(picturePath));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                Log.e("path from gallery", picturePath + "");
//                //Toast.makeText(getActivity(), ""+picturePath, Toast.LENGTH_SHORT).show();
//                c.close();
//                thumbnail = (BitmapFactory.decodeFile(picturePath));
//                circleImageView.setImageBitmap(thumbnail);
//            }
//
//        }
//
//    }
//
//    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
//                .checkSelfPermission(getActivity(),
//                        Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale
//                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale
//                            (getActivity(), Manifest.permission.CAMERA)) {
//
//                Snackbar.make(getActivity().findViewById(android.R.id.content),
//                        "Please Grant Permissions to upload profile photo",
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                requestPermissions(
//                                        new String[]{Manifest.permission
//                                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                        PERMISSIONS_MULTIPLE_REQUEST);
//                            }
//                        }).show();
//            } else {
//                requestPermissions(
//                        new String[]{Manifest.permission
//                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                        PERMISSIONS_MULTIPLE_REQUEST);
//            }
//        } else {
//            selectImage();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (requestCode) {
//            case PERMISSIONS_MULTIPLE_REQUEST:
//                if (grantResults.length > 0) {
//                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (cameraPermission && readExternalFile) {
//                        // write your logic here
//                    } else {
//                        Snackbar.make(getActivity().findViewById(android.R.id.content),
//                                "Please Grant Permissions to upload profile photo",
//                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                                new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        requestPermissions(
//                                                new String[]{Manifest.permission
//                                                        .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                                PERMISSIONS_MULTIPLE_REQUEST);
//                                    }
//                                }).show();
//                    }
//                }
//                break;
//        }
//    }
//
//
//    private void loadCarData() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.URL_CAR_LIST, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                final ArrayList<CarModel> serviceStaffModelArrayList = new ArrayList<CarModel>();
//               if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.has("success")) {
//                        boolean msg = jsonObject.getBoolean("success");
//                        if (msg == true) {
//                            if (jsonObject.has("data")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    final CarModel carModel = new CarModel();
//                                    JSONObject jsonObjData = jsonArray.getJSONObject(i);
//                                    if (jsonObjData.has("id")) {
//                                        String strCarId = jsonObjData.getString("id");
//                                        carModel.setCar_id(strCarId);
//                                    }
//                                    if (jsonObjData.has("car_name")) {
//                                        String strFn = jsonObjData.getString("car_name");
//                                        carModel.setCar_name(strFn);
//                                    }
//                                    if (jsonObjData.has("model")) {
//                                        String strLn = jsonObjData.getString("model");
//                                        carModel.setCar_model(strLn);
//                                    }
//                                    if (jsonObjData.has("description")) {
//                                        String strContactNo = jsonObjData.getString("description");
//                                        carModel.setCar_Description(strContactNo);
//                                    }
//                                    serviceStaffModelArrayList.add(carModel);
//                                    CarModelSpinner serviceStaffAdapter = new CarModelSpinner(getActivity(), serviceStaffModelArrayList);
//                                    spiCarData.setAdapter(serviceStaffAdapter);
//                                    spiCarData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                            strCarModelSpinn = serviceStaffModelArrayList.get(position).getCar_id();
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> parent) {
//
//                                        }
//                                    });
//
//                                }
//
//                            }
//
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
//
//
//    }
//
//    private void loadSupplierData() {
///*
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();*/
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.URL_SUPPLIER_LIST, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                final ArrayList<SupplierModel> serviceStaffModelArrayList = new ArrayList<SupplierModel>();
//  /*              if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//  */
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.has("success")) {
//                        boolean msg = jsonObject.getBoolean("success");
//                        if (msg == true) {
//                            if (jsonObject.has("data")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    SupplierModel serviceStaffModel = new SupplierModel();
//                                    JSONObject jsonObjData = jsonArray.getJSONObject(i);
//                                    if (jsonObjData.has("id")) {
//                                        String strId = jsonObjData.getString("id");
//                                        serviceStaffModel.setId(strId);
//                                    }
//                                    if (jsonObjData.has("first_name")) {
//                                        String strFn = jsonObjData.getString("first_name");
//                                        serviceStaffModel.setFirst_name(strFn);
//                                    }
//                                    if (jsonObjData.has("last_name")) {
//                                        String strLn = jsonObjData.getString("last_name");
//                                        serviceStaffModel.setLast_name(strLn);
//                                    }
//                                    if (jsonObjData.has("contact_no")) {
//                                        String strContactNo = jsonObjData.getString("contact_no");
//                                        serviceStaffModel.setContact_number(strContactNo);
//                                    }
//                                    if (jsonObjData.has("email_id")) {
//                                        String email_id = jsonObjData.getString("email_id");
//                                        serviceStaffModel.setEmail_id(email_id);
//                                    }
//                                    if (jsonObjData.has("address")) {
//                                        String address = jsonObjData.getString("address");
//                                        serviceStaffModel.setAddress(address);
//                                    }
//                                    if (jsonObjData.has("city")) {
//                                        String city = jsonObjData.getString("city");
//                                        serviceStaffModel.setCity(city);
//                                    }
//                                    if (jsonObjData.has("state")) {
//                                        String state = jsonObjData.getString("state");
//                                        serviceStaffModel.setState(state);
//                                    }
//                                    if (jsonObjData.has("country")) {
//                                        String country = jsonObjData.getString("country");
//                                        serviceStaffModel.setCountry(country);
//                                    }
//                                    serviceStaffModelArrayList.add(serviceStaffModel);
//                                    SupplierSpinner serviceStaffAdapter = new SupplierSpinner(getActivity(), serviceStaffModelArrayList);
//                                    spiSupplierData.setAdapter(serviceStaffAdapter);
//                                    spiSupplierData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                            strsupplierNameId = serviceStaffModelArrayList.get(position).getId();
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> parent) {
//
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
//
//
//    }
//
//    private void loadData(final String strPrice, final String strAvaliableQuntity,
//                          final String strCar, final String strCarModel,
//                          final String strCarDescription,
//                          final String strCarModelSpinn, final String strsupplierNameId, final String minQty) {
//
//
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
//                Utils.URL_PART_ADD, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//
//                if (progressDialog.isShowing()) {
//
//                    progressDialog.dismiss();
//
//                }
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(new String(response.data));
//                    String strData = jsonObject.getString("message");
//
//                    //  Toast.makeText(getActivity(), ""+strData, Toast.LENGTH_SHORT).show();
//
//                    Admin_ManageParts admin_manageParts = new Admin_ManageParts();
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, admin_manageParts);
//                    fragmentTransaction.commit();
//                    //  Admin_ManageServiceStaff admin_manageServiceStaff = new Admin_ManageServiceStaff();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("car_model_id", strCarModelSpinn);
//                params.put("parts_name", strCar);
//                params.put("description", strCarDescription);
//                params.put("price", strPrice);
//                params.put("model_number", strCarModel);
//                params.put("min_quantity", minQty);
//                params.put("available_quantity", strAvaliableQuntity);
//                params.put("supplier_id", strsupplierNameId);
//                return params;
//            }
//
//            @Override
//            protected Map<String, DataPart> getByteData() throws AuthFailureError {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(thumbnail)));
//                return params;
//            }
//        };
//
//        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
//
//    }
//
//    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }
//
//
//}*/
