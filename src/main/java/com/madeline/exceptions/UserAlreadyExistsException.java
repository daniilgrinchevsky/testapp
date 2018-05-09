package com.madeline.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(Long id){
        super("User with id - "+id+" already exists");
    }
}
