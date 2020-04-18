package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.HospitalClinic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/18
 */

@Data
@ApiModel(value = "VisitUserListDTO", description = "预约用户列表")
public class VisitUserListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 诊室地址
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "诊室地址")
    private String address;

    @ApiModelProperty("预约用户信息列表")
    private List<VisitUserInfoDTO> userInfoList;
}
