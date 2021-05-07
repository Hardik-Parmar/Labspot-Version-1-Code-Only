package Java_DAO.user_DAO.user_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Login_Bean;
import database_connection.ConnectionProvider;

public class User_Login_DAO 
{
	public String user_login(User_Login_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking status of user if it is inactive then we will not allow user to login into the system
			
			String query = "SELECT * from user_register WHERE user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String status = resultSet.getString("user_status");
				
				if(status.equals("inactive"))
				{
					return "Please Verify Your User Account";
				}
				else
				{
					String query1 = "SELECT * from user_register WHERE user_email = ? and user_password = ?";
					
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, bean.getEmail());
					ps1.setString(2, bean.getPassword());
					
					ResultSet resultSet1 = ps1.executeQuery();
					
					if(resultSet1.next())
					{
						if(resultSet1.getString("user_password").equals(bean.getPassword()))
						{
							return "User Login Success";
						}
						else
						{
							return "User Login Fail Wrong Password";
						}
					}
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
		
		return "Something went wrong in User Login";
	}
}