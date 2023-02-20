package checker.webapp.service;

import checker.webapp.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {
    FileMetaData store(MultipartFile file) ;
    List<Path> getAllFiles();
    FileMetaData getFile(String fileName) ;
}
