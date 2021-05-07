package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans.User_Request_Delivery_Verify_otp_Verify_Part_2_Bean;

public class User_Request_Delivery_Verify_otp_Verify_Part_2_Mail implements Confidential_Details_Mail
{
	
	public static String userVerifyDeliveryOTPVerifyPart2Email(User_Request_Delivery_Verify_otp_Verify_Part_2_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getCustomer_email();
		String subject = "Confirmation of Delivery Person";
		
		String message_text = "Hello "+bean.getCustomer_name()+"\n\nNow Delivery Person is Verified and Authenticated for Request I'D " +bean.getRequest_id()+ " Please Collect Test Report and All Other Necessary things.\n\nVerification Time is: - " + bean.getDate() +"\n\n- Thanks and Regards\nTeam LABSPOT";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(user,password);
			}
		});
		
		try 
		{
			MimeMessage message = new MimeMessage(session);    
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	        message.setSubject(subject);    
	        message.setText(message_text);    
	        
	        Transport.send(message);    
	        System.out.println("\nDelivery Boy Part 2 Verified Email Sent");
	        
	        return "Delivery Boy Part 2 Verified Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}