package com.shanhai.examples.config;

import com.wangshanhai.formrules.utils.HttpFormRulesException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(-Integer.MAX_VALUE)
public class ErrorHandlerConfig {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> errorHandler(Exception e) {
        Map<String,Object> resp=new HashMap<>();
        if(e instanceof HttpFormRulesException){
            resp.put("code",((HttpFormRulesException) e).getCode());
            resp.put("message",e.getMessage());
        } else{
            resp.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.put("message",HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application","json", Charset.forName("utf-8"));
        headers.setContentType(mediaType);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).headers(headers).body(resp);
    }
}
