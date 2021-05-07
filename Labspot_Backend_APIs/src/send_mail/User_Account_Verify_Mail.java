package send_mail;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;
  
public class User_Account_Verify_Mail implements Confidential_Details_Mail
{
	public static String userAccountVerifyEmail(String receiver_mail, String otp, String name)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = receiver_mail;
		String subject = "Mail for confirmation of Registration Process and Verify Account";
		
		String message_text = "Hello "+name+"\n\nYou have Successfully Registered as General User in our App.\n\nPlease enter this OTP to verify your account : - "+otp+"\n\n- Thanks and Regards\nTeam LABSPOT";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		//properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
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
	        System.out.println("\nUser Email Sent Successfully");
	        
	        return "User Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}