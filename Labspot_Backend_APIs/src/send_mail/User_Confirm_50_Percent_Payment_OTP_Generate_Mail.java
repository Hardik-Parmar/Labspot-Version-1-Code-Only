package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Bean;

public class User_Confirm_50_Percent_Payment_OTP_Generate_Mail implements Confidential_Details_Mail
{
	
	public static String userConfirm50PercentPaymentOTPGenerateEmail(User_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Bean bean, String otp)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getCustomer_email();
		String subject = "OTP for Confirmation of First 50% Payment";
		
		String message_text = "Hello "+bean.getCustomer_name()+"\n\nYour Testing Sample and First 50% Payment of Test in CASH mode has been Received by Delivery Boy.\n\nOTP for Request I'D " +bean.getRequest_id()+ " is: - " + otp + "\n\nPlease give above OTP to Delivery Boy to confirm that your First 50% payment of Test in CASH mode and your Sample for Testing is received Successfully.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nDelivery Boy User 50 Payment Confirm Email Sent");
	        
	        return "Delivery Boy User 50 Payment Confirm Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}