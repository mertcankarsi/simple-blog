package com.mertcankarsi.simpleblog.controller;

import com.mertcankarsi.simpleblog.dto.request.OtpRequest;
import com.mertcankarsi.simpleblog.security.JwtService;
import com.mertcankarsi.simpleblog.service.OtpService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class OtpController {
  private final JwtService jwtService;
  private final OtpService otpService;

  public OtpController(OtpService otpService, JwtService jwtService) {
    this.otpService = otpService;
    this.jwtService = jwtService;
  }

  @PostMapping("/request-otp")
  public ResponseEntity<?> requestOtp(@Valid @RequestBody OtpRequest otpRequest) {
    otpService.generateOtp(otpRequest.getEmail());
    return ResponseEntity.ok("OTP sent");
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String code) {
    if (otpService.verifyOtp(email, code)) {
      String token = jwtService.generateToken(email);
      return ResponseEntity.ok(Map.of("token", token));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
    }
  }
}
