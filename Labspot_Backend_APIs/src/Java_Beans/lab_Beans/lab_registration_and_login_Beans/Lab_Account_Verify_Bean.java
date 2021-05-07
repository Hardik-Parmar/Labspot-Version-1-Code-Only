package Java_Beans.lab_Beans.lab_registration_and_login_Beans;

public class Lab_Account_Verify_Bean 
{
private String email, otp;
	
	public Lab_Account_Verify_Bean(String email, String otp)
	{
		this.setEmail(email);
		this.setOtp(otp);
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getEmail() 
	{
		return email;
	}

	public void setOtp(String otp) 
	{
		this.otp = otp;
	}
	
	public String getOtp() 
	{
		return otp;
	}
}