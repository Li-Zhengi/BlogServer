package com.lizhengi.system.pojo.resp;


import lombok.Data;

/**
 * @author lizhengi
 * @date 2025/11/20 09:25
 */
@Data
public class ResponseResult<T> {


    private String code;

    private String msg;

    private T data;

    public static ResponseResult<?> buildOkResponse() {
        return buildResponse(ResponseErrorCode.SUCCESS.getCode(), ResponseErrorCode.SUCCESS.getMsg(), (Object)null);
    }

    public static <T> ResponseResult<T> buildOkResponse(T data) {
        return buildResponse(ResponseErrorCode.SUCCESS.getCode(), ResponseErrorCode.SUCCESS.getMsg(), data);
    }


    private static <T> ResponseResult<T> buildResponse(String code, String msg, T data) {
        ResponseResult<T> rr = new ResponseResult();
        rr.code = code == null ? "" : code;
        rr.msg = msg == null ? "" : msg;
        rr.data = data;
        return rr;
    }

}
