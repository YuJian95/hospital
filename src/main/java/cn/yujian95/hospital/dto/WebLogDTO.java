package cn.yujian95.hospital.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

@ApiModel(value = "WebLogDTO", description = "请求日志对象")
@Data
public class WebLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String accountName;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;
}
