package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.exceptions.NotFoundException;
import com.mananluvtocode.Recipie.exceptions.NumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// this is used for handling the exception at the global level as it will preprocess the request before sending it for the processing.
// It will save the duplicate of the code.
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView showingErrorPage(Exception exception) {
//        log.error("Not Found Error Exception for doing the particular Task");
//        log.error(exception.getMessage());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("404error");
//        modelAndView.addObject("exception", exception);
//        return modelAndView;
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberException.class)
//    public ModelAndView showBadRequestPage(Exception exception) {
//        log.error("The url contains the inappropriate things which can cause the trouble");
//        log.error(exception.getMessage());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("400error");
//        modelAndView.addObject("exception", exception);
//        return modelAndView;
//    }
}
