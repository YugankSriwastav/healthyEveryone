package shiva_care.healthify.exception.ExceptionHandling;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shiva_care.healthify.exception.NotificationException;
import shiva_care.healthify.exception.UserNameAlreadyCreated;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // First Exception
    @ExceptionHandler(UserNameAlreadyCreated.class)
    public ResponseEntity<ExceptionResponse> userNameAlreadyCreated(
            UserNameAlreadyCreated userNameAlreadyCreated,
            HttpServletRequest request
    ){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "User is Already Created",
                userNameAlreadyCreated.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ExceptionResponse> userNameAlreadyCreated(
            NotificationException notificationException,
            HttpServletRequest request
    ){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "User is Already Created",
                notificationException.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.ALREADY_REPORTED);
    }
}
