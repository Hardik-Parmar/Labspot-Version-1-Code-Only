package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.Lab_Request_For_Report_Collect_Bean;

public class Lab_Request_For_Report_Collect_Alert_Mail implements Confidential_Details_Mail 
{
	public static String labRequestForReportCollectEmail(Lab_Request_For_Report_Collect_Bean bean, String delivery_name, String delivery_address, String delivery_phone, String delivery_email)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getLab_email();
		String subject = "Delivery Boy Has Been Assigned to You";
		
		String message_text = "Hello "+bean.getLab_name()+"\n\nDelivery Boy has been assigned against your Request ID: - "+ bean.getRequest_id()+".\n\nThe Details are as below.\n\nDelivery Boy Name: - "+ delivery_name+"\nDelivery Boy Address: - "+delivery_address+"\nDelivery Boy Phone: - "+delivery_phone+"\nDelivery Boy E-Mail I'D: - "+delivery_email+"\n\nPlease Wait for Delivery Boy to take action on your Request.\nYou Will be notified when Delivery Boy Take any Action against your Request.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nLab Delivery Boy Assigned Email Sent");
	        
	        return "Lab Delivery Boy Assigned Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
	
	public static String labDeliveryBoyRequestEmail(Lab_Request_For_Report_Collect_Bean bean, String delivery_name, String delivery_address, String delivery_phone, String delivery_email)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = delivery_email;
		String subject = "You got a Request for Report Collection";
		
		String message_text = "Hello "+delivery_name+"\n\nYou got and Request for Report Collection from Laboratory Location your Request ID: - "+ bean.getRequest_id()+"\n\nLab Name: - " + bean.getLab_name() + "\nCustomer Name: - " + bean.getUser_name() + "\n\nPlease log-on into App for to View Request in Detail.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nDelivery Boy Email Sent");
	        
	        return "Delivery Boy Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}