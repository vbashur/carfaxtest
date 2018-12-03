package com.vbashur.carfax.controller;

import com.vbashur.carfax.domain.ExternalErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

public class AbstractBaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    protected ExternalErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ExternalErrorInfo(req.getRequestURL().toString(), ex);
    }
}
