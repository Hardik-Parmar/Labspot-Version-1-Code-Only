package Java_DAO.lab_DAO.lab_registration_and_login_DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Registration_Bean;
import database_connection.ConnectionProvider;

public class Lab_Registration_DAO 
{
	public String lab_Register(Lab_Registration_Bean bean, String otp)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - table contain only one entry with one email address (email element of table must be unique)
			
			String query = "SELECT * from lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
						
			ResultSet resultSet = ps.executeQuery();
						
			if(resultSet.next())
			{
				// TRUE if email already exist then we will not allow the entry in table
							
				return "Lab Same User Already Exist";
			}
			else
			{
				String query1 = "INSERT INTO lab_register(lab_name, lab_address, "
						+ "lab_city, lab_phone, lab_category, lab_established_year, "
						+ "lab_email, lab_password, lab_logo_name, lab_logo_url, "
						+ "lab_otp, lab_status, lab_forgot_otp, lab_forgot_otp_status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement ps1 = connection.prepareStatement(query1);
				
				// setting the data into DB query
				ps1.setString(1, bean.getName());
				ps1.setString(2, bean.getAddress());
				ps1.setString(3, bean.getCity());
				ps1.setString(4, bean.getPhone());
				ps1.setString(5, bean.getCategory());
				ps1.setString(6, bean.getEstablished_year());
				ps1.setString(7, bean.getEmail());
				ps1.setString(8, bean.getPassword());
				ps1.setString(9, bean.getImage_name());
				ps1.setString(10, bean.getImage_url());
				ps1.setString(11, otp);
				ps1.setString(12, "inactive"); // Setting lab_status "inactive" by default so lab user have to verify the account with OTP
				ps1.setString(13, "null");
				ps1.setString(14, "null");
				
				int temp = ps1.executeUpdate();
				
				if(temp == 1)
				{
					return "Lab Register Success";
				}
				else
				{
					return "Lab Register Fail";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Something went wrong in Lab Register";
	}
}