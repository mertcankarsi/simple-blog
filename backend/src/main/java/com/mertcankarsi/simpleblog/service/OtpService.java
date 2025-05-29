package com.mertcankarsi.simpleblog.service;

import com.mertcankarsi.simpleblog.entity.Otp;
import com.mertcankarsi.simpleblog.repository.OtpRepository;
import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

  private final Logger logger = LoggerFactory.getLogger(OtpService.class);
  private final Random random = new Random();
  private final EmailService emailService;
  private final OtpRepository otpRepository;
  private final Integer otpTtl; // Time to live for OTP in minutes

  public OtpService(
      EmailService emailService,
      OtpRepository otpRepository,
      @Value("${otp.ttl:5}") Integer otpTtl) {
    this.emailService = emailService;
    this.otpRepository = otpRepository;
    this.otpTtl = otpTtl;
  }

  /**
   * Generates a One-Time Password (OTP) for the given email and sends it via email.
   *
   * @param email The email address to which the OTP will be sent.
   */
  public void generateOtp(String email) {
    logger.info("Generating OTP for email: {}", email);
    String otp = generateRandomOtp();
    saveOtpToDatabase(email, otp);
    sendOtpEmail(email, otp);
  }

  /**
   * Verifies the OTP for the given email.
   *
   * @param email The email address associated with the OTP.
   * @param code The OTP code to verify.
   * @return true if the OTP is valid and not expired, false otherwise.
   */
  public boolean verifyOtp(String email, String code) {
    logger.info("Verifying OTP for email: {}", email);
    var latestOtp = otpRepository.findLatestByEmail(email);
    if (latestOtp.isEmpty()) {
      logger.warn("No OTP found for email: {}", email);
      return false;
    }
    Otp otp = latestOtp.get();
    if (otp.expiresAt().isBefore(LocalDateTime.now())) {
      logger.warn("OTP verification failed for email: {}. OTP is expired.", email);
      return false;
    }
    if (otp.code().equals(code)) {
      logger.info("OTP verified successfully for email: {}", email);
      return true;
    }
    return false;
  }

  /**
   * Generates a 6-digit OTP (One-Time Password).
   *
   * @return A random 6-digit OTP as a String.
   */
  private String generateRandomOtp() {
    int otp = 100000 + random.nextInt(900000);
    return String.valueOf(otp);
  }

  /**
   * Saves the generated OTP to the database associated with the user's email.
   *
   * @param email The email address of the user to whom the OTP is associated.
   * @param otp The generated OTP to be saved.
   */
  private void saveOtpToDatabase(String email, String otp) {
    logger.info("Saving OTP for email {}", email);
    Otp otpCode = new Otp(email, otp, LocalDateTime.now().plusMinutes(5));
    otpRepository.save(otpCode);
  }

  /**
   * Sends the generated OTP to the user's email address.
   *
   * @param email The email address of the user.
   * @param otp The generated OTP to be sent.
   */
  private void sendOtpEmail(String email, String otp) {
    emailService.sendEmail(
        email,
        "Your OTP Code",
        String.format("Your OTP code is: %s. It is valid for %d minutes.", otp, otpTtl));
  }
}
