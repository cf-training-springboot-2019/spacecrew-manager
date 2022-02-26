package com.springboot.training.spaceover.spacecrew.manager.controller;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SEMI_COLON_DELIMITER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.WHITE_SPACE_DELIMITER;

import com.github.fge.jsonpatch.JsonPatchException;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.OperationErrorResponse;
import com.springboot.training.spaceover.spacecrew.manager.error.InvalidResourceStatusException;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//LT2.1-Include request validation
@RestControllerAdvice
public class ExceptionHandlingController {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    //LT2.5-Include EntityNotFoundException class handler method map to 404 HTTP status
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<OperationErrorResponse> handleNotFoundError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //LT2.2-Include MethodArgumentNotValidException class handler method map to 400 HTTP status
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OperationErrorResponse> handleBadRequestMethodArgument(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
            .map(f -> String.join(WHITE_SPACE_DELIMITER, f.getField(), f.getDefaultMessage()))
            .collect(Collectors.toList());
        return buildErrorMessageResponseEntity(String.join(SEMI_COLON_DELIMITER.concat(WHITE_SPACE_DELIMITER), fieldErrors),
            HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidResourceStatusException.class)
    //LT2.3-Include InvalidResourceStatusException class handler method map to 400 HTTP status
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidResourceStatus(InvalidResourceStatusException e) {
        return buildErrorMessageResponseEntity(e.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonPatchException.class)
    //LT2.4-Include JsonPatchException class handler method map to 400 HTTP status
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidJsonPatch(JsonPatchException e) {
        return buildErrorMessageResponseEntity(e.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    //LT2.6-Include DataIntegrityViolationException class handler method map to 409 HTTP status
    public ResponseEntity<OperationErrorResponse> handleConflict(DataIntegrityViolationException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    //LT2.7-Include Exception class handler method map to 500 HTTP status
    public ResponseEntity<OperationErrorResponse> handleInternalError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<OperationErrorResponse> buildErrorMessageResponseEntity(String msg, HttpStatus httpStatus) {
        return new ResponseEntity<>(
            OperationErrorResponse.builder()
                .message(msg)
                .code(httpStatus.value())
                .reason(httpStatus.getReasonPhrase())
                .status(httpStatus.value())
                .build(),
            httpStatus);
    }


}