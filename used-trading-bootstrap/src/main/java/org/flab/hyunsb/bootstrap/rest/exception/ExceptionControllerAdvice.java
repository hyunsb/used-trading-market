package org.flab.hyunsb.bootstrap.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.flab.hyunsb.application.exception.authentication.AuthenticationException;
import org.flab.hyunsb.application.exception.constraint.ConstraintException;
import org.flab.hyunsb.domain.exception.MemberAuthException;
import org.flab.hyunsb.domain.exception.PasswordConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationException(
        MethodArgumentNotValidException exception, HttpServletRequest request) {
        logError(exception, request.getRequestURL().toString());

        String errorMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        return ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, errorMessage);
    }

    private void logError(Exception exception, String requestUrl) {
        log.error(
            "[{}]: requestUrl={}, stackTrace={}",
            exception.getClass(), requestUrl, exception.getStackTrace()
        );
    }

    @ExceptionHandler({ConstraintException.class, PasswordConstraintException.class,
        NotFoundException.class, MethodArgumentTypeMismatchException.class})
    public ErrorResponse constraintException(
        RuntimeException exception, HttpServletRequest request) {
        logError(exception, request.getRequestURL().toString());

        return ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({ActorTokenException.class, AuthenticationException.class,
        MemberAuthException.class})
    public ErrorResponse authenticationException(
        RuntimeException exception, HttpServletRequest request) {
        logError(exception, request.getRequestURL().toString());

        return ErrorResponse.create(exception, HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse runtimeException(
        RuntimeException exception, HttpServletRequest request) {
        logError(exception, request.getRequestURL().toString());

        return ErrorResponse.create(exception, HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage());
    }
}
