package com.example.laspost10h.lab.after_login.labsupportclass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.example.laspost10h.R;

import java.util.ArrayList;
import java.util.List;

public class LabViewEditTestDetailsCustomAdapter extends BaseAdapter
{
    public class ViewHolder
    {
        TextView test_name, test_price;
        JustifiedTextView test_description;
    }

    public List<Lab_Test_Detailed_Display_Content> final_list;

    public Activity activity_context;

    ArrayList<Lab_Test_Detailed_Display_Content> arrayList;

    public LabViewEditTestDetailsCustomAdapter(Activity activity_context, List<Lab_Test_Detailed_Display_Content> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<Lab_Test_Detailed_Display_Content>();
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
        View testRowView = view;

        ViewHolder viewHolder = null;

        if(testRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            testRowView = layoutInflater.inflate(R.layout.lab_view_or_edit_test_detail_row_display, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.test_name = (TextView) testRowView.findViewById(R.id.lab_test_detail_test_name);
            viewHolder.test_description  = (JustifiedTextView) testRowView.findViewById(R.id.lab_test_detail_test_description);
            viewHolder.test_price = (TextView) testRowView.findViewById(R.id.lab_test_detail_test_price);

            testRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.test_name.setText("Test Name: - "+final_list.get(position).getLab_test_name());
        viewHolder.test_description.setText("Test Description: - "+final_list.get(position).getLab_test_description());
        viewHolder.test_price.setText("Test Price: - "+final_list.get(position).getLab_test_price()+".00 ₹");

        return testRowView;
    }
}