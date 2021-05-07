package com.example.laspost10h.delivery.after_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.App_Main_Page;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.DeliveryLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Welcome_Delivery_Page extends AppCompatActivity
{
    Toolbar delivery_toolbar;
    DrawerLayout delivery_drawer;
    NavigationView delivery_navigation;

    // Navigation Header Data
    ImageView delivery_image_view;
    TextView delivery_display_name;

    String delivery_received_email;

    // Retrieving all the information
    String delivery_name_from_api, delivery_address_from_api, delivery_city_from_api, delivery_phone_from_api;
    String delivery_dob_from_api, delivery_email_from_api, delivery_password_from_api, delivery_image_name_from_api;
    String delivery_image_url_from_api, delivery_available_from_api, delivery_busy_from_api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_delivery_page);

        Bundle bundle = getIntent().getExtras();
        delivery_received_email = bundle.getString("delivery_sent_mail");

        // Navigation View
        delivery_navigation = findViewById(R.id.delivery_navigation);

        // Getting VIEW of HEADER PART OF MENU
        View view = delivery_navigation.getHeaderView(0);

        // Toolbar
        delivery_toolbar = findViewById(R.id.delivery_toolbar);

        delivery_image_view = (ImageView) view.findViewById(R.id.delivery_image_view);
        delivery_display_name = (TextView) view.findViewById(R.id.delivery_display_name);

        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
        {
            // API CALL PART
            RequestQueue queue = Volley.newRequestQueue(Welcome_Delivery_Page.this);

            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("delivery_email", delivery_received_email);

            // Progress Dialog till Process Completes
            final ProgressDialog progressDialog = new ProgressDialog(Welcome_Delivery_Page.this);
            progressDialog.setMessage("Please Wait Some Processes are being in Progress");
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setIcon(R.mipmap.logo1);

            JsonObjectRequest delivery_Fetch_Data_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_WELCOME_FETCH_DATA, new JSONObject(jsonParams), new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        progressDialog.dismiss();

                        delivery_name_from_api = response.getString("delivery_name");
                        delivery_address_from_api = response.getString("delivery_address");
                        delivery_city_from_api = response.getString("delivery_city");
                        delivery_phone_from_api = response.getString("delivery_phone");
                        delivery_dob_from_api = response.getString("delivery_dob");
                        delivery_email_from_api = response.getString("delivery_email");
                        delivery_password_from_api = response.getString("delivery_password");
                        delivery_image_name_from_api= response.getString("delivery_image_name");
                        delivery_image_url_from_api = response.getString("delivery_image_url");
                        delivery_available_from_api = response.getString("delivery_available");
                        delivery_busy_from_api = response.getString("delivery_busy");
                        //Toast.makeText(Welcome_Delivery_Page.this, delivery_name_from_api, Toast.LENGTH_SHORT).show();

                        // Set the Image
                        Picasso.with(Welcome_Delivery_Page.this).load(delivery_image_url_from_api).into(delivery_image_view);

                        // Set the Name
                        delivery_display_name.setText(delivery_name_from_api);

                        // Setting the Toolbar Title of HEADER Part
                        delivery_toolbar.setTitle(delivery_name_from_api);

                        // Setting default Fragment to Home Page
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("delivery_sent_name", delivery_name_from_api);
                        bundle1.putString("delivery_sent_phone", delivery_phone_from_api);
                        bundle1.putString("delivery_sent_mail", delivery_email_from_api);
                        bundle1.putString("delivery_sent_address", delivery_address_from_api);

                        Delivery_Home_Page delivery_home_page = new Delivery_Home_Page();
                        delivery_home_page.setArguments(bundle1);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        int temp = fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_home_page).commit();
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
                            Toast.makeText(Welcome_Delivery_Page.this, "ERROR : "+error+" \n message" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            delivery_Fetch_Data_POST_Request.setRetryPolicy(new RetryPolicy()
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

            queue.add(delivery_Fetch_Data_POST_Request);
        }
        else
        {
            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        // Drawer
        delivery_drawer = findViewById(R.id.delivery_drawer);

        // to view toogle option
        setSupportActionBar(delivery_toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Welcome_Delivery_Page.this, delivery_drawer, delivery_toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        delivery_drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        delivery_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (item.getItemId())
                {
                    case R.id.delivery_home:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            //Toast.makeText(Welcome_Delivery_Page.this, "Home_Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("delivery_sent_name", delivery_name_from_api);
                            bundle2.putString("delivery_sent_phone", delivery_phone_from_api);
                            bundle2.putString("delivery_sent_mail", delivery_email_from_api);
                            bundle2.putString("delivery_sent_address", delivery_address_from_api);

                            Delivery_Home_Page delivery_home_page = new Delivery_Home_Page();
                            delivery_home_page.setArguments(bundle2);

                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_home_page).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.delivery_password_change:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            //Toast.makeText(Welcome_Delivery_Page.this, "Password Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("delivery_sent_mail", delivery_email_from_api);
                            bundle3.putString("delivery_sent_name", delivery_name_from_api);

                            Delivery_Password_Change delivery_password_change = new Delivery_Password_Change();
                            delivery_password_change.setArguments(bundle3);

                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_password_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.delivery_address_change:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            //Toast.makeText(Welcome_Delivery_Page.this, "Address Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("delivery_sent_mail", delivery_email_from_api);
                            bundle4.putString("delivery_sent_name", delivery_name_from_api);

                            Delivery_Address_Change delivery_address_change = new Delivery_Address_Change();
                            delivery_address_change.setArguments(bundle4);

                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_address_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.delivery_about_us:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            //Toast.makeText(Welcome_Delivery_Page.this, "Delivery About Us Clicked", Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, new Delivery_About_Us()).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.delivery_contact_us:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            //Toast.makeText(Welcome_Delivery_Page.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("delivery_sent_mail", delivery_email_from_api);
                            bundle5.putString("delivery_sent_name", delivery_name_from_api);

                            Delivery_Contact_Us delivery_contact_us = new Delivery_Contact_Us();
                            delivery_contact_us.setArguments(bundle5);

                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_contact_us).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.delivery_logout:
                        if(Utility.isNetworkAvailable(Welcome_Delivery_Page.this))
                        {
                            DeliveryLoginSessionManagement deliveryLoginSessionManagement = new DeliveryLoginSessionManagement(Welcome_Delivery_Page.this);
                            deliveryLoginSessionManagement.removeDeliveryDetailsFromSession();

                            Intent intent = new Intent(Welcome_Delivery_Page.this, App_Main_Page.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Delivery_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

                delivery_drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (delivery_drawer.isDrawerOpen(GravityCompat.START))
        {
            delivery_drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}