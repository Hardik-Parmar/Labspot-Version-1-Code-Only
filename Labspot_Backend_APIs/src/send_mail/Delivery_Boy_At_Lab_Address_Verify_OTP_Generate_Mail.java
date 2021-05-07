package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean;

public class Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Mail implements Confidential_Details_Mail
{
	
	public static String deliveryBoyAtLabAddressVerifyOTPGenerateEmail(Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean bean, String otp)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getLab_email();
		String subject = "OTP to Authenticate Delivery Boy";
		
		String message_text = "Hello "+bean.getLab_name()+"\n\nDelivery Boy has been arrieved at your location for Giving the Sample.\n\nOTP for Request ID " +bean.getRequest_id()+ " is: - " + otp + "\n\nPlease give above OTP to Delivery Boy to verify him as Authenticated Person and Collect Sample for Test from him.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nDelivery Boy Confirm OTP at Lab Email Sent");
	        
	        return "Delivery Boy Confirm OTP at Lab Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}