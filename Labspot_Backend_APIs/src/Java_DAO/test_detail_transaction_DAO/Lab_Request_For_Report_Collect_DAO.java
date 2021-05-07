package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Java_Beans.test_detail_transaction_Beans.Lab_Request_For_Report_Collect_Bean;
import database_connection.ConnectionProvider;
import send_mail.Lab_Request_For_Report_Collect_Alert_Mail;

public class Lab_Request_For_Report_Collect_DAO 
{
	public String request_For_Report_Collect(Lab_Request_For_Report_Collect_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM  delivery_register WHERE delivery_city = ? "
					+ "AND delivery_busy = ? ORDER BY RAND() LIMIT 1";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, bean.getLab_city());
			ps.setString(2, "NOT BUSY");
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				String delivery_name = resultSet.getString("delivery_name");
				String delivery_phone = resultSet.getString("delivery_phone");
				String delivery_email = resultSet.getString("delivery_email");
				String delivery_address = resultSet.getString("delivery_address");
				
				String query1 = "UPDATE test_transaction_details SET "
						+ "delivery_boy_name_2 = ?, delivery_boy_phone_2 = ?, "
						+ "delivery_boy_email_2 = ?, delivery_boy_address_2 = ?, "
						+ "delivery_boy_accept_2 = ? WHERE id = ?"
						+ " AND applicant_name = ? AND applicant_email = ? "
						+ "AND test_transaction_city = ? "
						+ "AND lab_name = ? AND lab_email = ?";
				
				PreparedStatement ps1 = connection.prepareStatement(query1);
				ps1.setString(1, delivery_name);
				ps1.setString(2, delivery_phone);
				ps1.setString(3, delivery_email);
				ps1.setString(4, delivery_address);
				ps1.setString(5, "PENDING");
				
				ps1.setString(6, bean.getRequest_id());
				ps1.setString(7, bean.getUser_name());
				ps1.setString(8, bean.getUser_email());
				ps1.setString(9, bean.getLab_city());
				ps1.setString(10, bean.getLab_name());
				ps1.setString(11, bean.getLab_email());
				
				int temp = ps1.executeUpdate();
				
				if(temp == 1)
				{
					String response = Lab_Request_For_Report_Collect_Alert_Mail.labRequestForReportCollectEmail(bean, delivery_name, delivery_address,  delivery_phone, delivery_email);
					
					if(response.equals("Lab Delivery Boy Assigned Email Sent"))
					{
						String response1 = Lab_Request_For_Report_Collect_Alert_Mail.labDeliveryBoyRequestEmail(bean, delivery_name, delivery_address,  delivery_phone, delivery_email);

						return "Delivery Boy is assigned please wait for him to accept request and " + response + " and " + response1;
					}
					
				}
				else
				{
					return "Error Delivery Boy is not Assigned";
				}
			}
			else
			{
				return "There is No Delivery Boy is Free, Please Try Again";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Request For Report Collect";
	}
}