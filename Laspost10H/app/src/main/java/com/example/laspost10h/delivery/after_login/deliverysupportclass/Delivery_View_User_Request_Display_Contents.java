package com.example.laspost10h.delivery.after_login.deliverysupportclass;

public class Delivery_View_User_Request_Display_Contents
{
    private String request_id, applicant_name, applicant_address, applicant_phone, applicant_email;
    private String lab_name, lab_phone, lab_address, lab_email;
    private String test_name, test_price, price_1, price_2, request_title;

    public Delivery_View_User_Request_Display_Contents(String request_id, String applicant_name, String applicant_address, String applicant_phone,
                                                       String applicant_email, String lab_name, String lab_address, String lab_phone,
                                                       String lab_email, String test_name, String test_price, String price_1,
                                                       String price_2, String request_title)
    {
        this.setRequest_id(request_id);

        this.setApplicant_name(applicant_name);
        this.setApplicant_address(applicant_address);
        this.setApplicant_phone(applicant_phone);
        this.setApplicant_email(applicant_email);

        this.setLab_name(lab_name);
        this.setLab_address(lab_address);
        this.setLab_phone(lab_phone);
        this.setLab_email(lab_email);

        this.setTest_name(test_name);
        this.setTest_price(test_price);
        this.setPrice_1(price_1);
        this.setPrice_2(price_2);
        this.setRequest_title(request_title);
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

    public void setApplicant_address(String applicant_address)
    {
        this.applicant_address = applicant_address;
    }

    public String getApplicant_address()
    {
        return applicant_address;
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

    public void setLab_name(String lab_name)
    {
        this.lab_name = lab_name;
    }

    public String getLab_name()
    {
        return lab_name;
    }

    public void setLab_phone(String lab_phone)
    {
        this.lab_phone = lab_phone;
    }

    public String getLab_phone()
    {
        return lab_phone;
    }

    public void setLab_address(String lab_address)
    {
        this.lab_address = lab_address;
    }

    public String getLab_address()
    {
        return lab_address;
    }

    public void setLab_email(String lab_email)
    {
        this.lab_email = lab_email;
    }

    public String getLab_email()
    {
        return lab_email;
    }

    public void setTest_name(String test_name)
    {
        this.test_name = test_name;
    }

    public String getTest_name()
    {
        return test_name;
    }

    public void setPrice_1(String price_1)
    {
        this.price_1 = price_1;
    }

    public String getPrice_1()
    {
        return price_1;
    }

    public void setPrice_2(String price_2)
    {
        this.price_2 = price_2;
    }

    public String getPrice_2()
    {
        return price_2;
    }

    public void setRequest_title(String request_title)
    {
        this.request_title = request_title;
    }

    public String getRequest_title()
    {
        return request_title;
    }

    public void setTest_price(String test_price)
    {
        this.test_price = test_price;
    }

    public String getTest_price()
    {
        return test_price;
    }
}