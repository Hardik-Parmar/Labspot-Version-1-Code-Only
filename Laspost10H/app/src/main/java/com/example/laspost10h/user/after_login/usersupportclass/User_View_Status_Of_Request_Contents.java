package com.example.laspost10h.user.after_login.usersupportclass;

public class User_View_Status_Of_Request_Contents
{
    private String applicant_id, lab_name, test_name, date_time_of_test_order, test_price, lab_accept_order, date_of_rejection;

    public User_View_Status_Of_Request_Contents(String applicant_id, String lab_name, String test_name, String date_time_of_test_order, String test_price, String lab_accept_order, String date_of_rejection)
    {
        this.setApplicant_id(applicant_id);
        this.setLab_name(lab_name);
        this.setTest_name(test_name);
        this.setDate_time_of_test_order(date_time_of_test_order);
        this.setTest_price(test_price);
        this.setLab_accept_order(lab_accept_order);
        this.setDate_of_rejection(date_of_rejection);
    }

    public void setApplicant_id(String applicant_id)
    {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_id()
    {
        return applicant_id;
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

    public void setDate_time_of_test_order(String date_time_of_test_order)
    {
        this.date_time_of_test_order = date_time_of_test_order;
    }

    public String getDate_time_of_test_order()
    {
        return date_time_of_test_order;
    }

    public void setTest_price(String test_price)
    {
        this.test_price = test_price;
    }

    public String getTest_price()
    {
        return test_price;
    }

    public void setLab_accept_order(String lab_accept_order)
    {
        this.lab_accept_order = lab_accept_order;
    }

    public String getLab_accept_order()
    {
        return lab_accept_order;
    }

    public void setDate_of_rejection(String date_of_rejection)
    {
        this.date_of_rejection = date_of_rejection;
    }

    public String getDate_of_rejection()
    {
        return date_of_rejection;
    }
}