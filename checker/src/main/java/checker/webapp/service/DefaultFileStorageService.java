
package checker.webapp.service;

//import checker.virustotal.FileScannerService;
import checker.webapp.FileMetaData;
import checker.webapp.UploadFileProperties;

import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service("fileStorageService")
public class DefaultFileStorageService implements FileStorageService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileStorageService.class);
    @Autowired
    UploadFileProperties uploadFileProperties;
//    @Autowired
//    FileScannerService fileScannerService;
    @Override
    public FileMetaData store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // we can add additional file validation to discard invalid files
            Path uploadDir = getUploadDirLocation().resolve(fileName);//
        //copy the file to the upload directory,it will replace any file with same name.
            Files.copy(file.getInputStream(), uploadDir, StandardCopyOption.REPLACE_EXISTING);
            Thread.sleep(2500);
            /*String resource=fileScannerService.scanFile(uploadDir.toString());
            if(fileScannerService.getFileScanReport(resource)){
                System.out.println("VIRUS alert");
                return null;
            }//else continue
*/

            FileMetaData fileData = new FileMetaData();
            fileData.setFileName(fileName);
            fileData.setSize(file.getSize());
            fileData.setMime(file.getContentType());

            return fileData;

        } catch (IOException e) {
            LOG.error("unable to cpy file to the target location {}", e);
           return null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public FileMetaData storeForMoji(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            //
            Path uploadDir =Paths.get("/home/osboxes/nou/checker-c114e/checker/moji/students/").toAbsolutePath().normalize().resolve(fileName);
            Files.copy(file.getInputStream(), uploadDir, StandardCopyOption.REPLACE_EXISTING);

            uploadDir =Paths.get("/home/osboxes/nou/checker-c114e/checker/moji/base/").toAbsolutePath().normalize().resolve(fileName);
            Files.copy(file.getInputStream(), uploadDir, StandardCopyOption.REPLACE_EXISTING);

            FileMetaData fileData = new FileMetaData();
            fileData.setFileName(fileName);
            fileData.setSize(file.getSize());
            fileData.setMime(file.getContentType());
            return fileData;

        } catch (IOException e) {
            LOG.error("unable to cpy file to the target location {}", e);
            return null;
        }
    }



    @Override
    public List<Path> getAllFiles() {
        return null;
    }

    @Override
    public FileMetaData getFile(String fileName) {
        Path path = getUploadDirLocation().resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists()){
                Metadata metadata = getFileMetaDataInfo(resource);
                FileMetaData fileMetaData = new FileMetaData();
                fileMetaData.setResource(resource);
                fileMetaData.setFileName(fileName);
                fileMetaData.setSize(metadata.size());
                fileMetaData.setMime(metadata.get(Metadata.CONTENT_TYPE));
                return fileMetaData;
            }
            else{
                System.out.println("File not found");
                return null;
            }
        } catch (MalformedURLException e) {
            System.out.println("not able to find file");
            return null;
        }
    }
    private Path getUploadDirLocation() {
        return Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
    }
    private Metadata getFileMetaDataInfo(Resource resource){
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = parser.getDetector();
        Metadata metadata = new Metadata();
        try {
            metadata.set(Metadata.RESOURCE_NAME_KEY, resource.getFile().getName());
            TikaInputStream stream = TikaInputStream.get(resource.getInputStream());
            MediaType mediaType = detector.detect(stream,metadata);
            metadata.set(Metadata.CONTENT_TYPE, mediaType.toString());
        } catch (IOException e) {
            e.printStackTrace();

            metadata.set(Metadata.CONTENT_TYPE, "application/octet-stream");

        }
        return metadata;
    }
}