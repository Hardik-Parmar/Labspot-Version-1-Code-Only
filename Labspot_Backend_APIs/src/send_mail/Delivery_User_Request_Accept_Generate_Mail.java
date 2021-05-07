package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.Delivery_User_Request_Accept_Bean;

public class Delivery_User_Request_Accept_Generate_Mail implements Confidential_Details_Mail 
{
	public static String deliveryUserAcceptGenerateEmail(Delivery_User_Request_Accept_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getApplicant_email();
		String subject = "Delivery Boy Has Accepted Your Request";
		
		String message_text = "Hello "+bean.getApplicant_name()+"\n\nWe are feeling very Happy to inform you that your Delivery Request (Request ID: - " + bean.getRequest_id() + ") for Collection of Sample has been Accepted by Delivery Boy.\n\nPlease Wait Delivery Boy will be there at your location with-in sometime to Collection Sample and First 50% Payment of Test in CASH mode.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nDelivery Boy Accept Request Email Sent");
	        
	        return "Delivery Boy Accept Request Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}