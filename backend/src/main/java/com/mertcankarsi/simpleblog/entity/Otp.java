package com.mertcankarsi.simpleblog.entity;

import java.time.LocalDateTime;

public record Otp(String email, String code, LocalDateTime expiresAt) {}
