package Java_DAO.lab_DAO.lab_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Login_Bean;
import database_connection.ConnectionProvider;

public class Lab_Login_DAO 
{

	public String lab_login(Lab_Login_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking status of user if it is inactive then we will not allow user to login into the system
			
			String query = "SELECT * from lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String status = resultSet.getString("lab_status");
				
				if(status.equals("inactive"))
				{
					return "Please Verify Your Lab Account";
				}
				else
				{
					String query1 = "SELECT * from lab_register WHERE lab_email = ? and lab_password = ?";
					
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, bean.getEmail());
					ps1.setString(2, bean.getPassword());
					
					ResultSet resultSet1 = ps1.executeQuery();
					
					if(resultSet1.next())
					{
						if(resultSet1.getString("lab_password").equals(bean.getPassword()))
						{
							return "Lab Login Success";
						}
						else
						{
							return "Lab Login Fail Wrong Password";
						}
					}
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
		
		return "Something went wrong in Lab Login";
	}
}