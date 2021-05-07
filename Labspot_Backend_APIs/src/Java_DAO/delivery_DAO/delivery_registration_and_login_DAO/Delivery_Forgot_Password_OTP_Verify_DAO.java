package Java_DAO.delivery_DAO.delivery_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Forgot_Password_OTP_Verify_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Forgot_Password_OTP_Verify_DAO 
{
	public String deliveryForgotOTPVerify(Delivery_Forgot_Password_OTP_Verify_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String otp = resultSet.getString("delivery_forgot_otp");
				
				if(otp.equals(bean.getOtp()))
				{
					String query1 = "UPDATE delivery_register set delivery_forgot_otp_status = 'TRUE' where delivery_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Delivery Forgot Password OTP Verified";
					}
					else
					{
						return "Something went wrong in Delivery Forgot Password OTP Verification";
					}
				}
				else
				{
						return "Delivery Forgot Password OTP Not Verified Wrong OTP";
				}
			}
			else
			{
				return "You Does Not Exist as Delivery Staff please register yourself first";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Delivery Forgot Password OTP Verification";
	}
}