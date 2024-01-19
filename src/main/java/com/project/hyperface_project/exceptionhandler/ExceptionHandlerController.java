package com.project.hyperface_project.exceptionhandler;

import com.project.hyperface_project.DTO.ApiResponse;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;


@RestControllerAdvice
public class ExceptionHandlerController {

//    @ExceptionHandler(value= NoHandlerFoundException.class)
//    public ResponseEntity<ApiResponse> throwError(NoHandlerFoundException e){
//        return new ResponseEntity<>(new ApiResponse(501,"Path not specified in controller",e.getMessage(),Instant.now()), HttpStatus.BAD_GATEWAY);
//    }
    @ExceptionHandler(value = InvalidFieldException.class)
    public ResponseEntity<ApiResponse> throwError(InvalidFieldException e){
        Map<String,Object> map=new HashMap<>();
        map.put("message",e.getMessage());
        return new ResponseEntity<>(new ApiResponse(502, "Invalid Field Exception",map, Instant.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiResponse> throwError(MethodArgumentNotValidException e){
        Map<String,Object> map=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->map.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(new ApiResponse(503,"Validation failed",map,Instant.now()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiResponse> invalidInput(NullPointerException e){
        Map<String,Object> map=new HashMap<>();
        map.put("message",e.getMessage());
        return new ResponseEntity<>(new ApiResponse(504,"Invalid Call",map,Instant.now()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> runtimeException(RuntimeException e){
        Map<String,Object> map=new HashMap<>();
        map.put("message",e.getMessage());
        return new ResponseEntity<>(new ApiResponse(505,"Invalid Call",map,Instant.now()),HttpStatus.BAD_REQUEST);
    }


}
