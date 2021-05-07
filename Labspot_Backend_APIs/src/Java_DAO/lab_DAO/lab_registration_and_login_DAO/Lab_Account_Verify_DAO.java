package Java_DAO.lab_DAO.lab_registration_and_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Account_Verify_Bean;
import database_connection.ConnectionProvider;

public class Lab_Account_Verify_DAO 
{
	public String labAccountVerify(Lab_Account_Verify_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// TRUE if user exist in the system
				
				String otp = resultSet.getString("lab_otp");
				
				if(otp.equals(bean.getOtp()))
				{
					String query1 = "UPDATE lab_register set lab_status = 'active', lab_otp = ? where lab_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setString(1, "null");
					ps1.setString(2, bean.getEmail());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Lab Account Verified";
					}
					else
					{
						return "Something went wrong in Lab Account Verification";
					}
				}
				else
				{
						return "Lab Account Not Verified Wrong OTP";
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
		
		return "Something went wrong in Lab Account Verification";
	}
}