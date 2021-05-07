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
import android.widget.ListView;
import android.widget.Toast;

import com.example.laspost10h.Confidential_Details.DatabaseConnectionDetails;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.user.after_login.usersupportclass.UserTestHistoryCustomAdapter;
import com.example.laspost10h.user.after_login.usersupportclass.User_Test_History_Content;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User_Test_History extends Fragment
{
    View view;

    ListView user_past_test_request_list_view;

    SwipeRefreshLayout user_test_history_refresh;

    String user_received_name, user_received_email, user_received_city, user_received_address;

    private ArrayList<User_Test_History_Content> user_test_history_contents;

    UserTestHistoryCustomAdapter userTestHistoryCustomAdapter;

    private boolean success = false;

    String request_id_from_api, lab_name_from_api, test_name_from_api, test_price_from_api;
    String date_of_order_from_api, date_of_report_delivered_from_api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_test_history, container, false);

        Bundle bundle = getArguments();
        user_received_name = bundle.getString("user_sent_name");
        user_received_email = bundle.getString("user_sent_mail");
        user_received_city = bundle.getString("user_sent_city");
        user_received_address = bundle.getString("user_sent_address");

        // List View
        user_past_test_request_list_view = (ListView) view.findViewById(R.id.user_past_test_request_list_view);

        //SwipeRefresh
        user_test_history_refresh  = (SwipeRefreshLayout) view.findViewById(R.id.user_test_history_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        user_test_history_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_city", user_received_city);
                bundle1.putString("user_sent_address", user_received_address);

                User_Test_History user_test_history = new User_Test_History();
                user_test_history.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_test_history).detach(user_test_history).attach(user_test_history).commit();
            }
        });

        user_test_history_contents = new ArrayList<User_Test_History_Content>();

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
                    String query = "SELECT * FROM test_transaction_details where applicant_name = ?  AND applicant_email = ? AND test_transaction_city = ? AND applicant_address = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, user_received_name);
                    preparedStatement.setString(2, user_received_email);
                    preparedStatement.setString(3, user_received_city);
                    preparedStatement.setString(4, user_received_address);

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
                                    lab_name_from_api = resultSet.getString("lab_name");
                                    test_name_from_api = resultSet.getString("test_name");
                                    test_price_from_api = resultSet.getString("test_price");
                                    date_of_order_from_api = resultSet.getString("date_time_of_test_order");
                                    date_of_report_delivered_from_api = resultSet.getString("date_time_of_delivery_boy_arrive_2");

                                    user_test_history_contents.add(0, new User_Test_History_Content(request_id_from_api, lab_name_from_api, test_name_from_api, test_price_from_api, date_of_order_from_api, date_of_report_delivered_from_api));

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
                user_past_test_request_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Completed Test Request. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    userTestHistoryCustomAdapter = new UserTestHistoryCustomAdapter(getActivity(), user_test_history_contents);
                    user_past_test_request_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    user_past_test_request_list_view.setAdapter(userTestHistoryCustomAdapter);
                    user_past_test_request_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}