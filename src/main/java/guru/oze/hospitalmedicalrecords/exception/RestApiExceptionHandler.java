package guru.oze.hospitalmedicalrecords.exception;

import guru.oze.hospitalmedicalrecords.security.exception.SignUpException;
import guru.oze.hospitalmedicalrecords.security.exception.UserNotActivatedException;
import guru.oze.hospitalmedicalrecords.service.constant.ResponseCode;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
@Slf4j
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(" handleHttpMessageNotReadable ", ex);
        String errorMessage = "Invalid request payload";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    // Handle handleClientErrorException. Happens when request Entity is unprocessable.
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleClientErrorException(HttpClientErrorException ex) {
        log.error(" handleClientErrorException ", ex);
        String errorMessage = "Request entity unprocessable";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Handles IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(" handleIllegalArgumentException ", ex);
        String errorMessage = "Invalid parameters";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    // Handles ResourceAccessException
    @ExceptionHandler(ResourceAccessException.class)
    protected ResponseEntity<Object> handleResourceAccessException(ResourceAccessException ex) {
        log.error(" handleResourceAccessException exception ", ex);
        String errorMessage = "Invalid access to resource";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.UNAUTHORIZED);
    }

    // Handles ConnectException
    // Handle ConnectException. Happens when request JSON is malformed.
    @ExceptionHandler(ConnectException.class)
    protected ResponseEntity<Object> handleConnectException(ConnectException ex) {
        log.error(" handleConnectException ", ex);
        String errorMessage = "Remote connection not found";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Handles AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(" handleAccessDeniedException ", ex);
        String errorMessage = "Access denied";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.UNAUTHORIZED);
    }

    // Handles IllegalStateException
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalException(IllegalStateException ex) {
        log.error(" handleIllegalException ", ex);
        String errorMessage = "Malformed JSON request";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    // Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        log.error(" handleEntityNotFound ", ex);
        String errorMessage = "Resource object not found";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND);
    }

    //Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error(" handleMissingServletRequestParameter ", ex);
        String errorMessage = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //  Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid.
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        log.error(" handleHttpMediaTypeNotSupported ", ex);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        return buildResponseEntity(ResponseCode.ERROR.getCode(), builder.substring(0, builder.length() - 2), ex.getLocalizedMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error(" handleMethodArgumentNotValid ", ex);
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String errorMessage = objectError.getDefaultMessage();
            errors.add(errorMessage);
        });

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        return buildResponseEntity(ResponseCode.ERROR.getCode(), String.join("\n", errors).replace(",", ""), ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        log.error(" handleConstraintViolation ", ex);
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        constraintViolations.forEach(constraintViolation -> {
            String message = constraintViolation.getMessage();
            String messageTemplate = constraintViolation.getMessageTemplate();
            errors.put(messageTemplate, message);
        });

        return buildResponseEntity(
                ResponseCode.ERROR.getCode(),
                errors.entrySet()
                        .stream()
                        .map(e -> e.getKey() + " : " + e.getValue())
                        .collect(Collectors.joining("\n")),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    // Handle HttpMessageNotWritableException.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(" handleHttpMessageNotWritable ", ex);
        String errorMessage = "Error writing JSON output";
        System.out.println("Error writing JSON output ==> Cause " + ex.getCause());
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //Handle NoHandlerFoundException.
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        log.error(" handleHttpMessageNotWritable ", ex);
        String errorMessage = String.format("Could not find the %s method for URL %s",
                ex.getHttpMethod(), ex.getRequestURL());
        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //Handle MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("handleMethodArgumentTypeMismatch ", ex);
        String errorMessage = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType());

        return buildResponseEntity(ResponseCode.ERROR.getCode(), errorMessage, ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    //Handle DataIntegrityViolationException, inspects the cause for different DB causes.
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("handleDataIntegrityViolation ", ex);
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(ResponseCode.ERROR.getCode(), "Database error", ex.getLocalizedMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return buildResponseEntity(ResponseCode.ERROR.getCode(), "Invalid input data", ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //Handle GenralExceptiom
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        log.error("handleAllException ", ex);

        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "An error occurred, please try again",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

     //Handle GenralExceptiom
    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleAllException(Throwable ex, WebRequest request) {
        log.error("handleAllThrowableException ", ex);

        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Error!, please try again",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex) {
        log.error("handleHttpServerErrorException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Connection error, please try again",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("handleBadCredentialsException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Bad credentials",
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("handleUsernameNotFoundException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Username not found!",
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected ResponseEntity<Object> handleAInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        log.error("handleAInsufficientAuthenticationException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Insufficient authentication",
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }



    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        log.error("handleNullPointerException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "A null error occurred! Please try again",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );

    }

    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
        log.error("handleNumberFormatException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Invalid Number format",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<Object> handleGenericException(GenericException ex) {
        log.error("handleGenericException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(NonUniqueResultException.class)
    protected ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex) {
        log.error("handleNonUniqueResultException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Data already exist",
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SocketTimeoutException.class)
    protected ResponseEntity<Object> handleSocketTimeoutException(SocketTimeoutException ex) {
        log.error("handleNonUniqueResultException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                "Api timeout",
                ex.getLocalizedMessage(), HttpStatus.REQUEST_TIMEOUT
        );
    }

    @ExceptionHandler(ApiKeyNotSetException.class)
    protected ResponseEntity<Object> handleApiKeyNotSetException(ApiKeyNotSetException ex) {
        log.error("handleApiKeyNotSetException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UserNotActivatedException.class)
    protected ResponseEntity<Object> handleUserNotActivatedException(UserNotActivatedException ex) {
        log.error("handleUserNotActivatedException", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FailedDecryptionException.class)
    protected ResponseEntity<Object> handleFailedDecryptionException(FailedDecryptionException ex) {
        log.error("handleFailedDecryptionException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(FailedExportToCsvException.class)
    protected ResponseEntity<Object> handleFailedExportToCsvException(FailedExportToCsvException ex) {
        log.error("handleFailedExportToCsvException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidAccessKeyException.class)
    protected ResponseEntity<Object> handleInvalidAccessKeyException(InvalidAccessKeyException ex) {
        log.error("handleInvalidAccessKeyException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InvalidPatientException.class)
    protected ResponseEntity<Object> handleInvalidPatientException(InvalidPatientException ex) {
        log.error("handleInvalidAccessKeyException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignUpException.class)
    protected ResponseEntity<Object> handleSignUpException(SignUpException ex) {
        log.error("handleSignUpException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidUserNameException.class)
    protected ResponseEntity<Object> handleInvalidUserNameException(InvalidUserNameException ex) {
        log.error("handleInvalidUserNameException ", ex);
        return buildResponseEntity(ResponseCode.ERROR.getCode(),
                ex.getMessage(),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<Object> buildResponseEntity(String code, String errorMessage, Object data,
                                                       HttpStatus status) {
        ApiResponse response = ApiResponse.builder()
                .code(code)
                .message(errorMessage)
                .data(data)
                .build();
        return new ResponseEntity<>(response, status);
    }

}
