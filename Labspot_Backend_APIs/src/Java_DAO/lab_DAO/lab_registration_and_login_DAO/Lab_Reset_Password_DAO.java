package Java_DAO.lab_DAO.lab_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Reset_Password_Bean;
import database_connection.ConnectionProvider;

public class Lab_Reset_Password_DAO 
{
	public String labResetPassword(Lab_Reset_Password_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM lab_register where lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				String lab_forgot_otp_status = rs.getString("lab_forgot_otp_status");
				
				if(lab_forgot_otp_status.equals("TRUE"))
				{
					String query1 = "UPDATE lab_register SET lab_password = ?, lab_forgot_otp_status = ?, lab_forgot_otp = ? WHERE lab_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, "Flase");
					ps1.setString(3, "null");
					ps1.setString(4, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Lab Password has been Reset";
					}
					else
					{
						return "Lab Password has not been Reset";
					}
				}
				else
				{
					return "Lab Please verify OTP for the reset Password";
				}
			}
			else
			{
				return "You Does Not Exist as Lab please register yourself first";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Password Reset";
	}
}