package com.example.laspost10h.lab.after_login.labsupportclass;

public class Lab_New_Test_Request_Contents
{
    private String applicant_id, user_name, user_phone, user_email_temp, test_name, date_of_request;

    public Lab_New_Test_Request_Contents(String applicant_id, String user_name, String user_phone, String user_email_temp, String test_name, String date_of_request)
    {
        this.applicant_id = applicant_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_email_temp = user_email_temp;
        this.test_name = test_name;
        this.date_of_request = date_of_request;
    }

    public void setApplicant_id(String applicant_id)
    {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_id()
    {
        return applicant_id;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_phone(String user_phone)
    {
        this.user_phone = user_phone;
    }

    public String getUser_phone()
    {
        return user_phone;
    }

    public void setUser_email_temp(String user_email_temp)
    {
        this.user_email_temp = user_email_temp;
    }

    public String getUser_email_temp()
    {
        return user_email_temp;
    }

    public void setTest_name(String test_name)
    {
        this.test_name = test_name;
    }

    public String getTest_name()
    {
        return test_name;
    }

    public void setDate_of_request(String date_of_request)
    {
        this.date_of_request = date_of_request;
    }

    public String getDate_of_request()
    {
        return date_of_request;
    }
}