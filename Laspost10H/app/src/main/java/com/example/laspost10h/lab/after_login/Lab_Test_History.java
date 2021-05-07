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
import android.widget.ListView;
import android.widget.Toast;

import com.example.laspost10h.Confidential_Details.DatabaseConnectionDetails;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.lab.after_login.labsupportclass.LabTestHistoryCustomAdapter;
import com.example.laspost10h.lab.after_login.labsupportclass.Lab_Test_History_Content;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Lab_Test_History extends Fragment
{
    View view;

    ListView lab_past_test_request_list_view;

    SwipeRefreshLayout lab_test_history_refresh;

    String lab_received_name, lab_received_email, lab_received_city, lab_received_address;

    private ArrayList<Lab_Test_History_Content> lab_test_history_contents;

    LabTestHistoryCustomAdapter labTestHistoryCustomAdapter;

    private boolean success = false;

    String request_id_from_api, applicant_name_from_api, test_name_from_api;
    String date_of_order_from_api, date_of_report_dispatched, date_of_report_delivered_from_api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_test_history, container, false);

        Bundle bundle = getArguments();
        lab_received_name = bundle.getString("lab_sent_name");
        lab_received_email = bundle.getString("lab_sent_mail");
        lab_received_city = bundle.getString("lab_sent_city");
        lab_received_address = bundle.getString("lab_sent_address");

        // List View
        lab_past_test_request_list_view = (ListView) view.findViewById(R.id.lab_past_test_request_list_view);

        //SwipeRefresh
        lab_test_history_refresh  = (SwipeRefreshLayout) view.findViewById(R.id.lab_test_history_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        lab_test_history_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_sent_name", lab_received_name);
                bundle1.putString("lab_sent_mail", lab_received_email);
                bundle1.putString("lab_sent_city", lab_received_city);
                bundle1.putString("lab_sent_address", lab_received_address);

                Lab_Test_History lab_test_history = new Lab_Test_History();
                lab_test_history.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_test_history).detach(lab_test_history).attach(lab_test_history).commit();
            }
        });

        lab_test_history_contents = new ArrayList<Lab_Test_History_Content>();

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

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
            progressDialog.setMessage("Please Wait Fetching Past Completed Test Requests from the Server");
            progressDialog.setTitle("Fetching Past Completed Test Request");
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
                    String query = "SELECT * FROM test_transaction_details where lab_name = ?  AND lab_email = ? AND test_transaction_city = ? AND lab_address = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, lab_received_name);
                    preparedStatement.setString(2, lab_received_email);
                    preparedStatement.setString(3, lab_received_city);
                    preparedStatement.setString(4, lab_received_address);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                if(resultSet.getString("whole_cycle_completed").equals("COMPLETED"))
                                {
                                    request_id_from_api = resultSet.getString("id");
                                    applicant_name_from_api = resultSet.getString("applicant_name");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_of_order_from_api = resultSet.getString("date_time_of_test_order");
                                    date_of_report_dispatched = resultSet.getString("date_time_of_delivery_boy_collect_report_2");
                                    date_of_report_delivered_from_api = resultSet.getString("date_time_of_delivery_boy_arrive_2");

                                    lab_test_history_contents.add(0, new Lab_Test_History_Content(request_id_from_api, applicant_name_from_api, test_name_from_api, date_of_order_from_api, date_of_report_dispatched, date_of_report_delivered_from_api));

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
                lab_past_test_request_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Completed Test Request. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    labTestHistoryCustomAdapter = new LabTestHistoryCustomAdapter(getActivity(), lab_test_history_contents);
                    lab_past_test_request_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_past_test_request_list_view.setAdapter(labTestHistoryCustomAdapter);
                    lab_past_test_request_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}