package cn.yujian95.hospital.component;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@Component
public class WxComponent {

    /**
     * 微信小程序登录,session请求路径
     */
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?";

    /**
     * 默认登录类型
     */
    private static final String GRANT_TYPE = "authorization_code";

    /**
     * 小程序appid
     */
    @Value("${wx.appId}")
    private String appId;

    /**
     * 小程序会话密钥
     */
    @Value("${wx.appSecret}")
    private String appSecret;

    /**
     * 获取openid和Session
     *
     * @param code 会话码
     * @return 结果
     */
    private String getSessionKeyOrOpenId(String code) {
        String params = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=" + GRANT_TYPE;

        // 发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识

        return HttpUtil.get(REQUEST_URL + params);
    }

    public String getOpenId(String code) {

        JSONObject jsonObject = JSONObject.parseObject(getSessionKeyOrOpenId(code));

        return jsonObject.getString("openid");
    }
}