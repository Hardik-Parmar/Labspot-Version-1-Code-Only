package com.example.laspost10h.user.after_login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.Confidential_Details.DatabaseConnectionDetails;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.user.after_login.usersupportclass.UserRequestForSampleCollectCustomAdapter;
import com.example.laspost10h.user.after_login.usersupportclass.User_View_Accept_Request_For_Sample_Collect_Contents;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_Request_For_Sample_Collect extends Fragment
{
    View view;

    ListView user_view_accept_request_for_sample_collect_list_view;

    private boolean success = false;

    String user_received_name, user_received_email, user_received_city;

    String applicant_id_from_api, lab_name_from_api, test_name_from_api, test_price_from_api;

    String date_time_of_test_order_from_api, lab_accept_order_from_api, date_of_accept_from_api, lab_email_from_api;

    String delivery_boy_accept_from_api;

    private ArrayList<User_View_Accept_Request_For_Sample_Collect_Contents> user_view_accept_request_for_sample_collect_contents;

    UserRequestForSampleCollectCustomAdapter userRequestForSampleCollectCustomAdapter;

    SwipeRefreshLayout user_accept_request_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_request_for_sample_collect, container, false);

        // ListView
        user_view_accept_request_for_sample_collect_list_view = (ListView) view.findViewById(R.id.user_view_accept_request_for_sample_collect_list_view);

        user_view_accept_request_for_sample_collect_contents = new ArrayList<User_View_Accept_Request_For_Sample_Collect_Contents>();

        // Swipe Refresh
        user_accept_request_refresh = (SwipeRefreshLayout) view.findViewById(R.id.user_accept_request_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        user_accept_request_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_city", user_received_city);

                User_Request_For_Sample_Collect user_request_for_sample_collect = new User_Request_For_Sample_Collect();
                user_request_for_sample_collect.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_request_for_sample_collect).detach(user_request_for_sample_collect).attach(user_request_for_sample_collect).commit();
            }
        });

        Bundle bundle = getArguments();
        user_received_email = bundle.getString("user_sent_mail");
        user_received_name = bundle.getString("user_sent_name");
        user_received_city = bundle.getString("user_sent_city");

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData1 orderData = new SyncData1();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        user_view_accept_request_for_sample_collect_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    if(user_view_accept_request_for_sample_collect_contents.get(position).getDelivery_boy_accept().equals("ACCEPTED"))
                    {
                        Toast.makeText(getActivity(), "Please Wait for Delivery Boy to Collect Sample from you.", Toast.LENGTH_SHORT).show();
                    }
                    else if(user_view_accept_request_for_sample_collect_contents.get(position).getDelivery_boy_accept().equals("PENDING"))
                    {
                        Toast.makeText(getActivity(), "Please Wait for Delivery Boy to take action on your Request.", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        //Alert Dialog for handling responses
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setIcon(R.mipmap.logo1);
                        builder.setTitle("Request Delivery Boy");
                        builder.setMessage("The Dialog Box is for Request Delivery Boy for Sample Collection.\n\nPlease click on 'Request' to do Request for Delivery Boy");

                        builder.setPositiveButton("Request", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                getRequestForSampleCollect(user_view_accept_request_for_sample_collect_contents.get(position).getApplicant_id(),
                                        user_received_name, user_received_email, user_received_city,
                                        user_view_accept_request_for_sample_collect_contents.get(position).getLab_name(),
                                        user_view_accept_request_for_sample_collect_contents.get(position).getLab_email());
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
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private class SyncData1 extends AsyncTask<String, String, String>
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
                                if(resultSet.getString("lab_accept_order").equals("ACCEPTED") && ! resultSet.getString("applicant_first_50_percent_cod_payment_otp_status_1").equals("OTP_VERIFIED"))
                                {
                                    applicant_id_from_api = resultSet.getString("id");
                                    lab_name_from_api = resultSet.getString("lab_name");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_time_of_test_order_from_api = resultSet.getString("date_time_of_test_order");
                                    test_price_from_api = resultSet.getString("test_price");
                                    lab_accept_order_from_api = resultSet.getString("lab_accept_order");
                                    date_of_accept_from_api = resultSet.getString("date_time_of_order_accepted");
                                    lab_email_from_api = resultSet.getString("lab_email");

                                    delivery_boy_accept_from_api = resultSet.getString("delivery_boy_accept_1");

                                    user_view_accept_request_for_sample_collect_contents.add(0, new User_View_Accept_Request_For_Sample_Collect_Contents(applicant_id_from_api, lab_name_from_api, test_name_from_api, date_time_of_test_order_from_api, test_price_from_api, lab_accept_order_from_api, date_of_accept_from_api, lab_email_from_api, delivery_boy_accept_from_api));

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
                user_view_accept_request_for_sample_collect_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Request Accepted By Lab Yet. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    userRequestForSampleCollectCustomAdapter = new UserRequestForSampleCollectCustomAdapter(getActivity(), user_view_accept_request_for_sample_collect_contents);
                    user_view_accept_request_for_sample_collect_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    user_view_accept_request_for_sample_collect_list_view.setAdapter(userRequestForSampleCollectCustomAdapter);
                    user_view_accept_request_for_sample_collect_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void getRequestForSampleCollect(String request_id, String user_name, String user_email, String user_city, String lab_name, String lab_email)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);
        jsonParameters.put("user_name", user_name);
        jsonParameters.put("user_email", user_email);
        jsonParameters.put("user_city", user_city);
        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Assigning Delivery Boy");
        progressDialog.setTitle("Assigning Delivery Boy");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest user_Request_For_Sample_Collect_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_REQUEST_FOR_SAMPLE_COLLECT, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String transaction_return_message = null;

                try
                {
                    transaction_return_message = (String) response.get("transaction_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st possible response
                if (transaction_return_message.equals("Something went wrong in User Request For Sample Collect"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Error Delivery Boy is not Assigned"))
                {
                    Toast.makeText(getActivity(), "Some How Delivery Boy is Not Assigned for The Sample Collection. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("There is No Delivery Boy is Free, Please Try Again"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "There is No Delivery Boy is Available for The Sample Collection. Please Try Again after Some Time.", Toast.LENGTH_LONG).show();
                }

                // 4th possible response
                else if (transaction_return_message.equals("Delivery Boy is assigned please wait for him to accept request and User Delivery Boy Assigned Email Sent and Delivery Boy Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Delivery Boy is Assigned for The Sample Collection. Please Wait for him to Accept your delivery Request.", Toast.LENGTH_LONG).show();
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


        // User Forgot Password API CALL
        user_Request_For_Sample_Collect_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Request_For_Sample_Collect_POST_Request);
    }
}