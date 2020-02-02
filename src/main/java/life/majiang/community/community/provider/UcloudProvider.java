package life.majiang.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.UUID;
@Service
public class UcloudProvider {

    @Value("${Ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${Ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${Ucloud.ufile.bucketName}")
    private String bucketName;

    @Value("${Ucloud.ufile.region}")
    private String region;

    @Value("${Ucloud.ufile.proxySuffix}")
    private String proxySuffix;

    @Value("${Ucloud.ufile.expiresDuration}")
    private int expiresDuration;

    public String upload(InputStream fileStream,String mimeType,String FileName){
        ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey, privateKey);
        ObjectConfig config = new ObjectConfig(region, proxySuffix);
        String generateFileName;
        String[] fileSpliter=FileName.split("\\.");
        if (fileSpliter.length>1){
            generateFileName= UUID.randomUUID().toString()+"."+fileSpliter[fileSpliter.length-1];
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        try {
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();

            if (response !=null && response.getRetCode()==0) {
                String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(generateFileName, bucketName,expiresDuration)
                        .createUrl();
                return url;
            } else {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }

        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

    }

}
