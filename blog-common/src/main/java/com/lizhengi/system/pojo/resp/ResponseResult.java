package com.lizhengi.system.pojo.resp;


import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 通用接口返回类
 *
 * <p>封装 API 响应结果，包含：
 * 1. code: 状态码，如 "200" 表示成功，"401" 未登录
 * 2. msg: 返回信息
 * 3. data: 返回业务数据（泛型）
 *
 * <p>支持链式调用，统一封装，方便前端解析。
 *
 * @param <T> 返回数据类型
 * @author lizhengi
 * @date 2025/11/20
 */
@Data
@Accessors(chain = true) // 支持链式调用，例如: rr.setCode("200").setMsg("成功")
public class ResponseResult<T> {

    /** 状态码 */
    private String code;

    /** 返回消息 */
    private String msg;

    /** 返回数据 */
    private T data;

    // =========================================
    //  ✅ 静态构建方法
    // =========================================

    /** 构建成功响应，无数据 */
    public static ResponseResult<Void> buildOkResponse() {
        return buildResponse(ResponseErrorCode.SUCCESS.getCode(),
                ResponseErrorCode.SUCCESS.getMsg(),
                null);
    }

    /** 构建成功响应，带数据 */
    public static <T> ResponseResult<T> buildOkResponse(T data) {
        return buildResponse(ResponseErrorCode.SUCCESS.getCode(),
                ResponseErrorCode.SUCCESS.getMsg(),
                data);
    }

    /** 构建失败响应，带自定义 code 和 msg */
    public static <T> ResponseResult<T> buildFailResponse(String code, String msg) {
        return buildResponse(code, msg, null);
    }

    /** 构建失败响应，带自定义 code、msg 和数据 */
    public static <T> ResponseResult<T> buildFailResponse(String code, String msg, T data) {
        return buildResponse(code, msg, data);
    }

    /** 根据 ResponseErrorCode 构建失败响应 */
    public static <T> ResponseResult<T> buildFailResponse(ResponseErrorCode errorCode) {
        return buildResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /** 内部通用构建方法 */
    private static <T> ResponseResult<T> buildResponse(String code, String msg, T data) {
        ResponseResult<T> rr = new ResponseResult<>();
        rr.code = code == null ? "" : code;
        rr.msg = msg == null ? "" : msg;
        rr.data = data;
        return rr;
    }

    // =========================================
    //  ✅ 便利方法
    // =========================================

    /** 判断是否成功 */
    public boolean isSuccess() {
        return ResponseErrorCode.SUCCESS.getCode().equals(this.code);
    }

    /** 判断是否失败 */
    public boolean isFail() {
        return !isSuccess();
    }
}
