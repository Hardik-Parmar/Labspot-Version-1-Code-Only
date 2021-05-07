package com.example.laspost10h.user.after_login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laspost10h.Confidential_Details.DatabaseConnectionDetails;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.user.after_login.usersupportclass.UserHomePageCustomAdapter;
import com.example.laspost10h.user.after_login.usersupportclass.User_Lab_Display_Content;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User_Home_Page extends Fragment
{
    View view;

    private ArrayList<User_Lab_Display_Content> lab_display_contents;

    private ListView user_home_lab_list_view;

    private boolean success = false;

    String user_received_email, user_received_name, user_received_city, user_received_address, user_received_phone;

    String lab_name_from_api, lab_address_from_api, lab_city_from_api, lab_phone_from_api;
    String lab_category_from_api, lab_established_year, lab_email_from_api;
    String lab_logo_name_from_api, lab_logo_url_from_api;

    UserHomePageCustomAdapter userHomePageCustomAdapter;

    SwipeRefreshLayout user_home_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_home_page, container, false);

        Bundle bundle = getArguments();
        user_received_email = bundle.getString("user_sent_mail");
        user_received_name = bundle.getString("user_sent_name");
        user_received_city = bundle.getString("user_sent_city");
        user_received_address = bundle.getString("user_sent_address");
        user_received_phone = bundle.getString("user_sent_phone");

        //Toast.makeText(getActivity(), "CITYYYY  "+user_received_name, Toast.LENGTH_LONG).show();

        // ListView
        user_home_lab_list_view = (ListView) view.findViewById(R.id.user_home_lab_list_view);

        //Swipe Refresh
        user_home_refresh = (SwipeRefreshLayout) view.findViewById(R.id.user_home_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        user_home_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_city", user_received_city);
                bundle1.putString("user_sent_address", user_received_address);
                bundle1.putString("user_sent_phone", user_received_phone);

                User_Home_Page user_home_page = new User_Home_Page();
                user_home_page.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_home_page).detach(user_home_page).attach(user_home_page).commit();
            }
        });

        lab_display_contents = new ArrayList<User_Lab_Display_Content>();

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        user_home_lab_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_lab_logo_url", lab_display_contents.get(position).getLab_logo_url());
                bundle1.putString("user_lab_name", lab_display_contents.get(position).getLab_name());
                bundle1.putString("user_lab_address", lab_display_contents.get(position).getLab_address());
                bundle1.putString("user_lab_category", lab_display_contents.get(position).getLab_category());
                bundle1.putString("user_lab_phone", lab_display_contents.get(position).getLab_phone());
                bundle1.putString("user_lab_mail", lab_display_contents.get(position).getLab_email());

                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_address", user_received_address);
                bundle1.putString("user_sent_phone", user_received_phone);
                bundle1.putString("user_sent_city", user_received_city);

                User_After_Clicked_Lab_Detailed_Page user_after_clicked_lab_detailed_page = new User_After_Clicked_Lab_Detailed_Page();
                user_after_clicked_lab_detailed_page.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_after_clicked_lab_detailed_page).addToBackStack(null).commit();

            }
        });

        return view;
    }

    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg;

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() //Starts the progress dialog
        {
            progressDialog.setMessage("Please Wait Fetching Laboratories Data from the Server");
            progressDialog.setTitle("Fetching Laboratories Data");
            progressDialog.show();
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setIcon(R.mipmap.logo1);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                Class.forName(DatabaseConnectionDetails.JDBC_DRIVER).newInstance();

                Connection connection = DriverManager.getConnection(DatabaseConnectionDetails.DATABASE_URL, DatabaseConnectionDetails.DATABASE_USERNAME, DatabaseConnectionDetails.DATABASE_PASSWORD); //Connection Object

                if (connection == null)
                {
                    success = false;
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "SELECT * FROM lab_register where lab_city = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, user_received_city);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                lab_name_from_api = resultSet.getString("lab_name");
                                lab_address_from_api = resultSet.getString("lab_address");
                                lab_city_from_api = resultSet.getString("lab_city");
                                lab_phone_from_api = resultSet.getString("lab_phone");
                                lab_category_from_api = resultSet.getString("lab_category");
                                lab_established_year = resultSet.getString("lab_established_year");
                                lab_email_from_api = resultSet.getString("lab_email");
                                lab_logo_name_from_api = resultSet.getString("lab_logo_name");
                                lab_logo_url_from_api = resultSet.getString("lab_logo_url");

                                lab_display_contents.add(new User_Lab_Display_Content(lab_name_from_api, lab_address_from_api, lab_city_from_api, lab_phone_from_api, lab_category_from_api, lab_established_year, lab_email_from_api, lab_logo_name_from_api, lab_logo_url_from_api));

                                msg = "Found";

                                success = true;
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        msg = "No Data found!";
                        success = false;
                    }
                }
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // dismissing the progress dialog, showing error and setting up my ListView
        {
            progressDialog.dismiss();
            System.out.println(msg);
            //Toast.makeText(getActivity(), "msg  " + msg, Toast.LENGTH_SHORT).show();
            if (success == false)
            {
                // Nothing
                user_home_lab_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Laboratory are Registered in your City yet. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    userHomePageCustomAdapter = new UserHomePageCustomAdapter(getActivity(), lab_display_contents);
                    user_home_lab_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    user_home_lab_list_view.setAdapter(userHomePageCustomAdapter);
                    user_home_lab_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}