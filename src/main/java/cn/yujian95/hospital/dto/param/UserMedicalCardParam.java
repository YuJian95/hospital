package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户医疗卡信息参数
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@ApiModel(value = "UserMedicalCardParam", description = "用户医疗卡信息参数")
@Data
public class UserMedicalCardParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他")
    private Integer type;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别 男：1，女：2
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "性别 男：1，女：2")
    private Integer gender;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 证件号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "证件号")
    private String identificationNumber;

    /**
     * 出生日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "出生日期")
    private Date birthDate;
}
