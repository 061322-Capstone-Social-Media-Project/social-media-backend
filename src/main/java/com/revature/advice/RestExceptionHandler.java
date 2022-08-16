package com.revature.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException notLoggedInException) {

        String errorMessage = "Must be logged in to perform this action";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(HttpServletRequest request, UserNotFoundException e) {

        String errorMessage = "Unable to find this user";

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    
    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<Object> handleAlreadyFollowingException(HttpServletRequest request, AlreadyFollowingException e) {

        String errorMessage = "You are already following this user";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    
    @ExceptionHandler(NotFollowingException.class)
    public ResponseEntity<Object> handleNotFollowingException(HttpServletRequest request, NotFollowingException e) {

        String errorMessage = "You are not following this user";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
