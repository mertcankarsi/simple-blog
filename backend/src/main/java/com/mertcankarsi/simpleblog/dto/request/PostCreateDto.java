package com.mertcankarsi.simpleblog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostCreateDto(
    @NotBlank(message = "Title cannot be empty") String title,
    @NotBlank(message = "Content cannot be empty") String content) {}
