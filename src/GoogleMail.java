//using gmail to send email to phone as a text
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleMail {

	private String username;
	private String password;
	private Properties props = new Properties();
	private Session session;
	private Message message;
	
	public GoogleMail() {
		username = new PrivateInfo().USERNAME;
		password = new PrivateInfo().PASSWORD;
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	//add a message body and then send
	public void addMsg(String msg, String phoneNum)	{
		try{
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(phoneNum));
			message.setSubject("Free Stuff");
			message.setText(msg);
			
			Transport.send(message);
		}
		catch(MessagingException e)
		{
			e.printStackTrace();
		}
		finally{
			
		}
	}
}
