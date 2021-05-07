package Java_DAO.user_DAO.user_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.user_Beans.user_after_login_Beans.User_Change_Address_Bean;
import database_connection.ConnectionProvider;

public class User_Change_Address_DAO 
{
	public String user_Change_Address(User_Change_Address_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE user_register SET user_address = ? where user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getAddress());
			ps.setString(2, bean.getEmail());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "User Address has been Changed";
			}
			else
			{
				return "User Address has not been Changed";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in User Change Address";
	}
}