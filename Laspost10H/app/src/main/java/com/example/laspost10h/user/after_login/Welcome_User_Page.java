package com.example.laspost10h.user.after_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.App_Main_Page;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.UserLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Welcome_User_Page extends AppCompatActivity
{
    Toolbar user_toolbar;
    DrawerLayout user_drawer;
    NavigationView user_navigation;

    // Navigation Header Data
    ImageView user_image_view;
    TextView user_display_name;

    String user_received_email;

    // Retrieving all the information
    String user_name_from_api, user_address_from_api, user_city_from_api, user_phone_from_api;
    String user_dob_from_api, user_email_from_api, user_password_from_api, user_image_name_from_api;
    String user_image_url_from_api;

    public boolean data = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user_page);

        Bundle bundle = getIntent().getExtras();
        user_received_email = bundle.getString("user_sent_mail");

        // Navigation View
        user_navigation = findViewById(R.id.user_navigation);

        // Getting VIEW of HEADER PART OF MENU
        View view = user_navigation.getHeaderView(0);

        // Toolbar
        user_toolbar = findViewById(R.id.user_toolbar);

        user_image_view = (ImageView) view.findViewById(R.id.user_image_view);
        user_display_name = (TextView) view.findViewById(R.id.user_display_name);

        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
        {
            // API CALL PART
            RequestQueue queue = Volley.newRequestQueue(Welcome_User_Page.this);

            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("user_email", user_received_email);

            // Progress Dialog till Process Completes
            final ProgressDialog progressDialog = new ProgressDialog(Welcome_User_Page.this);
            progressDialog.setMessage("Please Wait Some Processes are being in Progress");
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setIcon(R.mipmap.logo1);

            JsonObjectRequest user_Fetch_Data_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_WELCOME_FETCH_DATA, new JSONObject(jsonParams), new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        progressDialog.dismiss();

                        user_name_from_api = response.getString("user_name");
                        user_address_from_api = response.getString("user_address");
                        user_city_from_api = response.getString("user_city");
                        user_phone_from_api = response.getString("user_phone");
                        user_dob_from_api = response.getString("user_DOB");
                        user_email_from_api = response.getString("user_email");
                        user_password_from_api = response.getString("user_password");
                        user_image_name_from_api = response.getString("user_image_name");
                        user_image_url_from_api = response.getString("user_image_url");
                        //Toast.makeText(Welcome_User_Page.this, user_city_from_api, Toast.LENGTH_SHORT).show();
                        data = true;

                        // Set the Image
                        Picasso.with(Welcome_User_Page.this).load(user_image_url_from_api).into(user_image_view);

                        // Set the Name
                        user_display_name.setText(user_name_from_api);

                        // Setting the Toolbar Title of HEADER Part
                        user_toolbar.setTitle(user_name_from_api);

                        // Setting default Fragment to Home Page
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("user_sent_mail", user_email_from_api);
                        bundle1.putString("user_sent_name", user_name_from_api);
                        bundle1.putString("user_sent_city", user_city_from_api);
                        bundle1.putString("user_sent_address", user_address_from_api);
                        bundle1.putString("user_sent_phone", user_phone_from_api);

                        //Toast.makeText(Welcome_User_Page.this, "nameeeeee " + user_name_from_api, Toast.LENGTH_SHORT).show();

                        User_Home_Page user_home_page = new User_Home_Page();
                        user_home_page.setArguments(bundle1);

                        int temp = fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_home_page).commit();
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
                            Toast.makeText(Welcome_User_Page.this, "ERROR : "+error+" \n message" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            user_Fetch_Data_POST_Request.setRetryPolicy(new RetryPolicy()
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

            queue.add(user_Fetch_Data_POST_Request);
        }
        else
        {
            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        // Drawer
        user_drawer = findViewById(R.id.user_drawer);

        // to view toogle option
        setSupportActionBar(user_toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Welcome_User_Page.this,user_drawer, user_toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        user_drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        user_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (item.getItemId())
                {
                    case R.id.user_home:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Home_Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("user_sent_mail", user_email_from_api);
                            bundle2.putString("user_sent_name", user_name_from_api);
                            bundle2.putString("user_sent_city", user_city_from_api);
                            bundle2.putString("user_sent_address", user_address_from_api);
                            bundle2.putString("user_sent_phone", user_phone_from_api);

                            User_Home_Page user_home_page = new User_Home_Page();
                            user_home_page.setArguments(bundle2);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_home_page).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_status_of_request:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Status Of Request Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("user_sent_mail", user_email_from_api);
                            bundle3.putString("user_sent_name", user_name_from_api);

                            User_View_Status_Of_Request user_view_status_of_request = new User_View_Status_Of_Request();
                            user_view_status_of_request.setArguments(bundle3);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_view_status_of_request).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_request_for_sample_collect:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Delivery Boy Request Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("user_sent_mail", user_email_from_api);
                            bundle3.putString("user_sent_name", user_name_from_api);
                            bundle3.putString("user_sent_city", user_city_from_api);

                            User_Request_For_Sample_Collect user_request_for_sample_collect = new User_Request_For_Sample_Collect();
                            user_request_for_sample_collect.setArguments(bundle3);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_request_for_sample_collect).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_password_change:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Password Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("user_sent_mail", user_email_from_api);
                            bundle4.putString("user_sent_name", user_name_from_api);

                            User_Password_Change user_password_change = new User_Password_Change();
                            user_password_change.setArguments(bundle4);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_password_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_address_change:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Address Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("user_sent_mail", user_email_from_api);
                            bundle5.putString("user_sent_name", user_name_from_api);

                            User_Address_Change user_address_change = new User_Address_Change();
                            user_address_change.setArguments(bundle5);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_address_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_test_history:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Test History Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle6 = new Bundle();
                            bundle6.putString("user_sent_mail", user_email_from_api);
                            bundle6.putString("user_sent_name", user_name_from_api);
                            bundle6.putString("user_sent_city", user_city_from_api);
                            bundle6.putString("user_sent_address", user_address_from_api);

                            User_Test_History user_test_history = new User_Test_History();
                            user_test_history.setArguments(bundle6);

                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_test_history).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_about_us:
                        if(Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "User About Us Clicked", Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, new User_About_Us()).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_contact_us:
                        if (Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            //Toast.makeText(Welcome_User_Page.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle8 = new Bundle();
                            bundle8.putString("user_sent_mail", user_email_from_api);
                            bundle8.putString("user_sent_name", user_name_from_api);

                            User_Contact_Us user_contact_us = new User_Contact_Us();
                            user_contact_us.setArguments(bundle8);
                            fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_contact_us).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.user_logout:
                        if (Utility.isNetworkAvailable(Welcome_User_Page.this))
                        {
                            UserLoginSessionManagement userLoginSessionManagement = new UserLoginSessionManagement(Welcome_User_Page.this);
                            userLoginSessionManagement.removeUserDetailsFromSession();

                            Intent intent = new Intent(Welcome_User_Page.this, App_Main_Page.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(Welcome_User_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

                user_drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (user_drawer.isDrawerOpen(GravityCompat.START))
        {
            user_drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}