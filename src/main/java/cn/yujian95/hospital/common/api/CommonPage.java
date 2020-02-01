package cn.yujian95.hospital.common.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 通用分页记录返回对象
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/18
 */

@ApiModel(value = "CommonPage", description = "分页封装对象")
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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonPage)) {
            return false;
        }
        CommonPage<?> that = (CommonPage<?>) o;
        return getPageNum().equals(that.getPageNum()) &&
                getPageSize().equals(that.getPageSize()) &&
                getTotalPage().equals(that.getTotalPage()) &&
                getTotal().equals(that.getTotal()) &&
                getList().equals(that.getList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageNum(), getPageSize(), getTotalPage(), getTotal(), getList());
    }

    @Override
    public String toString() {
        return "CommonPage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
