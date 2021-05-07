package com.example.laspost10h.delivery.after_login;

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
import com.example.laspost10h.delivery.after_login.deliverysupportclass.DeliveryViewLabRequestDisplayCustomAdapter;
import com.example.laspost10h.delivery.after_login.deliverysupportclass.Delivery_View_Lab_Request_Display_Contents;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Delivery_View_Lab_Request extends Fragment
{
    View view;

    String delivery_received_name, delivery_received_phone, delivery_received_email, delivery_received_address;

    String request_id_from_api, applicant_name_from_api, applicant_phone_from_api, applicant_email_from_api,  applicant_address_from_api;

    String lab_name_from_api, lab_phone_from_api, lab_email_from_api, lab_address_from_api;

    String test_name_from_api, test_price_from_api, price_1_from_api, price_2_from_api;

    String request_title;

    private boolean success = false;

    private ArrayList<Delivery_View_Lab_Request_Display_Contents> delivery_view_lab_request_display_contents;

    ListView lab_request_for_report_collect_list_view;

    private DeliveryViewLabRequestDisplayCustomAdapter deliveryViewLabRequestDisplayCustomAdapter;

    SwipeRefreshLayout delivery_lab_view_request_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_view_lab_request, container, false);

        Bundle bundle = getArguments();
        delivery_received_name = bundle.getString("delivery_sent_name");
        delivery_received_phone = bundle.getString("delivery_sent_phone");
        delivery_received_email = bundle.getString("delivery_sent_mail");
        delivery_received_address = bundle.getString("delivery_sent_address");

        delivery_view_lab_request_display_contents = new ArrayList<Delivery_View_Lab_Request_Display_Contents>();

        //Swipe Refresh
        delivery_lab_view_request_refresh = (SwipeRefreshLayout) view.findViewById(R.id.delivery_lab_view_request_refresh);

        //ListView
        lab_request_for_report_collect_list_view = (ListView) view.findViewById(R.id.lab_request_for_report_collect_list_view);

        delivery_lab_view_request_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("delivery_sent_name", delivery_received_name);
                bundle1.putString("delivery_sent_phone", delivery_received_phone);
                bundle1.putString("delivery_sent_mail", delivery_received_email);
                bundle1.putString("delivery_sent_address", delivery_received_address);

                Delivery_View_Lab_Request delivery_view_lab_request = new Delivery_View_Lab_Request();
                delivery_view_lab_request.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_view_lab_request).detach(delivery_view_lab_request).attach(delivery_view_lab_request).commit();

            }
        });

        if(Utility.isNetworkAvailable(getActivity()))
        {
            //Toast.makeText(getActivity(), delivery_received_address, Toast.LENGTH_SHORT).show();
            SyncData orderData = new SyncData();
            orderData.execute("");
        }

        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        lab_request_for_report_collect_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("request_id", delivery_view_lab_request_display_contents.get(position).getRequest_id());

                bundle1.putString("applicant_name", delivery_view_lab_request_display_contents.get(position).getApplicant_name());
                bundle1.putString("applicant_address", delivery_view_lab_request_display_contents.get(position).getApplicant_address());
                bundle1.putString("applicant_phone", delivery_view_lab_request_display_contents.get(position).getApplicant_phone());
                bundle1.putString("applicant_mail", delivery_view_lab_request_display_contents.get(position).getApplicant_email());

                bundle1.putString("lab_name", delivery_view_lab_request_display_contents.get(position).getLab_name());
                bundle1.putString("lab_address", delivery_view_lab_request_display_contents.get(position).getLab_address());
                bundle1.putString("lab_phone", delivery_view_lab_request_display_contents.get(position).getLab_phone());
                bundle1.putString("lab_mail", delivery_view_lab_request_display_contents.get(position).getLab_email());

                bundle1.putString("test_name", delivery_view_lab_request_display_contents.get(position).getTest_name());
                bundle1.putString("test_price", delivery_view_lab_request_display_contents.get(position).getTest_price());
                bundle1.putString("50_price", delivery_view_lab_request_display_contents.get(position).getPrice_2());

                Delivery_Lab_Request_Take_Action delivery_lab_request_take_action = new Delivery_Lab_Request_Take_Action();
                delivery_lab_request_take_action.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_lab_request_take_action).addToBackStack(null).commit();
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
            progressDialog.setMessage("Please Wait Fetching Delivery Request from Lab Side to Collect Report");
            progressDialog.setTitle("Fetching Delivery Request from Lab Side");
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
                    String query = "SELECT * FROM test_transaction_details WHERE delivery_boy_name_2 = ? AND delivery_boy_phone_2 = ? AND delivery_boy_address_2 = ? AND delivery_boy_email_2 = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, delivery_received_name);
                    preparedStatement.setString(2, delivery_received_phone);
                    preparedStatement.setString(3, delivery_received_address);
                    preparedStatement.setString(4, delivery_received_email);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                if(resultSet.getString("delivery_boy_accept_2").equals("PENDING"))
                                {
                                    request_id_from_api = resultSet.getString("id");

                                    applicant_name_from_api = resultSet.getString("applicant_name");
                                    applicant_phone_from_api = resultSet.getString("applicant_phone");
                                    applicant_email_from_api = resultSet.getString("applicant_email");
                                    applicant_address_from_api = resultSet.getString("applicant_address");

                                    lab_name_from_api = resultSet.getString("lab_name");
                                    lab_phone_from_api = resultSet.getString("lab_phone");
                                    lab_email_from_api = resultSet.getString("lab_email");
                                    lab_address_from_api = resultSet.getString("lab_address");

                                    test_name_from_api = resultSet.getString("test_name");
                                    test_price_from_api = resultSet.getString("test_price");
                                    price_1_from_api = resultSet.getString("first_50_percent_price");
                                    price_2_from_api = resultSet.getString("second_50_percent_price");

                                    request_title = "Request From Laboratory to Collect the Report";

                                    delivery_view_lab_request_display_contents.add(0, new Delivery_View_Lab_Request_Display_Contents(request_id_from_api,
                                            applicant_name_from_api, applicant_address_from_api, applicant_phone_from_api, applicant_email_from_api,
                                            lab_name_from_api, lab_address_from_api,lab_phone_from_api, lab_email_from_api,
                                            test_name_from_api, test_price_from_api, price_1_from_api, price_2_from_api, request_title));

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
                lab_request_for_report_collect_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Request made by Lab. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    deliveryViewLabRequestDisplayCustomAdapter = new DeliveryViewLabRequestDisplayCustomAdapter(getActivity(), delivery_view_lab_request_display_contents);
                    lab_request_for_report_collect_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_request_for_report_collect_list_view.setAdapter(deliveryViewLabRequestDisplayCustomAdapter);
                    lab_request_for_report_collect_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}