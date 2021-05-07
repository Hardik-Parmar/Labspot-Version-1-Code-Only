package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.Place_Test_Request_to_Lab_Bean;

public class Place_Test_Request_to_Lab_Generate_Mail implements Confidential_Details_Mail
{
	
	public static String plactTestRequestToLabGenerateEmail(Place_Test_Request_to_Lab_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getLab_email();
		String subject = "You got A New Request for Test";
		
		String message_text = "Hello "+bean.getLab_name()+"\n\nYou just got a new Request for Test from a customer.\n\nPlease log-on to the App to View Request in Detail and take appropriate action on the Request.\n\nThe Some Details of Request is shown below.\n\nCustomer Name: - " + bean.getUser_name()+ "\nCustomer Address: - " + bean.getUser_address() + "\nCustomer Phone Number: - " + bean.getUser_phone() + "\nCustomer E-Mail I'D: - " + bean.getUser_email() + "\nRequested Test Name: - " + bean.getTest_name() + "\nRequest made Date: - " + bean.getDate() +"\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nPlace Test Request to Lab Email Sent");
	        
	        return "Place Test Request to Lab Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}