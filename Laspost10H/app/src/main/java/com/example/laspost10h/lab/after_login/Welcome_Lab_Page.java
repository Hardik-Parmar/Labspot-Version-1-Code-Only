package com.example.laspost10h.lab.after_login;

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
import com.example.laspost10h.SupportClass.LabLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Welcome_Lab_Page extends AppCompatActivity
{
    Toolbar lab_toolbar;
    DrawerLayout lab_drawer;
    NavigationView lab_navigation;

    // Navigation Header Data
    ImageView lab_image_view;
    TextView lab_display_name;

    String lab_received_email;

    // Retrieving all the information
    String lab_name_from_api, lab_address_from_api, lab_city_from_api, lab_phone_from_api;
    String lab_category_from_api, lab_established_year, lab_email_from_api, lab_password_from_api;
    String  lab_logo_name_from_api, lab_logo_url_from_api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_lab_page);

        Bundle bundle = getIntent().getExtras();
        lab_received_email = bundle.getString("lab_sent_mail");

        // Navigation View
        lab_navigation = findViewById(R.id.lab_navigation);

        // Getting VIEW of HEADER PART OF MENU
        View view = lab_navigation.getHeaderView(0);

        // Toolbar
        lab_toolbar = findViewById(R.id.lab_toolbar);

        lab_image_view = (ImageView) view.findViewById(R.id.lab_image_view);
        lab_display_name = (TextView) view.findViewById(R.id.lab_display_name);

        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
        {
            // API CALL PART
            RequestQueue queue = Volley.newRequestQueue(Welcome_Lab_Page.this);

            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("lab_email", lab_received_email);

            // Progress Dialog till Process Completes
            final ProgressDialog progressDialog = new ProgressDialog(Welcome_Lab_Page.this);
            progressDialog.setMessage("Please Wait Some Processes are being in Progress");
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setIcon(R.mipmap.logo1);

            JsonObjectRequest user_Fetch_Data_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_WELCOME_FETCH_DATA, new JSONObject(jsonParams), new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        progressDialog.dismiss();

                        lab_name_from_api = response.getString("lab_name");
                        lab_address_from_api = response.getString("lab_address");
                        lab_city_from_api = response.getString("lab_city");
                        lab_phone_from_api = response.getString("lab_phone");
                        lab_category_from_api = response.getString("lab_category");
                        lab_established_year = response.getString("lab_established_year");
                        lab_email_from_api = response.getString("lab_email");
                        lab_password_from_api = response.getString("lab_password");
                        lab_logo_name_from_api = response.getString("lab_logo_name");
                        lab_logo_url_from_api = response.getString("lab_logo_url");
                        //Toast.makeText(Welcome_User_Page.this, user_name_from_api, Toast.LENGTH_SHORT).show();

                        // Set the Image
                        Picasso.with(Welcome_Lab_Page.this).load(lab_logo_url_from_api).into(lab_image_view);

                        // Set the Name
                        lab_display_name.setText(lab_name_from_api);

                        // Setting the Toolbar Title of HEADER Part
                        lab_toolbar.setTitle(lab_name_from_api);

                        // Setting default Fragment to Home Page
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("lab_sent_mail", lab_email_from_api);
                        bundle1.putString("lab_sent_name", lab_name_from_api);

                        Lab_Home_Page lab_home_page = new Lab_Home_Page();
                        lab_home_page.setArguments(bundle1);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_home_page).commit();
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
                            Toast.makeText(Welcome_Lab_Page.this, "ERROR : "+error+" \n message" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        // Drawer
        lab_drawer = findViewById(R.id.lab_drawer);

        // to view toogle option
        setSupportActionBar(lab_toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Welcome_Lab_Page.this, lab_drawer, lab_toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        lab_drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        lab_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (item.getItemId())
                {
                    case R.id.lab_home:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Home_Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("lab_sent_mail", lab_email_from_api);
                            bundle2.putString("lab_sent_name", lab_name_from_api);

                            Lab_Home_Page lab_home_page = new Lab_Home_Page();
                            lab_home_page.setArguments(bundle2);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_home_page).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_password_change:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Password Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("lab_sent_mail", lab_email_from_api);
                            bundle3.putString("lab_sent_name", lab_name_from_api);

                            Lab_Password_Change lab_password_change = new Lab_Password_Change();
                            lab_password_change.setArguments(bundle3);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_password_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_address_change:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Address Change Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("lab_sent_mail", lab_email_from_api);
                            bundle4.putString("lab_sent_name", lab_name_from_api);

                            Lab_Address_Change lab_address_change = new Lab_Address_Change();
                            lab_address_change.setArguments(bundle4);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_address_change).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_add_test_details:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Add Test Detail Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("lab_sent_mail", lab_email_from_api);
                            bundle5.putString("lab_sent_name", lab_name_from_api);

                            Lab_Add_New_Test_Details lab_add_new_test_details = new Lab_Add_New_Test_Details();
                            lab_add_new_test_details.setArguments(bundle5);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_add_new_test_details).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_view_test_details:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "View Test Detail Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle6 = new Bundle();
                            bundle6.putString("lab_sent_mail", lab_received_email);
                            bundle6.putString("lab_sent_name", lab_name_from_api);

                            Lab_View_Edit_Test_Details lab_view_edit_test_details = new Lab_View_Edit_Test_Details();
                            lab_view_edit_test_details.setArguments(bundle6);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_view_edit_test_details).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_view_pending_order:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            Bundle bundle7 = new Bundle();
                            bundle7.putString("lab_sent_mail", lab_received_email);
                            bundle7.putString("lab_sent_name", lab_name_from_api);

                            Lab_View_Pending_Order lab_view_pending_order = new Lab_View_Pending_Order();
                            lab_view_pending_order.setArguments(bundle7);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_view_pending_order).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_request_for_report_collect:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            Bundle bundle8 = new Bundle();
                            bundle8.putString("lab_sent_mail", lab_received_email);
                            bundle8.putString("lab_sent_name", lab_name_from_api);
                            bundle8.putString("lab_sent_city", lab_city_from_api);

                            Lab_Request_For_Report_Collect lab_request_for_report_collect = new Lab_Request_For_Report_Collect();
                            lab_request_for_report_collect.setArguments(bundle8);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_request_for_report_collect).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.lab_view_past_order:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Lab View Past Order Clicked", Toast.LENGTH_SHORT).show();

                            Bundle bundle9 = new Bundle();
                            bundle9.putString("lab_sent_mail", lab_received_email);
                            bundle9.putString("lab_sent_name", lab_name_from_api);
                            bundle9.putString("lab_sent_city", lab_city_from_api);
                            bundle9.putString("lab_sent_address", lab_address_from_api);

                            Lab_Test_History lab_test_history = new Lab_Test_History();
                            lab_test_history.setArguments(bundle9);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_test_history).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.lab_about_us:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Lab About Us Clicked", Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, new Lab_About_Us()).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_contact_us:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            //Toast.makeText(Welcome_Lab_Page.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                            Bundle bundle10 = new Bundle();
                            bundle10.putString("lab_sent_mail", lab_email_from_api);
                            bundle10.putString("lab_sent_name", lab_name_from_api);

                            Lab_Contact_Us lab_contact_us = new Lab_Contact_Us();
                            lab_contact_us.setArguments(bundle10);

                            fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_contact_us).addToBackStack(null).commit();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.lab_logout:
                        if(Utility.isNetworkAvailable(Welcome_Lab_Page.this))
                        {
                            LabLoginSessionManagement labLoginSessionManagement = new LabLoginSessionManagement(Welcome_Lab_Page.this);
                            labLoginSessionManagement.removeLabDetailsFromSession();

                            Intent intent = new Intent(Welcome_Lab_Page.this, App_Main_Page.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(Welcome_Lab_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

                lab_drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (lab_drawer.isDrawerOpen(GravityCompat.START))
        {
            lab_drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}