package cn.yujian95.hospital.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PowerPermission implements Serializable {
    /**
     * 权限编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限编号")
    private Long id;

    /**
     * 权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    /**
     * 权限名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限值")
    private String value;

    /**
     * 前端图标
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端图标")
    private String icon;

    /**
     * 前端路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端路径")
    private String url;

    /**
     * 父权限编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父权限编号")
    private Long pid;

    /**
     * 权限状态 1：开启，0：禁用
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限状态 1：开启，0：禁用")
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", value=").append(value);
        sb.append(", icon=").append(icon);
        sb.append(", url=").append(url);
        sb.append(", pid=").append(pid);
        sb.append(", status=").append(status);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}