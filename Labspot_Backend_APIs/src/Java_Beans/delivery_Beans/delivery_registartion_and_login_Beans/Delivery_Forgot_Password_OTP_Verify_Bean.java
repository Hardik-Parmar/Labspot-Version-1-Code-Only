package Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans;

public class Delivery_Forgot_Password_OTP_Verify_Bean 
{
	private String email, otp;
	
	public Delivery_Forgot_Password_OTP_Verify_Bean(String email, String otp)
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