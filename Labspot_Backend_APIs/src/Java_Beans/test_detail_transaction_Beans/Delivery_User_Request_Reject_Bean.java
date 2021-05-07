package Java_Beans.test_detail_transaction_Beans;

public class Delivery_User_Request_Reject_Bean 
{
	private String request_id, applicant_name, applicant_email, lab_name, lab_email, test_name;

    public Delivery_User_Request_Reject_Bean(String request_id, String applicant_name, 
    		String applicant_email, String lab_name, String lab_email, String test_name)
    {
        this.setRequest_id(request_id);

        this.setApplicant_name(applicant_name);
        this.setApplicant_email(applicant_email);

        this.setLab_name(lab_name);
        this.setLab_email(lab_email);

        this.setTest_name(test_name);
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
}