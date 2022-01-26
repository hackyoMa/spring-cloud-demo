package io.github.hackyoma.springclouddemo.servicecommon.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler
 *
 * @author hakcyo
 * @version 2022/1/25
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONObject> exceptionHandler(Exception e) {
        JSONObject info = new JSONObject()
                .fluentPut("message", e.getMessage());
        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
