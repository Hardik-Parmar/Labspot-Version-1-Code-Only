package send_mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_Bean;

public class User_Confirm_50_Percent_Payment_otp_Verify_Part_2_Mail implements Confidential_Details_Mail
{
	
	public static String userConfirm50PercentPaymentOTPVerifyPart2Email(User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getCustomer_email();
		String subject = "Confirmation of Second 50% Payment of Test in CASH mode";
		
		String message_text = "Hello "+bean.getCustomer_name()+"\n\nYour Second 50% Payment of Test in CASH mode (Request ID " +bean.getRequest_id()+ ") has been Successfully Received by Delivery Boy.\n\nHope You Enjoy and got an Amazing Experience of Digital way of Lab Testing.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nUser 50 Percent Payment Part 2 Confirm Email Sent");
	        
	        return "User 50 Percent Payment Part 2 Confirm Email Sent";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
	
	public static String deliveryBoyDeliveredReportToTheCustomerAlertToLabEmail(User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Part_2_Bean bean)
	{
		String user = Confidential_Details_Mail.USER;
		String password = Confidential_Details_Mail.PASSWORD;
		String to = bean.getLab_email();
		String subject = "Test Report has been Received by Customer";
		
		String message_text = "Hello "+bean.getLab_name()+"\n\nTest Report and All Other Necessary things (Request ID " + bean.getRequest_id() + ") has been delivered to the Customer.\n\nHope You Enjoy and got an Amazing Experience of Digital way to deal with Customer.\n\n- Thanks and Regards\nTeam LABSPOT";
		
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
	        System.out.println("\nReport Received Alert Email Sent to the Lab");
	        
	        return "Report Received Alert Email Sent to the Lab";
	    }
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		//return "Error Mail not sent yet";
	}
}