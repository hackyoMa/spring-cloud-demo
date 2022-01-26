package io.github.hackyoma.springclouddemo.gatewayservice.entity;

import java.io.Serializable;

/**
 * Result
 *
 * @author hackyo
 * @version 2022/1/24
 */
public class Result implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
