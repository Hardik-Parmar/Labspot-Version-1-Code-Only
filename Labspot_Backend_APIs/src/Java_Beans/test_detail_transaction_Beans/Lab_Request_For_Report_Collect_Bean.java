package Java_Beans.test_detail_transaction_Beans;

public class Lab_Request_For_Report_Collect_Bean 
{
	private String request_id, lab_name, lab_email, lab_city, user_name, user_email;

	public Lab_Request_For_Report_Collect_Bean(String request_id, String lab_name, String lab_email, String lab_city, String user_name, String user_email)
	{
		this.setRequest_id(request_id);
		this.setLab_name(lab_name);
		this.setLab_email(lab_email);
		this.setLab_city(lab_city);
		this.setUser_name(user_name);
		this.setUser_email(user_email);
		
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

	public void setLab_email(String lab_email) 
	{
		this.lab_email = lab_email;
	}

	public String getLab_email() 
	{
		return lab_email;
	} 
	
	public void setLab_city(String lab_city) 
	{
		this.lab_city = lab_city;
	}

	public String getLab_city() 
	{
		return lab_city;
	}
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	
	public String getUser_name() 
	{
		return user_name;
	}

	public void setUser_email(String user_email) 
	{
		this.user_email = user_email;
	}

	public String getUser_email() 
	{
		return user_email;
	}
}