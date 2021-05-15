package com.springboot.training.spaceover.spacecrew.manager.controller;

import com.github.fge.jsonpatch.JsonPatchException;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.OperationErrorResponse;
import com.springboot.training.spaceover.spacecrew.manager.error.InvalidResourceStatusException;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.*;
import static java.util.stream.Collectors.joining;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingController implements ResponseBodyAdvice<Object> {

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<OperationErrorResponse> handleNotFoundError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidResourceStatus(InvalidResourceStatusException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonPatchException.class)
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidJsonPatch(JsonPatchException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<OperationErrorResponse> handleConflict(DataIntegrityViolationException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OperationErrorResponse> handleInternalError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<OperationErrorResponse> buildErrorMessageResponseEntity(String msg, HttpStatus httpStatus) {
        log.error(msg);
        return new ResponseEntity<>(
                OperationErrorResponse.builder()
                        .message(msg)
                        .code(httpStatus.value())
                        .reason(httpStatus.getReasonPhrase())
                        .status(httpStatus.value())
                        .build(),
                httpStatus);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (body instanceof PagedModel<?> && spaceCrewManagerProperties.isOpenApiHeaderPaginationEnabled()) {
            PagedModel pagedModel = (PagedModel) body;
            serverHttpResponse.getHeaders()
                    .add(LINK_HEADER, pagedModel.getLinks().stream().map(Link::toString).collect(joining(SEMI_COLON_DELIMITER)));
            serverHttpResponse.getHeaders().add(PAGE_NUMBER_HEADER, String.valueOf(pagedModel.getMetadata().getNumber()));
            serverHttpResponse.getHeaders().add(PAGE_SIZE_HEADER, String.valueOf(pagedModel.getMetadata().getSize()));
            serverHttpResponse.getHeaders()
                    .add(TOTAL_ELEMENTS_HEADER, String.valueOf(pagedModel.getMetadata().getTotalElements()));
            serverHttpResponse.getHeaders().add(TOTAL_PAGES_HEADER, String.valueOf(pagedModel.getMetadata().getTotalPages()));
            return pagedModel.getContent();
        }

        return body;
    }

}