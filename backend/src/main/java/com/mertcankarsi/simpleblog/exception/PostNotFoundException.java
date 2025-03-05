package com.mertcankarsi.simpleblog.exception;

public class PostNotFoundException extends RuntimeException {
    
    public PostNotFoundException(String referenceKey) {
        super("Post not found with reference key: " + referenceKey);
    }
} 