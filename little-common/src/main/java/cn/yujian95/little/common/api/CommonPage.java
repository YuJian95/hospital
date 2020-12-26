package cn.yujian95.little.common.api;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 通用分页记录返回对象
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/18
 */
@Data
@ApiModel("分页封装对象")
public class CommonPage<T> implements Serializable {
    /**
     * 页号
     */
    @ApiModelProperty("第几页")
    private Integer pageNum;
    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Integer pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private Integer totalPage;
    /**
     * 结果总数
     */
    @ApiModelProperty("结果总数")
    private Long total;
    /**
     * 结果数据
     */
    @ApiModelProperty("结果列表")
    private List<T> list;

    /**
     * 将 PageHelper分页后的 list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();

        PageInfo<T> pageInfo = new PageInfo<T>(list);

        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());

        return result;
    }

    /**
     * 将PageInfo里面的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(PageInfo<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将 MyBatis Plus 分页结果转化为通用结果
     */
    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();

        result.setPageNum(Convert.toInt(pageResult.getCurrent()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(Convert.toInt(pageResult.getTotal() / pageResult.getSize() + 1));
        result.setList(pageResult.getRecords());

        return result;
    }
}
