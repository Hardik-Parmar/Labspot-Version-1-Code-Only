package com.example.laspost10h.delivery.after_login.deliverysupportclass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.codesgood.views.JustifiedTextView;
import com.example.laspost10h.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryViewLabRequestDisplayCustomAdapter extends BaseAdapter
{
    public class ViewHolder
    {
        MaterialTextView request_from, request_id, applicant_name, lab_name;
        MaterialTextView test_name, test_price, instruction;

        JustifiedTextView applicant_address, lab_address;
    }

    public List<Delivery_View_Lab_Request_Display_Contents> final_list;

    public Activity activity_context;

    ArrayList<Delivery_View_Lab_Request_Display_Contents> arrayList;

    public DeliveryViewLabRequestDisplayCustomAdapter(Activity activity_context, List<Delivery_View_Lab_Request_Display_Contents> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<Delivery_View_Lab_Request_Display_Contents>();
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
        View labDeliveryRequestRowView = view;

        ViewHolder viewHolder = null;

        if(labDeliveryRequestRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            labDeliveryRequestRowView = layoutInflater.inflate(R.layout.lab_delivery_request_row_view, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.request_from = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_from);
            viewHolder.request_id = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_id);

            viewHolder.lab_name = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_lab_name);
            viewHolder.lab_address = (JustifiedTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_lab_address);

            viewHolder.applicant_name = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_applicant_name);
            viewHolder.applicant_address = (JustifiedTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_applicant_address);

            viewHolder.test_name = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_test_name);
            viewHolder.test_price = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_test_price);
            viewHolder.instruction = (MaterialTextView) labDeliveryRequestRowView.findViewById(R.id.lab_delivery_request_instruction);

            labDeliveryRequestRowView.setTag(viewHolder);
        }

        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.request_from.setText(final_list.get(position).getRequest_title());
        viewHolder.request_id.setText("Request I'D: - "+final_list.get(position).getRequest_id());

        viewHolder.lab_name.setText("Lab Name: - "+final_list.get(position).getLab_name());
        viewHolder.lab_address.setText("Lab Address: - "+final_list.get(position).getLab_address());

        viewHolder.applicant_name.setText("Applicant Name: - "+final_list.get(position).getApplicant_name());
        viewHolder.applicant_address.setText("Applicant Address: - "+final_list.get(position).getApplicant_address());

        viewHolder.test_name.setText("Test Name: - "+final_list.get(position).getTest_name());
        viewHolder.test_price.setText("Test Price: - "+final_list.get(position).getTest_price()+".00 ₹");
        viewHolder.instruction.setText("* You have to Collect Only "+final_list.get(position).getPrice_2()+".00 ₹ Amount From Customer.");

        return labDeliveryRequestRowView;
    }
}