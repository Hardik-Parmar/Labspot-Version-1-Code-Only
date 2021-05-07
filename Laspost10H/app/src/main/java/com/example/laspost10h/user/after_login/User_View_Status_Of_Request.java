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
import com.example.laspost10h.user.after_login.usersupportclass.UserViewStatusOfRequestCustomAdapter;
import com.example.laspost10h.user.after_login.usersupportclass.User_View_Status_Of_Request_Contents;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User_View_Status_Of_Request extends Fragment
{
    View view;

    ListView user_status_of_test_request_list_view;

    private boolean success = false;

    String user_received_name, user_received_email;

    String applicant_id_from_api, lab_name_from_api, test_name_from_api, test_price_from_api;

    String date_time_of_test_order_from_api, lab_accept_order_from_api, date_of_rejection_from_api;

    private ArrayList<User_View_Status_Of_Request_Contents> user_view_status_of_request_contents;

    UserViewStatusOfRequestCustomAdapter userViewStatusOfRequestCustomAdapter;

    SwipeRefreshLayout user_view_request_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_view_status_of_request, container, false);

        // ListView
        user_status_of_test_request_list_view = (ListView) view.findViewById(R.id.user_status_of_test_request_list_view);

        user_view_status_of_request_contents = new ArrayList<User_View_Status_Of_Request_Contents>();

        // Swipe Refresh
        user_view_request_refresh = (SwipeRefreshLayout) view.findViewById(R.id.user_view_request_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        user_view_request_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_name", user_received_name);

                User_View_Status_Of_Request user_view_status_of_request = new User_View_Status_Of_Request();
                user_view_status_of_request.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_view_status_of_request).detach(user_view_status_of_request).attach(user_view_status_of_request).commit();
            }
        });

        Bundle bundle = getArguments();
        user_received_email = bundle.getString("user_sent_mail");
        user_received_name = bundle.getString("user_sent_name");

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
            progressDialog.setMessage("Please Wait Fetching Your Test Request Data from the Server");
            progressDialog.setTitle("Fetching Test Request Data");
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
                    String query = "SELECT * FROM test_transaction_details WHERE applicant_name = ?  AND applicant_email = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, user_received_name);
                    //preparedStatement.setString(2, "PENDING");
                    preparedStatement.setString(2, user_received_email);

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
                                    lab_name_from_api = resultSet.getString("lab_name");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_time_of_test_order_from_api = resultSet.getString("date_time_of_test_order");
                                    test_price_from_api = resultSet.getString("test_price");
                                    lab_accept_order_from_api = resultSet.getString("lab_accept_order");

                                    date_of_rejection_from_api = "NONE";

                                    user_view_status_of_request_contents.add(0, new User_View_Status_Of_Request_Contents(applicant_id_from_api, lab_name_from_api, test_name_from_api, date_time_of_test_order_from_api, test_price_from_api, lab_accept_order_from_api, date_of_rejection_from_api));

                                    msg = "Found";

                                    success = true;
                                }

                                if(resultSet.getString("lab_accept_order").equals("REJECTED"))
                                {
                                    applicant_id_from_api = resultSet.getString("id");
                                    lab_name_from_api = resultSet.getString("lab_name");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_time_of_test_order_from_api = resultSet.getString("date_time_of_test_order");
                                    test_price_from_api = resultSet.getString("test_price");
                                    lab_accept_order_from_api = resultSet.getString("lab_accept_order");
                                    date_of_rejection_from_api = resultSet.getString("date_time_of_order_accepted");

                                    user_view_status_of_request_contents.add(0, new User_View_Status_Of_Request_Contents(applicant_id_from_api, lab_name_from_api, test_name_from_api, date_time_of_test_order_from_api, test_price_from_api, lab_accept_order_from_api, date_of_rejection_from_api));

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
                user_status_of_test_request_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "You Haven't made any Test Request Yet.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    userViewStatusOfRequestCustomAdapter = new UserViewStatusOfRequestCustomAdapter(getActivity(), user_view_status_of_request_contents);
                    user_status_of_test_request_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    user_status_of_test_request_list_view.setAdapter(userViewStatusOfRequestCustomAdapter);
                    user_status_of_test_request_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}