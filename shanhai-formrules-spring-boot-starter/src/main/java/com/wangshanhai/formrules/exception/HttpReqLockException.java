package com.wangshanhai.formrules.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code HttpReqLockException} class
 *
 * @author Fly.Sky
 * @since 2022/12/12 11:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpReqLockException extends RuntimeException{
    private Integer code=500;
    private String message;

    public HttpReqLockException(String message) {
        this.message = message;
    }
}
