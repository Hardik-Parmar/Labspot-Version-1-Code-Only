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
import com.example.laspost10h.lab.after_login.labsupportclass.LabViewEditTestDetailsCustomAdapter;
import com.example.laspost10h.lab.after_login.labsupportclass.Lab_Test_Detailed_Display_Content;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Lab_View_Edit_Test_Details extends Fragment
{
    View view;

    private ListView lab_test_display_list_view;

    SwipeRefreshLayout lab_test_display_refresh;

    private ArrayList<Lab_Test_Detailed_Display_Content> lab_test_detailed_display_contents;

    LabViewEditTestDetailsCustomAdapter labViewEditTestDetailsCustomAdapter;

    private boolean success = false;

    String lab_received_email, lab_received_name;

    String lab_test_id_from_api, lab_name_from_api, lab_mail_from_api, test_name_from_api, test_description_from_api, test_price_from_api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_lab_view_edit_test_details, container, false);

        Bundle bundle = getArguments();
        lab_received_email = bundle.getString("lab_sent_mail");
        lab_received_name = bundle.getString("lab_sent_name");

        // List View
        lab_test_display_list_view = (ListView) view.findViewById(R.id.lab_test_display_list_view);

        // Swipe Refresh
        lab_test_display_refresh = (SwipeRefreshLayout) view.findViewById(R.id.lab_test_display_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        lab_test_display_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_sent_name", lab_received_name);
                bundle1.putString("lab_sent_mail", lab_received_email);

                Lab_View_Edit_Test_Details lab_view_edit_test_details = new Lab_View_Edit_Test_Details();
                lab_view_edit_test_details.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_view_edit_test_details).detach(lab_view_edit_test_details).attach(lab_view_edit_test_details).commit();
            }
        });

        lab_test_detailed_display_contents = new ArrayList<Lab_Test_Detailed_Display_Content>();

        if(Utility.isNetworkAvailable(getActivity()))
        {
            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        lab_test_display_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("lab_test_sent_id", lab_test_detailed_display_contents.get(position).getId());
                bundle1.putString("lab_test_sent_mail", lab_test_detailed_display_contents.get(position).getLab_email());
                bundle1.putString("lab_test_sent_name", lab_test_detailed_display_contents.get(position).getLab_test_name());
                bundle1.putString("lab_test_sent_description", lab_test_detailed_display_contents.get(position).getLab_test_description());
                bundle1.putString("lab_test_sent_price", lab_test_detailed_display_contents.get(position).getLab_test_price());

                Lab_Edit_Test_Detail lab_edit_test_detail = new Lab_Edit_Test_Detail();
                lab_edit_test_detail.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.lab_fragment_container, lab_edit_test_detail).addToBackStack(null).commit();
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
            progressDialog.setMessage("Please Wait Fetching Test Details from the Server");
            progressDialog.setTitle("Fetching Test Details");
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
                    String query = "SELECT * FROM test_details where lab_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, lab_received_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                lab_test_id_from_api = resultSet.getString("id");
                                lab_name_from_api = resultSet.getString("lab_name");
                                lab_mail_from_api = resultSet.getString("lab_email");
                                test_name_from_api = resultSet.getString("lab_test_name");
                                test_description_from_api = resultSet.getString("lab_test_description");
                                test_price_from_api = resultSet.getString("lab_test_price");

                                lab_test_detailed_display_contents.add(new Lab_Test_Detailed_Display_Content(lab_test_id_from_api, lab_name_from_api, lab_mail_from_api, test_name_from_api, test_description_from_api, test_price_from_api));

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
                lab_test_display_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Test has been Added By you. Please Try Again After Adding Test.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    labViewEditTestDetailsCustomAdapter = new LabViewEditTestDetailsCustomAdapter(getActivity(), lab_test_detailed_display_contents);
                    lab_test_display_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lab_test_display_list_view.setAdapter(labViewEditTestDetailsCustomAdapter);
                    lab_test_display_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}