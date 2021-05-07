package com.example.laspost10h.user.after_login.usersupportclass;

public class User_View_Accept_Request_For_Sample_Collect_Contents
{
    private String applicant_id, lab_name, test_name, date_time_of_test_order, test_price, lab_accept_order, date_of_accept, lab_email, delivery_boy_accept;

    public User_View_Accept_Request_For_Sample_Collect_Contents(String applicant_id, String lab_name, String test_name, String date_time_of_test_order, String test_price, String lab_accept_order, String date_of_accept, String lab_email, String delivery_boy_accept)
    {
        this.setApplicant_id(applicant_id);
        this.setLab_name(lab_name);
        this.setTest_name(test_name);
        this.setDate_time_of_test_order(date_time_of_test_order);
        this.setTest_price(test_price);
        this.setLab_accept_order(lab_accept_order);
        this.setDate_of_accept(date_of_accept);
        this.setLab_email(lab_email);
        this.setDelivery_boy_accept(delivery_boy_accept);
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

    public void setDate_of_accept(String date_of_accept)
    {
        this.date_of_accept = date_of_accept;
    }

    public String getDate_of_accept()
    {
        return date_of_accept;
    }

    public void setLab_email(String lab_email)
    {
        this.lab_email = lab_email;
    }

    public String getLab_email()
    {
        return lab_email;
    }

    public void setDelivery_boy_accept(String delivery_boy_accept)
    {
        this.delivery_boy_accept = delivery_boy_accept;
    }

    public String getDelivery_boy_accept()
    {
        return delivery_boy_accept;
    }
}