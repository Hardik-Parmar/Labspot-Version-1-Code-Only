package com.example.laspost10h.lab.after_login;

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
import com.example.laspost10h.lab.after_login.labsupportclass.LabNewTestRequestCustomAdapter;
import com.example.laspost10h.lab.after_login.labsupportclass.Lab_New_Test_Request_Contents;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Lab_Home_Page extends Fragment
{
    View view;

    String lab_received_name, lab_received_email;

    ListView lab_new_test_request_list_view;

    private boolean success = false;

    private ArrayList<Lab_New_Test_Request_Contents> lab_new_test_request_contents;

    String applicant_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api;
    String test_name_from_api, date_of_request_from_api;

    LabNewTestRequestCustomAdapter labNewTestRequestCustomAdapter;

    SwipeRefreshLayout lab_home_page_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_home_page, container, false);

        Bundle bundle = getArguments();
        lab_received_name = bundle.getString("lab_sent_name");
        lab_received_email = bundle.getString("lab_sent_mail");

        lab_new_test_request_contents = new ArrayList<Lab_New_Test_Request_Contents>();

        //ListView
        lab_new_test_request_list_view = (ListView) view.findViewById(R.id.lab_new_test_request_list_view);

        //Swipe Refresh Layout
        lab_home_page_refresh = (SwipeRefreshLayout) view.findViewById(R.id.lab_home_page_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        lab_home_page_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_sent_mail", lab_received_email);
                bundle1.putString("lab_sent_name", lab_received_name);

                Lab_Home_Page lab_home_page = new Lab_Home_Page();
                lab_home_page.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_home_page).detach(lab_home_page).attach(lab_home_page).commit();
            }
        });

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        lab_new_test_request_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("request_id", lab_new_test_request_contents.get(position).getApplicant_id());
                bundle1.putString("user_name", lab_new_test_request_contents.get(position).getUser_name());
                bundle1.putString("user_mail", lab_new_test_request_contents.get(position).getUser_email_temp());
                bundle1.putString("user_phone", lab_new_test_request_contents.get(position).getUser_phone());
                bundle1.putString("test_name", lab_new_test_request_contents.get(position).getTest_name());
                bundle1.putString("date_of_request", lab_new_test_request_contents.get(position).getDate_of_request());

                Lab_Take_Action_On_Test_Request lab_take_action_on_test_request = new Lab_Take_Action_On_Test_Request();
                lab_take_action_on_test_request.setArguments(bundle1);

                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.lab_fragment_container, lab_take_action_on_test_request).addToBackStack(null).commit();
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
            progressDialog.setMessage("Please Wait Fetching New Test Requests from the Server");
            progressDialog.setTitle("Fetching New Test Requests");
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
                    String query = "SELECT * FROM test_transaction_details WHERE lab_name = ?  AND lab_email = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, lab_received_name);
                    //preparedStatement.setString(2, "PENDING");
                    preparedStatement.setString(2, lab_received_email);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                if(resultSet.getString("lab_accept_order").equals("PENDING"))
                                {
                                    applicant_id_from_api = resultSet.getString("id");
                                    user_name_from_api = resultSet.getString("applicant_name");
                                    user_phone_from_api = resultSet.getString("applicant_phone");
                                    user_email_from_api = resultSet.getString("applicant_email");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_of_request_from_api = resultSet.getString("date_time_of_test_order");

                                    lab_new_test_request_contents.add(0, new Lab_New_Test_Request_Contents(applicant_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api, test_name_from_api, date_of_request_from_api));

                                    msg = "Found";

                                    success = true;
                                }
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
                lab_new_test_request_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Request made by Customer. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    labNewTestRequestCustomAdapter = new LabNewTestRequestCustomAdapter(getActivity(), lab_new_test_request_contents);
                    lab_new_test_request_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_new_test_request_list_view.setAdapter(labNewTestRequestCustomAdapter);
                    lab_new_test_request_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}