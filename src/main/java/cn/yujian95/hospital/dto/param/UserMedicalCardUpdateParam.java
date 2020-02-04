package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */
@ApiModel(value = "UserMedicalCardUpdateParam", description = "用户医疗卡信息更新参数")
@Data
public class UserMedicalCardUpdateParam implements Serializable {

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
     * 手机号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 性别 男：1，女：2
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "性别 男：1，女：2")
    private Integer gender;
}
