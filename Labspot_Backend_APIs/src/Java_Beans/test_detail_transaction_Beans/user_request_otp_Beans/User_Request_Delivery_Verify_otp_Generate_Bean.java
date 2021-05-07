package Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans;

public class User_Request_Delivery_Verify_otp_Generate_Bean 
{
	private  String request_id, customer_name, customer_email, lab_name, test_name;

	public User_Request_Delivery_Verify_otp_Generate_Bean(String request_id, String customer_name, String customer_email, String lab_name, String test_name)
	{
		this.setRequest_id(request_id);
		this.setCustomer_name(customer_name);
		this.setCustomer_email(customer_email);
		this.setLab_name(lab_name);
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

	public void setCustomer_name(String customer_name) 
	{
		this.customer_name = customer_name;
	}

	public String getCustomer_name() 
	{
		return customer_name;
	}

	public void setCustomer_email(String customer_email) 
	{
		this.customer_email = customer_email;
	}
	
	public String getCustomer_email() 
	{
		return customer_email;
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
}