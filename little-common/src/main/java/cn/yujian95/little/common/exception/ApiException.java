package cn.yujian95.little.common.exception;

import cn.yujian95.little.common.api.IErrorCode;

/**
 * 自定义API异常
 *
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/15
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}