package cn.yujian95.hospital.dto;

import lombok.Getter;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/10
 */

@Getter
public enum AppointmentEnum {


    /**
     * 未开始
     */
    WAITING(0),
    /**
     * 失约
     */
    MISSING(1),
    /**
     * 取消
     */
    CANCEL(2),
    /**
     * 已完成
     */
    FINISH(3);

    /**
     * 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     */
    private Integer status;

    AppointmentEnum(Integer status) {
        this.status = status;
    }
}
