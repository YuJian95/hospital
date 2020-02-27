package cn.yujian95.hospital.component;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 七牛云云存储
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/6
 */

@Component
public class QiniuComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuComponent.class);

    @Value("${qiniu.accesskey}")
    private String accessKey;

    @Value("${qiniu.secretkey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.url}")
    private String url;

    public String uploadFile(MultipartFile file) {

        //构造一个带指定 Region对象的配置类
        Configuration cfg = new Configuration(Region.huanan());

        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        // 默认不指定 key的情况下，以文件内容的 hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {

            Response response = uploadManager.put(file.getBytes(), key, upToken);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson()
                    .fromJson(response.bodyString(), DefaultPutRet.class);

            LOGGER.info(putRet.key);
            LOGGER.info(putRet.hash);

            return url + putRet.hash;

        } catch (QiniuException ex) {
            Response r = ex.response;

            LOGGER.error(r.toString());
            try {
                LOGGER.error(r.bodyString());

            } catch (QiniuException ex2) {
                LOGGER.error(ex2.response.toString());
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return null;
    }
}
