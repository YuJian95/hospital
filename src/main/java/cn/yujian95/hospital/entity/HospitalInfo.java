package cn.yujian95.hospital.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class HospitalInfo implements Serializable {
    /**
     * 医院编号 从1001开始
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院编号 从1001开始")
    private Long id;

    /**
     * 医院名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院名称")
    private String name;

    /**
     * 医院电话
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院电话")
    private String phone;

    /**
     * 医院地址
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院地址")
    private String address;

    /**
     * 医院简介
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院简介")
    private String description;

    /**
     * 医院图片
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院图片")
    private String picture;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
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
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", description=").append(description);
        sb.append(", picture=").append(picture);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}