package com.demo.common.consts;

/**
 * 公共返回码
 */
public enum CommonResultCode implements BaseEnum {
    SUCCESS("8000000000", "成功"),
    VERSION_UNMATCH("8000000001", "版本不匹配"),
    ILLEGAL_CALL("8000000002", "未授权的接口调用"),
    AUTH_CODE_ERROR("8000000003", "认证码错误"),
    TIMESTAMP_TIMEOUT("8000000004", "Timestamp时间戳已经过期"),
    ILLEGAL_IP_ADDRESS("8000000005", "非法的连接源IP地址"),
    ACCESS_ACCOUNT_TIMEOUT("8000000006", "接入账号已经失效"),
    REQUET_FORMAT_ERROR("8000000007", "请求消息格式错误"),
    PARA_VALIDATE_ERROR("8000000008", "参数校验失败"),
    RETURN_VALUE_ERROR("8000000009", "必选参数未返回"),
    DATAGRAM_OVER_SIZE("8000000010", "消息报文超大"),
    SYSTEM_ERROR("8000099999", "系统内部错误");

    private String code;

    private String msg;

    CommonResultCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

}
