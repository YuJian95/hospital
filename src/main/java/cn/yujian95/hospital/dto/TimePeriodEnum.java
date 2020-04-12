package cn.yujian95.hospital.dto;

import lombok.Getter;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/12
 */

@Getter
public enum TimePeriodEnum {

    /**
     * 上午就诊时间段
     * 1： 8点半~9点，2： 9点~9点半，3： 9点半~10点，4： 10点~10点半，
     * 5: 10点半~11点，6： 11点~11点半，7： 11点半~12点
     */
    AM(1, 1, 7),

    /**
     * 下午就诊时间段
     * 8：2点~2点半，9： 2点半~3点，10： 3点~3点半，11： 3点半~4点，
     * 12： 4点~4点半，13： 4点半~5点，14： 5点~5点半，15：5点半~6点
     */
    PM(2, 8, 15);


    private Integer time;

    private Integer start;

    private Integer end;

    TimePeriodEnum(Integer time, Integer start, Integer end) {
        this.time = time;
        this.start = start;
        this.end = end;
    }
}
