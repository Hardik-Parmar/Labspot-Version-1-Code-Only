package Java_DAO.delivery_DAO.delivery_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.delivery_Beans.delivery_after_login_Beans.Delivery_Change_Password_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Change_Password_DAO 
{
	public String delivery_Change_Password(Delivery_Change_Password_Bean bean)
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
				String delivery_old_password = rs.getString("delivery_password");
				
				if(delivery_old_password.equals(bean.getOld_password()))
				{
					String query1 = "UPDATE delivery_register SET delivery_password = ? WHERE delivery_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Delivery Password has been Changed";
					}
					else
					{
						return "Delivery Password has not been Changed";
					}
				}
				else
				{
					return "Delivery Please Enter the Correct Old Password";
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
		
		return "Something went wrong in Delivery Change Password";
	}
}