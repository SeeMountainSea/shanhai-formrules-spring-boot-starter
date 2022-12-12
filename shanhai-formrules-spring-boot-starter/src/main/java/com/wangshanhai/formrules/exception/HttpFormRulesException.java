package com.wangshanhai.formrules.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class HttpFormRulesException extends RuntimeException {
    private Integer code=500;
    private String message;
    public HttpFormRulesException(String message) {
        this.message = message;
    }
}