package com.mertcankarsi.simpleblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private static final Logger log = LoggerFactory.getLogger(EmailService.class);

  /**
   * Sends an email to the specified address with the given subject and body.
   *
   * @param email the recipient's email address
   * @param subject the subject of the email
   * @param body the body content of the email
   */
  public void sendEmail(String email, String subject, String body) {
    log.info("Sending email to: {}, Subject: {}, Body: {}", email, subject, body);
  }
}
