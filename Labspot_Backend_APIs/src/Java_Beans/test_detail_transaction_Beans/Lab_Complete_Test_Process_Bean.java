package Java_Beans.test_detail_transaction_Beans;

public class Lab_Complete_Test_Process_Bean 
{
	private String id, applicant_name, applicant_email, test_name, lab_name, lab_email;

	public Lab_Complete_Test_Process_Bean(String id, String applicant_name, String applicant_email, String test_name, String lab_name, String lab_email)
	{
		this.setId(id);
		this.setApplicant_name(applicant_name);
		this.setApplicant_email(applicant_email);
		this.setTest_name(test_name);
		this.setLab_name(lab_name);
		this.setLab_email(lab_email);
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
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

	public void setTest_name(String test_name) 
	{
		this.test_name = test_name;
	}

	public String getTest_name() 
	{
		return test_name;
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
}