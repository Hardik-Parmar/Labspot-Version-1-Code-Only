package Java_DAO.test_detail_transaction_DAO.lab_request_otp_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_Bean;
import database_connection.ConnectionProvider;

public class User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_DAO
{
	public String user_Confirm_50_Percent_OTP_Verify_Part_2(User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM test_transaction_details WHERE id = ? AND "
					+ "applicant_name = ? AND lab_name = ? AND test_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, bean.getRequest_id());
			
			ps.setString(2, bean.getCustomer_name());
			
			ps.setString(3, bean.getLab_name());
			
			ps.setString(4, bean.getTest_name());
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				String otp = resultSet.getString("applicant_second_50_percent_cod_payment_otp_2");
				
				if(otp.equals(bean.getOtp_from_delivery()))
				{
					String query1 = "UPDATE test_transaction_details SET "
							+ "applicant_second_50_percent_cod_payment_otp_status_2 = ?, "
							+ "whole_cycle_completed = ? "
							+ "WHERE id = ? AND applicant_name = ? AND lab_name = ? "
							+ "AND test_name = ?";
					
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, "OTP_VERIFIED");
					
					ps1.setString(2, "COMPLETED");
					
					ps1.setString(3, bean.getRequest_id());
					
					ps1.setString(4, bean.getCustomer_name());
					
					ps1.setString(5, bean.getLab_name());
					
					ps1.setString(6, bean.getTest_name());
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "50 Percent Part 2 OTP Verified";
					}
					else
					{
						return "Something went wrong in User Delivery Confirm 50 Percent Payment OTP Verify Part 2";
					}
				}
				else
				{
					return "50 Percent Part 2 OTP not Verified, Wrong OTP";
				}
			}
			else
			{
				return "OTP to Payment has not been generated yet, please generate OTP first then verify the OTP";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in User Delivery Confirm 50 Percent Payment OTP Verify Part 2";
	}
}