package Java_DAO.user_DAO.user_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Reset_Password_Bean;
import database_connection.ConnectionProvider;

public class User_Reset_Password_DAO 
{
	public String userResetPassword(User_Reset_Password_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM user_register where user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				String user_forgot_otp_status = rs.getString("user_forgot_otp_status");
				
				if(user_forgot_otp_status.equals("TRUE"))
				{
					String query1 = "UPDATE user_register SET user_password = ?, user_forgot_otp_status = ?, user_forgot_otp = ? WHERE user_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, "Flase");
					ps1.setString(3, "null");
					ps1.setString(4, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "User Password has been Reset";
					}
					else
					{
						return "User Password has not been Reset";
					}
				}
				else
				{
					return "User Please verify OTP for the reset Password";
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
		
		return "Something went wrong in User Password Reset";
	}
}