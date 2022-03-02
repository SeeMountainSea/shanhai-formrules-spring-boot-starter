package com.wangshanhai.formrules.utils;

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
public  class HttpBizRulesException extends RuntimeException {
    private String code="500";
    private String message;

    public HttpBizRulesException(String message) {
        this.message = message;
    }
}