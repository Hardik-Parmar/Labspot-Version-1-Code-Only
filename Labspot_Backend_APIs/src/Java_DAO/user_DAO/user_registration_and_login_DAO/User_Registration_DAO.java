package Java_DAO.user_DAO.user_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Registration_Bean;
import database_connection.ConnectionProvider;

public class User_Registration_DAO 
{
	public String user_Register(User_Registration_Bean bean, String otp)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - table contain only one entry with one email address (email element of table must be unique)
			
			String query = "SELECT * from user_register WHERE user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
						
			ResultSet resultSet = ps.executeQuery();
						
			if(resultSet.next())
			{
				// TRUE if email already exist then we will not allow the entry in table
							
				return "User Same User Already Exist";
			}
			else
			{
				String query1 = "INSERT INTO user_register(user_name, user_address, "
						+ "user_city, user_phone, user_dob, user_email, user_password, "
						+ "user_image_name, user_image_url, user_otp, user_status, "
						+ "user_forgot_otp, user_forgot_otp_status) values"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement ps1 = connection.prepareStatement(query1);
				
				// setting the data into DB query
				ps1.setString(1, bean.getName());
				ps1.setString(2, bean.getAddress());
				ps1.setString(3, bean.getCity());
				ps1.setString(4, bean.getPhone());
				ps1.setString(5, bean.getDOB());
				ps1.setString(6, bean.getEmail());
				ps1.setString(7, bean.getPassword());
				ps1.setString(8, bean.getImage_name());
				ps1.setString(9, bean.getImage_url());
				ps1.setString(10, otp);
				ps1.setString(11, "inactive"); // Setting user_status "inactive" by default so user have to verify the account with OTP
				ps1.setString(12, "null");
				ps1.setString(13, "null");
				
				int temp = ps1.executeUpdate();
				
				if(temp == 1)
				{
					return "User Register Success";
				}
				else
				{
					return "User Register Fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Something went wrong in User Register";
	}
}