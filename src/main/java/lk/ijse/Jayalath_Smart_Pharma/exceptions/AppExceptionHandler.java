package lk.ijse.Jayalath_Smart_Pharma.exceptions;

import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<CommonResponse> handleCustomerException(CustomerException ex) {
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
