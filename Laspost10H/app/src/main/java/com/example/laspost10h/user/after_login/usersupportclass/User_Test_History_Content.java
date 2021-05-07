package com.example.laspost10h.user.after_login.usersupportclass;

public class User_Test_History_Content
{
    private String request_id, lab_name, test_name, test_price, date_of_order, date_of_report_delivered;

    public User_Test_History_Content(String request_id, String lab_name, String test_name, String test_price, String date_of_order, String date_of_report_delivered)
    {
        this.request_id = request_id;
        this.lab_name = lab_name;
        this.test_name = test_name;
        this.test_price = test_price;
        this.date_of_order = date_of_order;
        this.date_of_report_delivered = date_of_report_delivered;
    }

    public void setRequest_id(String request_id)
    {
        this.request_id = request_id;
    }

    public String getRequest_id()
    {
        return request_id;
    }

    public void setLab_name(String lab_name)
    {
        this.lab_name = lab_name;
    }

    public String getLab_name()
    {
        return lab_name;
    }

    public void setTest_name(String test_name)
    {
        this.test_name = test_name;
    }

    public String getTest_name()
    {
        return test_name;
    }

    public void setTest_price(String test_price)
    {
        this.test_price = test_price;
    }

    public String getTest_price()
    {
        return test_price;
    }

    public void setDate_of_order(String date_of_order)
    {
        this.date_of_order = date_of_order;
    }

    public String getDate_of_order()
    {
        return date_of_order;
    }

    public void setDate_of_report_delivered(String date_of_report_delivered)
    {
        this.date_of_report_delivered = date_of_report_delivered;
    }

    public String getDate_of_report_delivered()
    {
        return date_of_report_delivered;
    }
}