package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Java_Beans.All_Contact_Us_Bean;

public class Contact_Us_Alert_Mail implements Confidential_Details_Mail 
{
	public static String contactUsAlertEmail(All_Contact_Us_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = Confidential_Details_Mail.USER;
		String subject = "Feedback from LABSPOT App";
		
		String message_text = "Hey Admin\n\nYou have got one Feedback from LABSPOT App.\n\nThe Details of the Feedback are as below.\n\nType of User: - "+bean.getUser_type_name()+"\nName of User: - "+bean.getName()+"\nEmail I'd of User: - "+bean.getEmail()+"\nFeedback Message from User: - "+bean.getFeedback()+"\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nUser Email Sent Successfully");
	        
	        return "Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}