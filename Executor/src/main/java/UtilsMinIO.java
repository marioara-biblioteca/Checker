import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UtilsMinIO {
    private static String endPoint ="http://127.0.0.1:9000";
    private static String accessKey ="minio99";
    private static String secretKey="minio123"  ;
    private static String bucketName="checker";
    private static String localFileFolder="Executor/src/main/resources/temp_zip/" ;
    public static String getUrlsFromMinIO(String fileName) throws InvalidKeyException, IOException, NoSuchAlgorithmException {
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
                                    .object(fileName)
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
    public static void writeToMinIO(String fileName)
            throws InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException, IOException{
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endPoint)
                    .credentials(accessKey, secretKey).build();

            //final String localFileFolder = "/home/mihnea/0_Mihnea/Proiect_IP_Executor/Executor/src/main/resources/temp_zip/";
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String fileToUpload =  fileName;
            UploadObjectArgs args = UploadObjectArgs.builder().bucket(bucketName).object(fileName)
                    .filename(fileToUpload).build();
            minioClient.uploadObject(args);
            System.out.println(fileToUpload + " successfully uploaded to:" + "   container: " + bucketName+"   blob: " + fileName);

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
    public static void readFromMinIO(String fileName) throws InvalidKeyException,
            IllegalArgumentException, NoSuchAlgorithmException, IOException {
        //  final String localFileFolder = "/home/mihnea/0_Mihnea/Proiect_IP_Executor/Executor/src/main/resources/temp_zip/";

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

            Utils.unzip(downloadedFile, localFileFolder);
            //delete downloaded zip file to avoid errors on future uploads
            File f = new File(downloadedFile);
            f.delete();

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

    }
    public static void readFromMinIOLink(String minIOLink,String localFilename) throws IOException {
        //noi primim un link, nu un nume de fisier
        //trebuie sa luam contentul de la link-ul respectiv cu un GET
        localFilename=localFileFolder+"X_"+localFilename;
        Utils.runProcess("wsl.exe Executor/src/main/resources/curlMinIO.sh " +
minIOLink+ " " + localFilename);

        System.out.println("Downloaded file to " + localFilename);
        System.out.println();

        Utils.unzip(localFilename, localFileFolder);
        //delete downloaded zip file to avoid errors on future uploads
        File f = new File(localFilename);
        f.delete();

    }

}
