package com.mertcankarsi.simpleblog.repository;

import com.mertcankarsi.simpleblog.entity.Otp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OtpRepository {

  private final JdbcTemplate jdbcTemplate;

  public OtpRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void save(Otp otp) {
    String sql = "INSERT INTO otps (email, code, expires_at) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, otp.email(), otp.code(), otp.expiresAt());
  }

  public Optional<Otp> findLatestByEmail(String email) {
    String sql =
        "SELECT email, code, expires_at FROM otps WHERE email = ? ORDER BY expires_at DESC LIMIT 1";
    return jdbcTemplate
        .query(
            sql,
            (rs, rowNum) ->
                new Otp(
                    rs.getString("email"),
                    rs.getString("code"),
                    rs.getTimestamp("expires_at").toLocalDateTime()),
            email)
        .stream()
        .findFirst();
  }

  public void deleteExpiredOtps() {
    String sql = "DELETE FROM otps WHERE expires_at < ?";
    jdbcTemplate.update(sql, LocalDateTime.now());
  }
}
