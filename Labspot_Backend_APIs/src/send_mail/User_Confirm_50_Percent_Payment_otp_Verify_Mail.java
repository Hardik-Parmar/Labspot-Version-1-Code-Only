package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean;

public class User_Confirm_50_Percent_Payment_otp_Verify_Mail implements Confidential_Details_Mail
{
	
	public static String userConfirm50PercentPaymentOTPVerifyEmail(User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getCustomer_email();
		String subject = "Confirmation of First 50% Payment of Test in CASH mode";
		
		String message_text = "Hello "+bean.getCustomer_name()+"\n\nYour First 50% Payment of Test in CASH mode (Request ID " +bean.getRequest_id()+ ") has been Successfully Received by Delivery Boy.\n\nAfter that Please Wait Delivery Boy will delivered your Sample to the Lab very soon and you will be notified for upcoming further updates.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nUser 50 Percent Payment Confirm Email Sent");
	        
	        return "User 50 Percent Payment Confirm Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}