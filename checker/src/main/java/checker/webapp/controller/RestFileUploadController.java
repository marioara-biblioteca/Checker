package checker.webapp.controller;


import checker.entities.Result;
import checker.minio.services.MinIOService;
import checker.services.ResultService;
import checker.webapp.FileMetaData;
import checker.webapp.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1")
public class RestFileUploadController  extends PageController{
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    MinIOService minIOService;
    @Autowired
    ResultService resultService;
    @GetMapping("/upload-file")
    public String uploadFile(final Model model){
        return "uploadFile";
    }
    @PostMapping("/upload-file")
    @ResponseBody
    public FileMetaData uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model)  {
        FileMetaData data= fileStorageService.store(file);
        data.setUrl(fileDownloadUrl(data.getFileName(),"/api/v1/media/download/"));
        return data;
    }


    @GetMapping("/media/download/{fileName:.+}")
    public  ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        FileMetaData fileData= fileStorageService.getFile(fileName);
        return new ResponseEntity<Resource>(fileData.getResource(), HttpStatus.OK);
    }
}
