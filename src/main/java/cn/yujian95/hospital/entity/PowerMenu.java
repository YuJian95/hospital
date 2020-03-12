package cn.yujian95.hospital.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PowerMenu implements Serializable {
    /**
     * 菜单编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单编号")
    private Long id;

    /**
     * 父级菜单
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentId;

    /**
     * 菜单名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /**
     * 菜单级数
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单级数")
    private Integer level;

    /**
     * 菜单排序
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    /**
     * 前端路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端路径")
    private String name;

    /**
     * 前端图标
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端图标")
    private String icon;

    /**
     * 前端隐藏 0：隐藏，1：显示
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端隐藏 0：隐藏，1：显示")
    private Integer hidden;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
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
        sb.append(", parentId=").append(parentId);
        sb.append(", title=").append(title);
        sb.append(", level=").append(level);
        sb.append(", sort=").append(sort);
        sb.append(", name=").append(name);
        sb.append(", icon=").append(icon);
        sb.append(", hidden=").append(hidden);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}