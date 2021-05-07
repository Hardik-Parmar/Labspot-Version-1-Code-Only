package Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans;

public class Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean 
{
	private String request_id, customer_name, lab_name, lab_email, test_name, otp_from_delivery, date;

	public Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean(String request_id, String customer_name, String lab_name, String lab_email, String test_name, String otp_from_delivery, String date)
	{
		this.setRequest_id(request_id);
		this.setCustomer_name(customer_name);
		this.setLab_name(lab_name);
		this.setLab_email(lab_email);
		this.setTest_name(test_name);
		this.setOtp_from_delivery(otp_from_delivery);
		this.setDate(date);
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
	
	public void setOtp_from_delivery(String otp_from_delivery) 
	{
		this.otp_from_delivery = otp_from_delivery;
	}
	
	public String getOtp_from_delivery() 
	{
		return otp_from_delivery;
	}
	
	public void setDate(String date) 
	{
		this.date = date;
	}
	
	public String getDate() 
	{
		return date;
	}
}