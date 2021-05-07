package Java_DAO.delivery_DAO.delivery_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Registration_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Registration_DAO 
{
	public String delivery_Register(Delivery_Registration_Bean bean, String otp)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - table contain only one entry with one email address (email element of table must be unique)
			
			String query = "SELECT * from delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
						
			ResultSet resultSet = ps.executeQuery();
						
			if(resultSet.next())
			{
				// TRUE if email already exist then we will not allow the entry in table
							
				return "Delivery Same User Already Exist";
			}
			else
			{
				String query1 = "INSERT INTO delivery_register(delivery_name, "
						+ "delivery_address, delivery_city, delivery_phone, delivery_dob, "
						+ "delivery_email, delivery_password, delivery_image_name, "
						+ "delivery_image_url, delivery_otp, delivery_status, "
						+ "delivery_available, delivery_busy, delivery_forgot_otp, "
						+ "delivery_forgot_otp_status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
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
				ps1.setString(11, "inactive"); // Setting delivery_status as "inactive" by default so delivery staff have to verify the account with OTP
				ps1.setString(12, "NOT AVAILABLE"); // Setting delivery_available as "NOT AVAILABLE" by default so delivery staff will not get any request for delivery unless he/she manually do available through the app
				ps1.setString(13, "YES"); // Setting delivery_busy as "YES" by default so delivery staff will not get any request for delivery
				ps1.setString(14, "null");
				ps1.setString(15, "null");
				
				int temp = ps1.executeUpdate();
				
				if(temp == 1)
				{
					return "Delivery Register Success";
				}
				else
				{
					return "Delivery Register Fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Something went wrong in Delivery Register";
	}

}