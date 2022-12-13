package io.github.luaprogrammer.poc.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";
    private static final String CONSTANT_VALIDATION_EMAIL = "Email";
    private static final String CONSTANT_VALIDATION_CNPJ = "CNPJ";
    private static final String CONSTANT_VALIDATION_CPF = "CPF";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Errors> errors = errorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String msgUser = "id not found";
        String msgServer = ex.toString();

        List<Errors> errors = Arrays.asList(new Errors(msgUser, msgServer));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RuleBusinessException.class)
    public ResponseEntity<Object> handleRuleBusinessException(RuleBusinessException ex, WebRequest request) {
        String msgUser = ex.getMessage();
        String msgServer = ex.getMessage();

        List<Errors> errors = Arrays.asList(new Errors(msgUser, msgServer));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        String msgUser = "Customer with CNPJ/CPF or registered email.";
        String msgServer = ex.getMessage();

        List<Errors> errors = Arrays.asList(new Errors(msgUser, msgServer));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Errors> errorsList(BindingResult bindingResult) {
        List<Errors> errorsList = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldErrors -> {
            String msgUser = handleUserErrorMessage(fieldErrors);
            String msgServer = fieldErrors.toString();
            errorsList.add(new Errors(msgUser, msgServer));
        });

        return errorsList;
    }

    private String handleUserErrorMessage(FieldError fieldErrors) {
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fieldErrors.getDefaultMessage().concat(" is required.");
        }
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_NOT_NULL)) {
            return fieldErrors.getDefaultMessage().concat(" cannot be null.");
        }
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            return fieldErrors.getDefaultMessage().concat(String.format(" must be between %s and %s characters", fieldErrors.getArguments()[2], fieldErrors.getArguments()[1]));
        }
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_EMAIL)) {
            return fieldErrors.getDefaultMessage();
        }
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_CNPJ)) {
            return fieldErrors.getDefaultMessage();
        }
        if (fieldErrors.getCode().equals(CONSTANT_VALIDATION_CPF)) {
            return fieldErrors.getDefaultMessage();
        }
        return fieldErrors.toString();
    }
}


