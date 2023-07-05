package com.goodday.proj.exhandler;

import com.goodday.proj.exhandler.customex.LoginException;
import com.goodday.proj.exhandler.customex.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.goodday.proj.api")
public class ApiExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult userExHandle(RuntimeException e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("USER-EX", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ErrorResult loginExHandle(LoginException e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("LOGIN-EX", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResult notFoundExHandle(NotFoundException e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("NOT-FOUND-EX", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
