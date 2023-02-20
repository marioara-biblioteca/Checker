
package checker.minio.services;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import java.util.*;
import java.io.IOException;
import java.security.*;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
@Service
public class MinIOService {
    //@Value("${spring.minio.end-point}")
    private static String endPoint ="http://127.0.0.1:9000";
   // @Value("${spring.minio.access-key}")
    private static String accessKey ="minio99";
   // @Value("${spring.minio.secret-key}")
    private static String secretKey="minio123"  ;
   // @Value("${spring.minio.bucket-name}")
    private static String bucketName="checker";
   // @Value("${spring.minio.local-file-folder}")
    private static String localFileFolder="./checker/uploads/" ;
    public void WriteToMinIO(String fileName)
            throws InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException, IOException{
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endPoint)
                    .credentials(accessKey, secretKey).build();

            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String fileToUpload = localFileFolder + fileName;
            UploadObjectArgs args = UploadObjectArgs.builder().bucket(bucketName).object(fileName)
                    .filename(fileToUpload).build();
            minioClient.uploadObject(args);
            System.out.println(fileToUpload + " successfully uploaded to:" + "   container: " + bucketName+"   blob: " + fileName);

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
    public String GetUrlsFromMinIO(String file) throws InvalidKeyException,IOException,NoSuchAlgorithmException{
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");
        MinioClient minioClient = MinioClient.builder().endpoint(endPoint)
                .credentials(accessKey, secretKey).build();
        try {
            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(file)
                                    .expiry(2, TimeUnit.HOURS)
                                    .extraQueryParams(reqParams)
                                    .build());
            return url;
        }
        catch (MinioException e){
            System.out.println("Error occurred: " + e);
        }
        return null;
    }
    public void ReadFromMinIO(String fileName)
            throws InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException, IOException {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endPoint)
                    .credentials(accessKey, secretKey).build();
            String downloadedFile = localFileFolder + "D_" + fileName;
            DownloadObjectArgs args = DownloadObjectArgs.builder().bucket(bucketName).object(fileName)
                    .filename(downloadedFile).build();
            minioClient.downloadObject(args);

            System.out.println("Downloaded file to ");
            System.out.println(" " + downloadedFile);
            System.out.println();
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

}