package Java_DAO.lab_DAO.lab_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Change_Password_Bean;
import database_connection.ConnectionProvider;

public class Lab_Change_Password_DAO 
{
	public String lab_Change_Password(Lab_Change_Password_Bean bean)
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
				String lab_old_password = rs.getString("lab_password");
				
				if(lab_old_password.equals(bean.getOld_password()))
				{
					String query1 = "UPDATE lab_register SET lab_password = ? WHERE lab_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, bean.getNew_password());
					ps1.setString(2, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Lab Password has been Changed";
					}
					else
					{
						return "Lab Password has not been Changed";
					}
				}
				else
				{
					return "Lab Please Enter the Correct Old Password";
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
		
		return "Something went wrong in Lab Change Password";
	}
}