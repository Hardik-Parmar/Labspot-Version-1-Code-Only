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
import com.example.laspost10h.lab.after_login.labsupportclass.LabViewPendingOrderCustomAdapter;
import com.example.laspost10h.lab.after_login.labsupportclass.Lab_View_Pending_Order_Contents;

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

public class Lab_View_Pending_Order extends Fragment
{
    View view;

    String lab_received_name, lab_received_email;

    ListView lab_view_pending_order_list_view;

    private boolean success = false;

    private ArrayList<Lab_View_Pending_Order_Contents> lab_view_pending_order_contents;

    String request_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api;
    String test_name_from_api, date_of_request_from_api;

    LabViewPendingOrderCustomAdapter labViewPendingOrderCustomAdapter;

    SwipeRefreshLayout lab_pending_request_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_view_pending_order, container, false);

        Bundle bundle = getArguments();
        lab_received_name = bundle.getString("lab_sent_name");
        lab_received_email = bundle.getString("lab_sent_mail");

        lab_view_pending_order_contents = new ArrayList<Lab_View_Pending_Order_Contents>();

        //ListView
        lab_view_pending_order_list_view = (ListView) view.findViewById(R.id.lab_view_pending_order_list_view);

        //Swipe Refresh Layout
        lab_pending_request_refresh = (SwipeRefreshLayout) view.findViewById(R.id.lab_pending_request_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        lab_pending_request_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_sent_mail", lab_received_email);
                bundle1.putString("lab_sent_name", lab_received_name);

                Lab_View_Pending_Order lab_view_pending_order = new Lab_View_Pending_Order();
                lab_view_pending_order.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_view_pending_order).detach(lab_view_pending_order).attach(lab_view_pending_order).commit();
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

        lab_view_pending_order_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    //Alert Dialog for handling responses
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Take Action on Pending Test Order");
                    builder.setMessage("The Dialog Box is for Take Action on Pending Test Order.\n\nPlease click on 'Completed' to Complete Current Test Process");

                    builder.setPositiveButton("Completed", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            getCompleteTestProcess(lab_view_pending_order_contents.get(position).getApplicant_id(),
                                    lab_view_pending_order_contents.get(position).getUser_name(),
                                    lab_view_pending_order_contents.get(position).getUser_email_temp(),
                                    lab_view_pending_order_contents.get(position).getTest_name(), lab_received_name, lab_received_email);
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
            progressDialog.setMessage("Please Wait Fetching Pending Test Requests from the Server");
            progressDialog.setTitle("Fetching Pending Test Requests");
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
                                if(resultSet.getString("lab_accept_order").equals("ACCEPTED") && !resultSet.getString("test_order_complete_by_lab").equals("COMPLETED"))
                                {
                                    request_id_from_api = resultSet.getString("id");
                                    user_name_from_api = resultSet.getString("applicant_name");
                                    user_phone_from_api = resultSet.getString("applicant_phone");
                                    user_email_from_api = resultSet.getString("applicant_email");
                                    test_name_from_api = resultSet.getString("test_name");
                                    date_of_request_from_api = resultSet.getString("date_time_of_test_order");

                                    lab_view_pending_order_contents.add(0, new Lab_View_Pending_Order_Contents(request_id_from_api, user_name_from_api, user_phone_from_api, user_email_from_api, test_name_from_api, date_of_request_from_api));

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
                lab_view_pending_order_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Incomplete Test Request. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    labViewPendingOrderCustomAdapter = new LabViewPendingOrderCustomAdapter(getActivity(), lab_view_pending_order_contents);
                    lab_view_pending_order_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_view_pending_order_list_view.setAdapter(labViewPendingOrderCustomAdapter);
                    lab_view_pending_order_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void getCompleteTestProcess(String request_id,  String applicant_name, String applicant_email, String test_name, String lab_name, String lab_email)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("customer_name", applicant_name);
        jsonParameters.put("customer_email", applicant_email);

        jsonParameters.put("test_name", test_name);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Completing Test Process");
        progressDialog.setTitle("Complete Test Process");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Complete_Test_Process_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_COMPLETE_TEST_PROCESS, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Lab Complete Test Process"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Test Request is not Completed"))
                {
                    Toast.makeText(getActivity(), "Some How Test Process is not Set to Completed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Test Request Completed and Complete Test Process Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Current Test Process is Set to Completed.", Toast.LENGTH_LONG).show();
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
        lab_Complete_Test_Process_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Complete_Test_Process_POST_Request);
    }
}