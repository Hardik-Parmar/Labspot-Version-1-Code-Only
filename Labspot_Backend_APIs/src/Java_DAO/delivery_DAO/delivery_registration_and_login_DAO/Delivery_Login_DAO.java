package Java_DAO.delivery_DAO.delivery_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Login_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Login_DAO 
{
	public String delivery_login(Delivery_Login_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking status of user if it is inactive then we will not allow user to login into the system
			
			String query = "SELECT * from delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String status = resultSet.getString("delivery_status");
				
				if(status.equals("inactive"))
				{
					return "Please Activate Your Delivery Account";
				}
				else
				{
					String query1 = "SELECT * from delivery_register WHERE delivery_email = ? and delivery_password = ?";
					
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, bean.getEmail());
					ps1.setString(2, bean.getPassword());
					
					ResultSet resultSet1 = ps1.executeQuery();
					
					if(resultSet1.next())
					{
						if(resultSet1.getString("delivery_password").equals(bean.getPassword()))
						{
							return "Delivery Login Success";
						}
						else
						{
							return "Delivery Login Fail Wrong Password";
						}
					}
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
		
		return "Something went wrong in Delivery Login";
	}
}