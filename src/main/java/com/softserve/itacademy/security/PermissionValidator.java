package com.softserve.itacademy.security;

import com.softserve.itacademy.exception.InvalidAccessException;

public class PermissionValidator {
    public static void validateOwnership(UserDetailsImpl userDetails, String email){
        if(!(userDetails.getUsername().equals(email) ||
                userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")))){
            throw new InvalidAccessException("cannot access other users' data");
        }
    }
}
