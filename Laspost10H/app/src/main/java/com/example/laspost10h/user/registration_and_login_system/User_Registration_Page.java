package com.example.laspost10h.user.registration_and_login_system;

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

public class User_Registration_Page extends AppCompatActivity
{
    private static final int REQUEST_CODE = 777;

    private static final int IMAGE_REQUEST = 1;

    TextInputEditText user_name, user_address, user_phone, user_dob, user_email, user_password;
    AppCompatSpinner user_city;
    Button user_select_image, user_register;
    TextView user_image_name;

    public String user_name_temp, user_address_temp, user_phone_temp, user_city_temp, user_dob_temp;
    public String user_email_temp, user_password_temp, user_image_name_temp;

    public Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_page);

        //EditText
        user_name = findViewById(R.id.user_name);
        user_address = findViewById(R.id.user_address);

        //Spinner
        user_city = findViewById(R.id.user_city);

        //EditText
        user_phone = findViewById(R.id.user_phone);
        user_dob = findViewById(R.id.user_dob);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);

        //Button
        user_select_image = findViewById(R.id.user_select_image);

        //TextView
        user_image_name = findViewById(R.id.user_image_name);

        //Button
        user_register = findViewById(R.id.user_register);

        user_select_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestStoragePermissionThenSelectImage();
            }
        });

        user_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                user_city_temp = parent.getItemAtPosition(position).toString();
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

        user_dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(User_Registration_Page.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month = month + 1;
                        user_dob_temp = day + " / " + month + " / " + year;
                        user_dob.setText(user_dob_temp);

                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        user_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_name_temp = user_name.getText().toString();
                user_address_temp = user_address.getText().toString();
                //user_city_temp done in Spinner Onclick Method
                user_phone_temp = user_phone.getText().toString();
                //user_dob_temp done in EditText Onclick Method
                user_email_temp = user_email.getText().toString();
                user_password_temp = user_password.getText().toString();
                //user_image_name_temp done in SelectImage Method

                // part 1
                if(user_name_temp.equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please fill out the Name Field.", Toast.LENGTH_SHORT).show();
                }
                if(user_address_temp.equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please fill out the Address Field.", Toast.LENGTH_SHORT).show();
                }
                if(user_city_temp.equals("Select your City"))
                {
                    Toast.makeText(User_Registration_Page.this, "Please Select City from List.", Toast.LENGTH_SHORT).show();
                }

                // part 2
                if(user_phone_temp.equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please fill out the Phone Filed.", Toast.LENGTH_SHORT).show();
                }
                if(user_phone_temp.length() < 10)
                {
                    Toast.makeText(User_Registration_Page.this, "Please Enter the Appropriate Phone Number.", Toast.LENGTH_SHORT).show();
                }

                // Critical Part
                if(user_dob.getText().toString().equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please Select Your Birth-Date.", Toast.LENGTH_SHORT).show();
                }

                // part 3
                if(user_email_temp.equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }
                if(user_password_temp.equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                // Critical Part
                if(user_image_name.getText().toString().equals(""))
                {
                    Toast.makeText(User_Registration_Page.this, "Please Select Your Profile Picture.", Toast.LENGTH_SHORT).show();
                }

                // part 4
                else
                {
                    if (Utility.isNetworkAvailable(User_Registration_Page.this))
                    {
                        getUserRegister(user_name_temp, user_address_temp, user_city_temp, user_phone_temp, user_dob_temp, user_email_temp, user_password_temp, user_image_name_temp, bitmap);
                    }
                    else
                    {
                        Toast.makeText(User_Registration_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
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
                            ActivityCompat.requestPermissions(User_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                            // If Permission is Granted then SelectImage Logic
                            if(ContextCompat.checkSelfPermission(User_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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
            ActivityCompat.requestPermissions(User_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

            // If Permission is Granted then SelectImage Logic
            if(ContextCompat.checkSelfPermission(User_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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

                user_image_name_temp = cursor.getString(name_index);

                user_image_name.setText(user_image_name_temp);
                user_image_name.setVisibility(View.VISIBLE);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void getUserRegister(String user_name, String user_address, String user_city, String user_phone, String user_dob, String user_email, String user_password, String user_image_name, Bitmap imageBitmap)
    {
        String user_image_url = Image_URLs.getUserImageURL(user_image_name);

        RequestQueue requestQueue = Volley.newRequestQueue(User_Registration_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("user_name", user_name);
        jsonParameters.put("user_address", user_address);
        jsonParameters.put("user_city", user_city);
        jsonParameters.put("user_phone", user_phone);
        jsonParameters.put("user_DOB", user_dob);
        jsonParameters.put("user_email", user_email);
        jsonParameters.put("user_password", user_password);
        jsonParameters.put("user_image_name", user_image_name);
        jsonParameters.put("user_image_url", user_image_url);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(User_Registration_Page.this);
        progressDialog.setMessage("Please Wait Registration for General User is in Progress");
        progressDialog.setTitle("General User Registration");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(User_Registration_Page.this);

        JsonObjectRequest user_Registration_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_REGISTRATION, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String user_return_message = null;

                try
                {
                    user_return_message =(String) response.get("user_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if(user_return_message.equals("User Same User Already Exist"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("User Already Exist");
                    builder.setMessage("The same General User Already Exist into the System whose E-mail I'd is as same as yours.\n\nPlease click on 'Ok' to try again with different E-mail I'd");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(User_Registration_Page.this, User_Registration_Page.class);
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
                else if(user_return_message.equals("User Register Fail"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Registration_Page.this, "Oops...!! Somehow User Registration has been failed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //3rd Possible Response
                else if(user_return_message.equals("Something went wrong in User Register"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Registration_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //4th Possible Response
                else if(user_return_message.equals("User Register Success and User Email Sent"))
                {
                    progressDialog.dismiss();

                    // When Positive response came then we have to upload image into server
                    uploadImage(user_image_name, user_email, imageBitmap);
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

        user_Registration_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Registration_POST_Request);

    }

    private void uploadImage(String user_image_name, String user_email, Bitmap temp_bitmap)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PHP_API_URLs_For_Image_Upload.USER_IMAGE_UPLOAD_PHP, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String user_image_upload_response = jsonObject.getString("user_image_upload_response");

                    if(user_image_upload_response.equals("User Image Uploaded"))
                    {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(User_Registration_Page.this);

                        builder1.setIcon(R.mipmap.logo1);
                        builder1.setTitle("User Registration Complete");
                        builder1.setMessage("Woo-Hoo Your Registration as General User has been Successfully Completed.\n\nPlease Check Mail for OTP for Verification of Account.");

                        builder1.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(User_Registration_Page.this, User_Account_Verification.class);
                                intent.putExtra("user_sent_mail", user_email);

                                startActivity(intent);
                                finish();
                            }
                        });

                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(User_Registration_Page.this, User_Login_Page.class);
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
                parameters.put("user_image", imageToString(temp_bitmap));
                parameters.put("user_image_name", user_image_name);
                return parameters;
            }
        };

        MySingletonUserUploadImage.getInstance(User_Registration_Page.this).addToRequestQueue(stringRequest);
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
        Intent intent = new Intent(User_Registration_Page.this, User_Login_Page.class);
        startActivity(intent);
        finish();
    }
}