package com.mungta.user.service;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

  @Autowired
  private JavaMailSender javaMailSender;

  public void mailSend (final String email,final String subject, final String text) {
		try{
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setFrom("-");
			message.setSubject(subject);
			message.setText(text);
			message.setSentDate(Calendar.getInstance().getTime());
			javaMailSender.send(message);

			} catch (MailParseException e) {
				e.printStackTrace();
			} catch (MailAuthenticationException e) {
				e.printStackTrace();
			} catch (MailSendException e) {
				e.printStackTrace();
			} catch (MailException e) {
				e.printStackTrace();
			}
	}
}
