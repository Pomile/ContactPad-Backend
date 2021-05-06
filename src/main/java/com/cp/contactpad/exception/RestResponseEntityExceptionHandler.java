package com.cp.contactpad.exception;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleBadReqValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            Map<String, String> error = new HashMap<>();
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.put(fieldName, errorMessage);
            errors.add(error);
        });
        ClientError badRequest = new ClientError(400, HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value={ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadNumReqValidationExceptions(
            ConstraintViolationException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for(ConstraintViolation<?> violation: ex.getConstraintViolations()){
            Map<String, String> err = new HashMap<>();
            err.put( "" + violation.getInvalidValue(), violation.getMessage());
            errors.add(err);
        }
        ClientError badRequest = new ClientError(400, HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value={NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadNum1ReqValidationExceptions(
            NumberFormatException ex) {
        List<String> errors = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*)\"");
        Matcher matcher = pattern.matcher(ex.getMessage());
        while(matcher.find()){
            String str = matcher.group(1);
            errors.add("Invalid input for /"+str);
        }

        ClientError badRequest = new ClientError(400, HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value={ResponseStatusException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundExceptions(
            ResponseStatusException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getReason());
        System.err.println(Arrays.toString(ex.getStackTrace()));
        ClientError notFoundRequest = new ClientError(404, HttpStatus.NOT_FOUND, errors);
        return new ResponseEntity<>(notFoundRequest, HttpStatus.NOT_FOUND );
    }


    @ExceptionHandler(value
            = { DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<?> handleConflict(
            DataIntegrityViolationException ex) {
       //  StringBuilder message = new StringBuilder();
        List<String> messages = Arrays.asList(Objects.requireNonNull(ex.getMessage()).split(";"));
        List<String> errors = new ArrayList<>();
        Pattern pat = Pattern.compile("_(.*?)_");
        Matcher matcher = pat.matcher(messages.get(2));
        while (matcher.find()){
            String res = matcher.group(1);
            errors.add(res + " already exists");
        }
        ClientError conflict = new ClientError(409, HttpStatus.CONFLICT, errors);
        return new ResponseEntity<>(conflict, HttpStatus.CONFLICT);
    }
}
