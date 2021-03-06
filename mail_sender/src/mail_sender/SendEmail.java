/**
 * @author yogeesh.srkvs@gmail.com
 *
 *Example of how to send email via smtp server (used gmail smtp server) 
 */
package mail_sender;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail
{

	private Properties emailProps = new Properties(); 
	private Session emailSession; 
	private MimeMessage message;
	private Logger log = Logger.getLogger(getClass().getName()); 
	
	public static void main(String [] args)
	{
		SendEmail emailSenderObject = new SendEmail();
		
		emailSenderObject.setProps();
		emailSenderObject.buildContent();
		emailSenderObject.pushOverTheAir();
	 }

	/**
	 * Method to send email
	 */
	private void pushOverTheAir() {
		try {
			Transport transport = emailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", "fromemailid@gmail.com", "password");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("Email sent successfully  "+ new Date().toString());
		} catch (MessagingException mxe) {
			log.log(Level.SEVERE, " Error sending emial ", mxe);
		}
		
	}

	/**
	 * Method to build actual email
	 */
	private void buildContent() {
		
		String toEmailId = "toemailreceipient@gmail.com";
		String emailSubject = "[ Mail Sent Via Java API ] "+"Subject Line";
		String emailContent = "Java is fun, sending mail via Java is fun too !";

		emailSession = Session.getDefaultInstance(emailProps);
		message = new MimeMessage(emailSession);
		
		try {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));
			message.setSubject(emailSubject);
			message.setContent(emailContent, "text/html");
		} catch (AddressException axe) {
			log.log(Level.SEVERE, " Error constructing message ", axe);
		} catch (MessagingException mex) {
			log.log(Level.SEVERE, " Error constructing message ", mex);
		}

	}

	/**
	 * Method to set all email properties
	 */
	private void setProps() {
		emailProps = System.getProperties();
		
		// Port number : 587 , since we are trying to use gmail here
		emailProps.put("mail.smtp.port", 587);
		emailProps.put("mail.smtp.auth", "true");
		emailProps.put("mail.smtp.starttls.enable", "true");
	}
}

