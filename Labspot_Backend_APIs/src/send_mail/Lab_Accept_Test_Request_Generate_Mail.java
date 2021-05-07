package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.Lab_Take_Action_On_Test_Request_Bean;

public class Lab_Accept_Test_Request_Generate_Mail implements Confidential_Details_Mail
{
	
	public static String labAcceptTestGenerateEmail(Lab_Take_Action_On_Test_Request_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getUser_mail();
		String subject = "Your Request for Test has been Accepted";
		
		String message_text = "Hello "+bean.getUser_name()+"\n\nWe are feeling very Happy to inform you that your Request for Test has been Accepted by Lab.\n\nThe Details of Request is shown below.\n\nRequest ID: - " + bean.getId()+ "\nTest Name: - " + bean.getTest_name() + "\nDate of Request Made: - " + bean.getDate() + "\nDate of Request got Accepted: - " + bean.getDate_of_action() + "\n\nPlease log-on to the App and tap 'Request to Collect Sample' to Request for Delivery Boy to Collect your Testing Sample.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nLab Accept Test Request Email Sent");
	        
	        return "Lab Accept Test Request Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}