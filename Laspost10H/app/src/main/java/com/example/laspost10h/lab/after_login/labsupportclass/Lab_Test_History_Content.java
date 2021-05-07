package com.example.laspost10h.lab.after_login.labsupportclass;

public class Lab_Test_History_Content
{
    private String request_id, applicant_name, test_name, date_of_order, date_of_report_dispatched, date_of_report_delivered;

    public Lab_Test_History_Content(String request_id, String applicant_name, String test_name, String date_of_order, String date_of_report_dispatched, String date_of_report_delivered)
    {
        this.request_id = request_id;
        this.applicant_name = applicant_name;
        this.test_name = test_name;
        this.date_of_order = date_of_order;
        this.date_of_report_dispatched = date_of_report_dispatched;
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

    public void setApplicant_name(String applicant_name)
    {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_name()
    {
        return applicant_name;
    }

    public void setTest_name(String test_name)
    {
        this.test_name = test_name;
    }

    public String getTest_name()
    {
        return test_name;
    }

    public void setDate_of_order(String date_of_order)
    {
        this.date_of_order = date_of_order;
    }

    public String getDate_of_order()
    {
        return date_of_order;
    }

    public void setDate_of_report_dispatched(String date_of_report_dispatched)
    {
        this.date_of_report_dispatched = date_of_report_dispatched;
    }

    public String getDate_of_report_dispatched()
    {
        return date_of_report_dispatched;
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