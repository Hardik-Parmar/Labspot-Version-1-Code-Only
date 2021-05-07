package com.example.laspost10h.delivery.registration_and_login_system;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.MySingletonUserUploadImage;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Image_URLs;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.Confidential_Details.PHP_API_URLs_For_Image_Upload;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Delivery_Registration_Page extends AppCompatActivity
{
    private static final int REQUEST_CODE = 777;

    private static final int IMAGE_REQUEST = 1;

    TextInputEditText delivery_name, delivery_address, delivery_phone, delivery_dob, delivery_email, delivery_password;
    AppCompatSpinner delivery_city;
    Button delivery_select_image, delivery_register;
    TextView delivery_image_name;

    public String delivery_name_temp, delivery_address_temp, delivery_phone_temp, delivery_city_temp;
    public String delivery_dob_temp, delivery_email_temp, delivery_password_temp, delivery_image_name_temp;

    public Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registration_page);

        //EditText
        delivery_name = findViewById(R.id.delivery_name);
        delivery_address = findViewById(R.id.delivery_address);

        //Spinner
        delivery_city = findViewById(R.id.delivery_city);

        //EditText
        delivery_phone = findViewById(R.id.delivery_phone);
        delivery_dob = findViewById(R.id.delivery_dob);
        delivery_email = findViewById(R.id.delivery_email);
        delivery_password = findViewById(R.id.delivery_password);

        //Button
        delivery_select_image = findViewById(R.id.delivery_select_image);

        //TextView
        delivery_image_name = findViewById(R.id.delivery_image_name);

        //Button
        delivery_register = findViewById(R.id.delivery_register);

        delivery_select_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestStoragePermissionThenSelectImage();
            }
        });

        delivery_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                delivery_city_temp = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        delivery_dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Delivery_Registration_Page.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month = month + 1;
                        delivery_dob_temp = day + " / " + month + " / " + year;
                        delivery_dob.setText(delivery_dob_temp);

                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        delivery_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_name_temp = delivery_name.getText().toString();
                delivery_address_temp = delivery_address.getText().toString();
                //delivery_city_temp done in Spinner Onclick Method
                delivery_phone_temp = delivery_phone.getText().toString();
                //delivery_dob_temp done in EditText Onclick Method
                delivery_email_temp = delivery_email.getText().toString();
                delivery_password_temp = delivery_password.getText().toString();
                //delivery_image_name_temp done in SelectImage Method

                // part 1
                if(delivery_name_temp.equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please fill out the Name Field.", Toast.LENGTH_SHORT).show();
                }
                if(delivery_address_temp.equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please fill out the Address Field.", Toast.LENGTH_SHORT).show();
                }
                if(delivery_city_temp.equals("Select your City"))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please Select City from List.", Toast.LENGTH_SHORT).show();
                }

                // part 2
                if(delivery_phone_temp.equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please fill out the Phone Filed.", Toast.LENGTH_SHORT).show();
                }
                if(delivery_phone_temp.length() < 10)
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please Enter the Appropriate Phone Number.", Toast.LENGTH_SHORT).show();
                }

                // Critical Part
                if(delivery_dob.getText().toString().equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please Select Your Birth-Date.", Toast.LENGTH_SHORT).show();
                }

                // part 3
                if(delivery_email_temp.equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }
                if(delivery_password_temp.equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                // Critical Part
                if(delivery_image_name.getText().toString().equals(""))
                {
                    Toast.makeText(Delivery_Registration_Page.this, "Please Select Your Profile Picture.", Toast.LENGTH_SHORT).show();
                }

                // part 4
                else
                {
                    if (Utility.isNetworkAvailable(Delivery_Registration_Page.this))
                    {
                        getDeliveryRegister(delivery_name_temp, delivery_address_temp, delivery_city_temp, delivery_phone_temp, delivery_dob_temp, delivery_email_temp, delivery_password_temp, delivery_image_name_temp, bitmap);
                    }
                    else
                    {
                        Toast.makeText(Delivery_Registration_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void requestStoragePermissionThenSelectImage()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            // User denied Permission

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Storage Permission needed for Selecting your Profile Picture from Gallery")
                    .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Ask For Permission
                            ActivityCompat.requestPermissions(Delivery_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                            // If Permission is Granted then SelectImage Logic
                            if(ContextCompat.checkSelfPermission(Delivery_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            {
                                selectImage();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            // Ask For Permission
            ActivityCompat.requestPermissions(Delivery_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

            // If Permission is Granted then SelectImage Logic
            if(ContextCompat.checkSelfPermission(Delivery_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                selectImage();
            }
        }
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && requestCode == 1 && data != null)
        {
            Uri path = data.getData();

            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                Cursor cursor = getContentResolver().query(path, null, null, null, null);

                int name_index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();

                delivery_image_name_temp = cursor.getString(name_index);

                delivery_image_name.setText(delivery_image_name_temp);
                delivery_image_name.setVisibility(View.VISIBLE);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void getDeliveryRegister(String delivery_name, String delivery_address, String delivery_city, String delivery_phone, String delivery_dob, String delivery_email, String delivery_password, String delivery_image_name, Bitmap imageBitmap)
    {
        String delivery_image_url = Image_URLs.getDeliveryImageURL(delivery_image_name);

        RequestQueue requestQueue = Volley.newRequestQueue(Delivery_Registration_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_name", delivery_name);
        jsonParameters.put("delivery_address", delivery_address);
        jsonParameters.put("delivery_city", delivery_city);
        jsonParameters.put("delivery_phone", delivery_phone);
        jsonParameters.put("delivery_DOB", delivery_dob);
        jsonParameters.put("delivery_email", delivery_email);
        jsonParameters.put("delivery_password", delivery_password);
        jsonParameters.put("delivery_image_name", delivery_image_name);
        jsonParameters.put("delivery_image_url", delivery_image_url);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Delivery_Registration_Page.this);
        progressDialog.setMessage("Please Wait Registration for Delivery Staff is in Progress");
        progressDialog.setTitle("Delivery Staff Registration");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Registration_Page.this);

        JsonObjectRequest delivery_Registration_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_REGISTRATION, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String delivery_return_message = null;

                try
                {
                    delivery_return_message =(String) response.get("delivery_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if(delivery_return_message.equals("Delivery Same User Already Exist"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("User Already Exist");
                    builder.setMessage("The same User Already Exist into the System whose E-mail I'd is as same as yours.\n\nPlease click on 'Ok' to try again with different E-mail I'd");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Registration_Page.this, Delivery_Registration_Page.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                // 2nd Possible Response
                else if(delivery_return_message.equals("Delivery Register Fail"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Registration_Page.this, "Oops...!! Somehow Delivery Staff Registration has been failed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //3rd Possible Response
                else if(delivery_return_message.equals("Something went wrong in Delivery Register"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Registration_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //4th Possible Response
                else if(delivery_return_message.equals("Delivery Register Success and Delivery Email Sent"))
                {
                    progressDialog.dismiss();

                    // When Positive response came then we have to upload image into server
                    uploadImage(delivery_image_name, delivery_email, imageBitmap);
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });

        delivery_Registration_POST_Request.setRetryPolicy(new RetryPolicy()
        {
            @Override
            public int getCurrentTimeout()
            {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount()
            {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError
            {

            }
        });

        requestQueue.add(delivery_Registration_POST_Request);

    }

    private void uploadImage(String delivery_image_name, String delivery_email, Bitmap temp_bitmap)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PHP_API_URLs_For_Image_Upload.DELIVERY_IMAGE_UPLOAD_PHP, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String delivery_image_upload_response = jsonObject.getString("delivery_image_upload_response");

                    if(delivery_image_upload_response.equals("Delivery Image Uploaded"))
                    {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(Delivery_Registration_Page.this);

                        builder1.setIcon(R.mipmap.logo1);
                        builder1.setTitle("Delivery Staff Registration Complete");
                        builder1.setMessage("Woo-Hoo Your Registration as Delivery Staff has been Successfully Completed.\n\nPlease Check Mail for OTP for Verification of Account.");

                        builder1.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Delivery_Registration_Page.this, Delivery_Account_Verification.class);
                                intent.putExtra("delivery_sent_mail", delivery_email);

                                startActivity(intent);
                                finish();
                            }
                        });

                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Delivery_Registration_Page.this, Delivery_Registration_Page.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("delivery_image", imageToString(temp_bitmap));
                parameters.put("delivery_image_name", delivery_image_name);
                return parameters;
            }
        };

        MySingletonUserUploadImage.getInstance(Delivery_Registration_Page.this).addToRequestQueue(stringRequest);
    }

    // converting image Bitmap to String
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // compress input bitmap into JPEG with 100% quality and storing the result into ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // Converting ByteArrayOutputStream into Bytes
        byte[] image_files = byteArrayOutputStream.toByteArray();

        // encode the bytes into String
        String temp = Base64.encodeToString(image_files, Base64.DEFAULT);
        // it will produce output a bitmap in form of String

        return temp;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Delivery_Registration_Page.this, Delivery_Login_Page.class);
        startActivity(intent);
        finish();
    }
}