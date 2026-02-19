package io.klustr.permissions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MissingPermissionsHandler {
    @ExceptionHandler(MissingPermissionsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MissingPermissionsResponse handleSecurityException(MissingPermissionsException se) {
        return new MissingPermissionsResponse(se.getMissingPermissions());
    }
}
