package cn.yujian95.little.common.exception;

import cn.yujian95.little.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 *
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/15
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}