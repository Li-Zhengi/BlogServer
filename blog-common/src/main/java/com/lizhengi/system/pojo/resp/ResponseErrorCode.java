package com.lizhengi.system.pojo.resp;


import lombok.Getter;

/**
 * @author lizhengi
 * @date 2025/11/20 09:27
 */
@Getter
public enum ResponseErrorCode {

    SUCCESS("0", "成功"),
    INTERNAL_SERVER_ERROR("-1", "系统繁忙，请稍后再试"),
    UNDEFINED_ERROR("-1", "未定义的系统异常"),
    VALIDATION_ERROR("-1", "参数有误，请检查参数");;

    ResponseErrorCode(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    private String code;
    private String msg;

}
