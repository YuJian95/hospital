package cn.yujian95.hospital.component;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

@Component
public class AliSendSmsComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger(AliSendSmsComponent.class);

    @Value("${aliSms.regionId}")
    private String regionId;

    @Value("${aliSms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliSms.accessSecret}")
    private String accessSecret;

    @Value("${aliSms.domain}")
    private String domain;

    @Value("${aliSms.version}")
    private String version;

    @Value("${aliSms.signName}")
    private String signName;

    @Value("${aliSms.loginTemplate}")
    private String loginTemplate;

    /**
     * 发送模版短信，到指定手机号
     *
     * @param phoneNumber   手机好
     * @param templateCode  短信模板
     * @param templateParam 模板参数
     * @return 是否成功
     */
    private boolean sendMessage(String phoneNumber, String templateCode, String templateParam) {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.toString());
            JSONObject jsonObject = JSONObject.parseObject(response.getData());

            String code = jsonObject.getString("Code");

            if ("OK".equals(code)) {
                LOGGER.info("send message success, phone: {} , templateCode: {} , param: {} .", phoneNumber, templateCode, templateParam);
                return true;
            } else {
                LOGGER.error("send message fails, phone: {} , code: {} ", phoneNumber, code);
            }

        } catch (ClientException e) {
            LOGGER.error("send message fail, error: {}", e.getMessage());
        }

        return false;
    }

    /**
     * 发送注册验证码
     *
     * @param phoneNumber 手机号码
     * @param code        验证码
     * @return 是否成功
     */
    public boolean sendRegisterCode(String phoneNumber, String code) {

        // 短信模板参数
        String param = "{\"code\":\"" + code + "\"}";

        return sendMessage(phoneNumber, loginTemplate, param);
    }

}
