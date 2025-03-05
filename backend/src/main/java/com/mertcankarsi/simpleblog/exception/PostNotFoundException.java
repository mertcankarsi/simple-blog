package com.mertcankarsi.simpleblog.exception;

public class PostNotFoundException extends RuntimeException {
    
    public PostNotFoundException(String referenceKey) {
        super(referenceKey);
    }
} 