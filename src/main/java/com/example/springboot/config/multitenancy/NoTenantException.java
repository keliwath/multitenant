package com.example.springboot.config.multitenancy;

public class NoTenantException extends RuntimeException {

    public NoTenantException(String message) {
        super(message);
    }
}
