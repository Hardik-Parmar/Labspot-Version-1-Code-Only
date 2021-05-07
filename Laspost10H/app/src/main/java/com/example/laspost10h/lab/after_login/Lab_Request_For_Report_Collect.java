package com.example.laspost10h.lab.after_login;

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
import com.example.laspost10h.lab.after_login.labsupportclass.LabRequestForReportCollectCustomAdapter;
import com.example.laspost10h.lab.after_login.labsupportclass.Lab_Request_For_Report_Collect_Contents;

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

public class Lab_Request_For_Report_Collect extends Fragment
{
    View  view;

    ListView lab_view_accept_request_for_report_collect_list_view;

    private boolean success = false;

    String lab_received_name, lab_received_email, lab_received_city;

    String request_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api;
    String test_name_from_api, date_of_request_from_api;
    String delivery_boy_accept_from_api;

    private ArrayList<Lab_Request_For_Report_Collect_Contents> lab_request_for_report_collect_contents;

    LabRequestForReportCollectCustomAdapter labRequestForReportCollectCustomAdapter;

    SwipeRefreshLayout lab_request_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_request_for_report_collect, container, false);

        // ListView
        lab_view_accept_request_for_report_collect_list_view = (ListView) view.findViewById(R.id.lab_view_accept_request_for_report_collect_list_view);

        lab_request_for_report_collect_contents = new ArrayList<Lab_Request_For_Report_Collect_Contents>();

        // Swipe Refresh
        lab_request_refresh = (SwipeRefreshLayout) view.findViewById(R.id.lab_request_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        lab_request_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_sent_mail", lab_received_email);
                bundle1.putString("lab_sent_name", lab_received_name);
                bundle1.putString("lab_sent_city", lab_received_city);

                Lab_Request_For_Report_Collect lab_request_for_report_collect = new Lab_Request_For_Report_Collect();
                lab_request_for_report_collect.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_request_for_report_collect).detach(lab_request_for_report_collect).attach(lab_request_for_report_collect).commit();
            }
        });

        Bundle bundle = getArguments();
        lab_received_email = bundle.getString("lab_sent_mail");
        lab_received_name = bundle.getString("lab_sent_name");
        lab_received_city = bundle.getString("lab_sent_city");

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        lab_view_accept_request_for_report_collect_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    if(lab_request_for_report_collect_contents.get(position).getDelivery_boy_accept().equals("ACCEPTED"))
                    {
                        Toast.makeText(getActivity(), "Please Wait for Delivery Boy to Collect Report from you.", Toast.LENGTH_SHORT).show();
                    }
                    else if(lab_request_for_report_collect_contents.get(position).getDelivery_boy_accept().equals("PENDING"))
                    {
                        Toast.makeText(getActivity(), "Please Wait for Delivery Boy to take action on your Request.", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        //Alert Dialog for handling responses
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setIcon(R.mipmap.logo1);
                        builder.setTitle("Request Delivery Boy");
                        builder.setMessage("The Dialog Box is for Request Delivery Boy for Report Collection.\n\nPlease click on 'Request' to do Request for Delivery Boy");

                        builder.setPositiveButton("Request", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                getRequestForReportCollect(lab_request_for_report_collect_contents.get(position).getRequest_id(),
                                        lab_received_name, lab_received_email, lab_received_city,
                                        lab_request_for_report_collect_contents.get(position).getApplicant_name(),
                                        lab_request_for_report_collect_contents.get(position).getApplicant_email());
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

    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg;

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() //Starts the progress dialog
        {
            progressDialog.setMessage("Please Wait Fetching Your Completed Test Request Data from the Server");
            progressDialog.setTitle("Fetching Completed Test Request Data");
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
                                if(resultSet.getString("test_order_complete_by_lab").equals("COMPLETED") && ! resultSet.getString("applicant_second_50_percent_cod_payment_otp_status_2").equals("OTP_VERIFIED"))
                                {
                                    request_id_from_api = resultSet.getString("id");
                                    user_name_from_api = resultSet.getString("applicant_name");
                                    user_phone_from_api = resultSet.getString("applicant_phone");
                                    user_email_from_api = resultSet.getString("applicant_email");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_of_request_from_api = resultSet.getString("date_time_of_test_order");
                                    delivery_boy_accept_from_api = resultSet.getString("delivery_boy_accept_2");

                                    lab_request_for_report_collect_contents.add(0, new Lab_Request_For_Report_Collect_Contents(request_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api, test_name_from_api, date_of_request_from_api, delivery_boy_accept_from_api));

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
                lab_view_accept_request_for_report_collect_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Completed Request Yet. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    labRequestForReportCollectCustomAdapter = new LabRequestForReportCollectCustomAdapter(getActivity(), lab_request_for_report_collect_contents);
                    lab_view_accept_request_for_report_collect_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_view_accept_request_for_report_collect_list_view.setAdapter(labRequestForReportCollectCustomAdapter);
                    lab_view_accept_request_for_report_collect_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void getRequestForReportCollect(String request_id, String lab_name, String lab_email, String lab_city, String user_name, String user_email)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);
        jsonParameters.put("lab_city", lab_city);

        jsonParameters.put("customer_name", user_name);
        jsonParameters.put("customer_email", user_email);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Assigning Delivery Boy");
        progressDialog.setTitle("Assigning Delivery Boy");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Request_For_Report_Collect_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_REQUEST_DELIVERY_BOY, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Lab Request For Report Collect"))
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
                else if (transaction_return_message.equals("Delivery Boy is assigned please wait for him to accept request and Lab Delivery Boy Assigned Email Sent and Delivery Boy Email Sent"))
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
        lab_Request_For_Report_Collect_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Request_For_Report_Collect_POST_Request);
    }
}