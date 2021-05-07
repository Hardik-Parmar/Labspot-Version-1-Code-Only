package Java_DAO.user_DAO.user_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Account_Verify_Bean;
import database_connection.ConnectionProvider;

public class User_Account_Verify_DAO 
{
	public String userAccountVerify(User_Account_Verify_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from user_register WHERE user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String otp = resultSet.getString("user_otp");
				
				if(otp.equals(bean.getOtp()))
				{
					String query1 = "UPDATE user_register set user_status = 'active', user_otp = ? where user_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, "null");
					ps1.setString(2, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "User Account Verified";
					}
					else
					{
						return "Something went wrong in User Account Verification";
					}
				}
				else
				{
						return "User Account Not Verified Wrong OTP";
				}
			}
			else
			{
				return "You Does Not Exist as General User please register yourself first";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in User Account Verification";
	}
}