package cn.yujian95.hospital.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PowerAccount implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 登录账号 唯一
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "登录账号 唯一")
    private String name;

    /**
     * 登录密码 使用md5加密
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "登录密码 使用md5加密")
    private String password;

    /**
     * 账号状态 1：正常，0：锁定
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "账号状态 1：正常，0：锁定")
    private Integer status;

    /**
     * 最后登录时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}