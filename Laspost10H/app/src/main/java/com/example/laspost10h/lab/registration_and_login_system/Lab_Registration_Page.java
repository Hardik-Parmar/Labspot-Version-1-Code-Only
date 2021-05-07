package com.example.laspost10h.lab.registration_and_login_system;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
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
import java.util.HashMap;
import java.util.Map;

public class Lab_Registration_Page extends AppCompatActivity
{
    private static final int REQUEST_CODE = 777;

    private static final int IMAGE_REQUEST = 1;

    TextInputEditText lab_name, lab_address, lab_phone, lab_email, lab_password;
    AppCompatSpinner lab_city,lab_category, lab_established_year;
    Button lab_select_logo, lab_register;
    TextView lab_logo_name;

    String lab_name_temp, lab_address_temp, lab_city_temp, lab_phone_temp, lab_email_temp;
    String lab_password_temp, lab_category_temp, lab_established_year_temp, lab_logo_name_temp;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_registration_page);


        // EditText
        lab_name = findViewById(R.id.lab_name);
        lab_address = findViewById(R.id.lab_address);

        // Spinner
        lab_city = findViewById(R.id.lab_city);

        // EditText
        lab_phone = findViewById(R.id.lab_phone);

        // Spinner
        lab_category = findViewById(R.id.lab_category);
        lab_established_year = findViewById(R.id.lab_established_year);

        // EditText
        lab_email = findViewById(R.id.lab_email);
        lab_password = findViewById(R.id.lab_password);

        // Button
        lab_select_logo = findViewById(R.id.lab_select_logo);

        //TextView
        lab_logo_name = findViewById(R.id.lab_logo_name);

        // Button
        lab_register = findViewById(R.id.lab_register);

        lab_select_logo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestStoragePermissionThenSelectImage();
            }
        });

        lab_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                lab_city_temp = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        lab_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                lab_category_temp = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        lab_established_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                lab_established_year_temp = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        lab_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_name_temp = lab_name.getText().toString();
                lab_address_temp = lab_address.getText().toString();
                //lab_city_temp in Spinner Onclick Method
                lab_phone_temp = lab_phone.getText().toString();
                //lab_category_temp in Spinner Onclick Method
                //lab_established_year_temp in Spinner Onclick Method
                lab_email_temp = lab_email.getText().toString();
                lab_password_temp = lab_password.getText().toString();
                //lab_logo_name_temp in selectLogo Method

                // part 1
                if(lab_name_temp.equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please fill out the Lab Name Field.", Toast.LENGTH_SHORT).show();
                }
                if(lab_address_temp.equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please fill out the Lab Address Filed.", Toast.LENGTH_SHORT).show();
                }
                if(lab_city_temp.equals("Select Lab City"))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please Select Lab City from List.", Toast.LENGTH_SHORT).show();
                }

                // part 2
                if(lab_phone_temp.equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please fill out the Phone Filed.", Toast.LENGTH_SHORT).show();
                }
                if(lab_phone_temp.length() < 10)
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please Enter the Appropriate Phone Number.", Toast.LENGTH_SHORT).show();
                }

                if(lab_category_temp.equals("Select Appropriate Category"))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please Select Category of Your Lab.", Toast.LENGTH_SHORT).show();
                }

                if(lab_established_year_temp.equals("Select Lab Established Year"))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please Select Established Year of Your Lab.", Toast.LENGTH_SHORT).show();
                }

                if(lab_email_temp.equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }

                if(lab_password_temp.equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                // Critical Part
                if(lab_logo_name.getText().toString().equals(""))
                {
                    Toast.makeText(Lab_Registration_Page.this, "Please Select Your Lab Logo.", Toast.LENGTH_SHORT).show();
                }

                // part 3
                else
                {
                    if (Utility.isNetworkAvailable(Lab_Registration_Page.this))
                    {
                        getLabRegister(lab_name_temp, lab_address_temp, lab_city_temp, lab_phone_temp, lab_category_temp, lab_established_year_temp, lab_email_temp, lab_password_temp, lab_logo_name_temp,bitmap);
                    }
                    else
                    {
                        Toast.makeText(Lab_Registration_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
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
                    .setMessage("Storage Permission needed for Selecting your Lab Logo from Gallery")
                    .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Ask For Permission
                            ActivityCompat.requestPermissions(Lab_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                            // If Permission is Granted then SelectImage Logic
                            if(ContextCompat.checkSelfPermission(Lab_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            {
                                selectLogo();
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
            ActivityCompat.requestPermissions(Lab_Registration_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

            // If Permission is Granted then SelectImage Logic
            if(ContextCompat.checkSelfPermission(Lab_Registration_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                selectLogo();
            }
        }
    }

    private void selectLogo()
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

                lab_logo_name_temp = cursor.getString(name_index);

                lab_logo_name.setText(lab_logo_name_temp);
                lab_logo_name.setVisibility(View.VISIBLE);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    private void getLabRegister(String lab_name, String lab_address, String lab_city, String lab_phone, String lab_category, String lab_established_year, String lab_email, String lab_password, String lab_logo_name, Bitmap imageBitmap)
    {
        String lab_logo_url = Image_URLs.getLabLogoURL(lab_logo_name);

        RequestQueue requestQueue = Volley.newRequestQueue(Lab_Registration_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_address", lab_address);
        jsonParameters.put("lab_city", lab_city);
        jsonParameters.put("lab_phone", lab_phone);
        jsonParameters.put("lab_category", lab_category);
        jsonParameters.put("lab_established_year", lab_established_year);
        jsonParameters.put("lab_email", lab_email);
        jsonParameters.put("lab_password", lab_password);
        jsonParameters.put("lab_logo_name", lab_logo_name);
        jsonParameters.put("lab_logo_url", lab_logo_url);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Lab_Registration_Page.this);
        progressDialog.setMessage("Please Wait Registration for Lab is in Progress");
        progressDialog.setTitle("Lab Registration");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Lab_Registration_Page.this);

        JsonObjectRequest delivery_Registration_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_REGISTRATION, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String lab_return_message = null;

                try
                {
                    lab_return_message =(String) response.get("lab_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if(lab_return_message.equals("Lab Same User Already Exist"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Lab Already Exist");
                    builder.setMessage("The same Lab User Already Exist into the System whose E-mail I'd is as same as yours.\n\nPlease click on 'Ok' to try again with different E-mail I'd");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Registration_Page.this, Lab_Registration_Page.class);
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
                else if(lab_return_message.equals("Lab Register Fail"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Registration_Page.this, "Oops...!! Somehow Lab Registration has been failed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //3rd Possible Response
                else if(lab_return_message.equals("Something went wrong in Lab Register"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Registration_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //4th Possible Response
                else if(lab_return_message.equals("Lab Register Success and Lab Email Sent"))
                {
                    progressDialog.dismiss();

                    // When Positive response came then we have to upload image into server
                    uploadImage(lab_logo_name, lab_email, imageBitmap);
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

    private void uploadImage(String lab_logo_name, String lab_email, Bitmap temp_bitmap)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PHP_API_URLs_For_Image_Upload.LAB_IMAGE_UPLOAD_PHP, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String lab_image_upload_response = jsonObject.getString("lab_image_upload_response");

                    if(lab_image_upload_response.equals("Lab Image Uploaded"))
                    {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(Lab_Registration_Page.this);

                        builder1.setIcon(R.mipmap.logo1);
                        builder1.setTitle("Lab Registration Complete");
                        builder1.setMessage("Woo-Hoo Your Registration as Lab has been Successfully Completed.\n\nPlease Check Mail for OTP for Verification of Account.");

                        builder1.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Lab_Registration_Page.this, Lab_Account_Verification.class);
                                intent.putExtra("lab_sent_mail", lab_email);

                                startActivity(intent);
                                finish();
                            }
                        });

                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Lab_Registration_Page.this, Lab_Login_Page.class);
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
                parameters.put("lab_logo", imageToString(temp_bitmap));
                parameters.put("lab_logo_name", lab_logo_name);
                return parameters;
            }
        };

        MySingletonUserUploadImage.getInstance(Lab_Registration_Page.this).addToRequestQueue(stringRequest);
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
        Intent intent = new Intent(Lab_Registration_Page.this, Lab_Login_Page.class);
        startActivity(intent);
        finish();
    }
}