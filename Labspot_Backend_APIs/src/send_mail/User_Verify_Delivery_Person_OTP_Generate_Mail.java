package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Request_Delivery_Verify_otp_Generate_Bean;

public class User_Verify_Delivery_Person_OTP_Generate_Mail implements Confidential_Details_Mail
{
	
	public static String userVerifyDeliveryPersonOTPGenerateEmail(User_Request_Delivery_Verify_otp_Generate_Bean bean, String otp)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getCustomer_email();
		String subject = "OTP to Authenticate Delivery Boy";
		
		String message_text = "Hello "+bean.getCustomer_name()+"\n\nDelivery Boy has been arrieved at your location for Sample Collection.\n\nOTP for Request ID " +bean.getRequest_id()+ " is: - " + otp + "\n\nPlease give above OTP to Delivery Boy to verify him as Authenticated Person to Collect your Sample and Payment.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nDelivery Boy Confirm OTP Email Sent");
	        
	        return "Delivery Boy Confirm OTP Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}