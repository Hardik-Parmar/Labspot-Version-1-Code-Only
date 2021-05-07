package Java_DAO.delivery_DAO.delivery_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Reset_Password_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Reset_Password_DAO 
{
	public String deliveryResetPassword(Delivery_Reset_Password_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM delivery_register where delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				String delivery_forgot_otp_status = rs.getString("delivery_forgot_otp_status");
				
				if(delivery_forgot_otp_status.equals("TRUE"))
				{
					String query1 = "UPDATE delivery_register SET delivery_password = ?, delivery_forgot_otp_status = ?, delivery_forgot_otp = ? WHERE delivery_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, "False");
					ps1.setString(3, "null");
					ps1.setString(4, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Delivery Password has been Reset";
					}
					else
					{
						return "Delivery Password has not been Reset";
					}
				}
				else
				{
					return "Delivery Please verify OTP for the reset Password";
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
		
		return "Something went wrong in Delivery Password Reset";
	}
}