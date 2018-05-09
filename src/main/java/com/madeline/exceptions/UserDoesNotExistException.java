package com.madeline.exceptions;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(Long id){
        super("User with id - "+id+" doesn't exists");
    }

}
