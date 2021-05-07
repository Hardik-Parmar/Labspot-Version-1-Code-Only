package Java_DAO.user_DAO.user_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.user_Beans.user_after_login_Beans.User_Change_Password_Bean;
import database_connection.ConnectionProvider;

public class User_Change_Password_DAO 
{
	public String user_Change_Password(User_Change_Password_Bean bean)
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
				String user_old_password = rs.getString("user_password");
				
				if(user_old_password.equals(bean.getOld_password()))
				{
					String query1 = "UPDATE user_register SET user_password = ? WHERE user_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "User Password has been Changed";
					}
					else
					{
						return "User Password has not been Changed";
					}
				}
				else
				{
					return "User Please Enter the Correct Old Password";
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
		
		return "Something went wrong in User Change Password";
	}
}