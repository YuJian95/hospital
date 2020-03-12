package cn.yujian95.hospital.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PowerRole implements Serializable {
    /**
     * 角色编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色编号")
    private Long id;

    /**
     * 英文名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "英文名称")
    private String name;

    /**
     * 中文名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "中文名称")
    private String chineseName;

    /**
     * 用户数目
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户数目")
    private Integer adminCount;

    /**
     * 排序 越小越靠前
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "排序 越小越靠前")
    private Integer sort;

    /**
     * 角色状态 1：启用，0：禁用
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色状态 1：启用，0：禁用")
    private Integer status;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", chineseName=").append(chineseName);
        sb.append(", adminCount=").append(adminCount);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}