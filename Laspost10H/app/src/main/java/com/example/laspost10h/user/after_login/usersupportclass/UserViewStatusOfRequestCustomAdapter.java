package com.example.laspost10h.user.after_login.usersupportclass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.laspost10h.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class UserViewStatusOfRequestCustomAdapter extends BaseAdapter
{
    public class ViewHolder
    {
        MaterialTextView applicant_id, lab_name, test_name, test_request_date, test_price, test_request_status, date_of_rejection_title, date_of_rejection;
    }

    public List<User_View_Status_Of_Request_Contents> final_list;

    public Activity activity_context;

    ArrayList<User_View_Status_Of_Request_Contents> arrayList;

    public UserViewStatusOfRequestCustomAdapter(Activity activity_context, List<User_View_Status_Of_Request_Contents> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<User_View_Status_Of_Request_Contents>();
        arrayList.addAll(final_list);
    }


    @Override
    public int getCount()
    {
        return final_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        View testRequestStatusRowView = view;

        ViewHolder viewHolder = null;

        if(testRequestStatusRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            testRequestStatusRowView = layoutInflater.inflate(R.layout.user_view_status_of_request_row_display, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.applicant_id = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_test_id);
            viewHolder.lab_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_lab_name);
            viewHolder.test_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_test_name);
            viewHolder.test_request_date = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_request_date);
            viewHolder.test_price = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_test_price);
            viewHolder.test_request_status = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_request_status);
            viewHolder.date_of_rejection_title = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_request_action_date_title);
            viewHolder.date_of_rejection = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_view_status_of_request_request_action_date);

            testRequestStatusRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.applicant_id.setText(final_list.get(position).getApplicant_id());
        viewHolder.lab_name.setText(final_list.get(position).getLab_name());
        viewHolder.test_name.setText(final_list.get(position).getTest_name());
        viewHolder.test_request_date.setText(final_list.get(position).getDate_time_of_test_order());
        viewHolder.test_price.setText(final_list.get(position).getTest_price()+".00 ₹");
        viewHolder.test_request_status.setText(final_list.get(position).getLab_accept_order());

        if(! (final_list.get(position).getLab_accept_order().equals("PENDING")))
        {
            viewHolder.date_of_rejection_title.setVisibility(View.VISIBLE);

            viewHolder.date_of_rejection.setText(final_list.get(position).getDate_of_rejection());
            viewHolder.date_of_rejection.setVisibility(View.VISIBLE);
        }

        else
        {
            viewHolder.date_of_rejection_title.setVisibility(View.GONE);
            viewHolder.date_of_rejection.setVisibility(View.GONE);
        }

        return testRequestStatusRowView;
    }
}