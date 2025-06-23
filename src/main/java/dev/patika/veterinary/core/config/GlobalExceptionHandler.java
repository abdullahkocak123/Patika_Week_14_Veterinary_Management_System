package dev.patika.veterinary.core.config;

import dev.patika.veterinary.core.exception.AlreadyExistsException;
import dev.patika.veterinary.core.exception.AlreadyExistsExceptionValidVaccine;
import dev.patika.veterinary.core.exception.AppointmentNotAvailable;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //custom exception
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException (NotFoundException e){
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Result> handleAlreadyExistsException (AlreadyExistsException e){
        return new ResponseEntity<>(ResultHelper.alreadyExistsException(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppointmentNotAvailable.class)
    public ResponseEntity<Result> handleAppointmentNotAvailable (AppointmentNotAvailable e){
        return new ResponseEntity<>(ResultHelper.appointmentNotAvailable(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsExceptionValidVaccine.class)
    public ResponseEntity<Result> handleAlreadyExistsValidVaccine (AlreadyExistsExceptionValidVaccine e){
        return new ResponseEntity<>(ResultHelper.alreadyExistsValidVaccine(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    //built-in exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e){

        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());

        ResultData<List<String>> resultData = ResultHelper.validateErrorData(validationErrorList);
        return new ResponseEntity<>(resultData, HttpStatus.BAD_REQUEST);
    }
}
