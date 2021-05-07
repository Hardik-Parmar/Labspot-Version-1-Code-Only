package com.example.laspost10h.lab.after_login.labsupportclass;

public class Lab_Request_For_Report_Collect_Contents
{
    private String request_id, applicant_name, applicant_phone, applicant_email, test_name, date_of_request, delivery_boy_accept;

    public Lab_Request_For_Report_Collect_Contents(String request_id, String applicant_name, String applicant_phone, String applicant_email, String test_name, String date_of_request, String delivery_boy_accept)
    {
        this.request_id = request_id;
        this.applicant_name = applicant_name;
        this.applicant_phone = applicant_phone;
        this.applicant_email = applicant_email;
        this.test_name = test_name;
        this.date_of_request = date_of_request;
        this.delivery_boy_accept = delivery_boy_accept;
    }

    public void setRequest_id(String request_id)
    {
        this.request_id = request_id;
    }

    public String getRequest_id()
    {
        return request_id;
    }

    public void setApplicant_name(String applicant_name)
    {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_name()
    {
        return applicant_name;
    }

    public void setApplicant_phone(String applicant_phone)
    {
        this.applicant_phone = applicant_phone;
    }

    public String getApplicant_phone()
    {
        return applicant_phone;
    }

    public void setApplicant_email(String applicant_email)
    {
        this.applicant_email = applicant_email;
    }

    public String getApplicant_email()
    {
        return applicant_email;
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

    public void setDelivery_boy_accept(String delivery_boy_accept)
    {
        this.delivery_boy_accept = delivery_boy_accept;
    }

    public String getDelivery_boy_accept()
    {
        return delivery_boy_accept;
    }
}