package com.invoice.utils;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.invoice.bean.User;
import com.invoice.service.user.UserService;


@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	UserService userService;

	public void sendUserEmail(final User user) {
		final String emailId = user.getEmail();
		final String username = user.getUsername();
		final String password = user.getPassword();
		try {
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage)
						throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
							true, "UTF-8");
					message.setTo(emailId);
					message.setSubject("A new account has been created for you");
					message.setText("Welcome to Invoicing!! "
							+ "<br> Your Username is: <b>" + username
							+ "</b> and <br>" + "Password is: <b>" + password
							+ "</b>", true);
				}
			});
		} catch (MailException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public void sendForgotPasswordEmail(String Email, String Password,
			final User user) {
		final String emailId = Email;
		final String password = Password;

		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(emailId);
				message.setSubject("Invoicing Reset Password");
				message.setText("Welcome to CAOAS!! "
						+ "<br> Your new Password is: <b>" + password + "</b>",
						true);
				User ceo = (User) userService.getCEODetails().get(0);
				final String email = ceo.getEmail();
				message.addCc(email);
			}
		});
	}

	

	public void changePasswordEmail(String userEmail, String newPassword,
			User user, String userName) {
		User ceo = (User) userService.getCEODetails().get(0);
		final String userName1 = userName;
		final String email = ceo.getEmail();
		final String password = newPassword;
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(email);
				message.setSubject("User" + "  " + userName1 + "  "
						+ "has changed the password");
				message.setText(
						"<br> New Password is: <b>" + password + "</b>", true);

			}
		});
	}

	/*public void lockedUserEmail(final String email, final String username) {

		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(email);
				message.setSubject("Your account has been locked!");
				message.setText(
						"Dear "
								+ username
								+ ","
								+ "<br>Your Account has been locked due to 5 failure attempts! Please contact CEO ",
						true);
				User ceo = (User) preparerDao.getCEO().get(0);
				final String email = ceo.getEmail();
				message.addCc(email);

			}
		});

	}*/

	/*public void sendUnlockUserEmail(User user) {

		final String userEmail = user.getEmail();
		final String userName = user.getUsername();
		
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setTo(userEmail);
				message.setSubject("Your account has been unlocked!");
				message.setText("Dear " + userName + ","
						+ "<br>Your Account has been unlocked from the CEO",
						true);

			}
		});

	}*/
	
	
}
