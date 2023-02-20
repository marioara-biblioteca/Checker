
package checker.webapp.controller;


import checker.DTOs.ResultDTO;
import checker.entities.*;
import checker.exceptions.TaskNotFoundException;
import checker.minio.services.MinIOService;
//import checker.moji.service.MojiService;
import checker.request_models.AddTaskRequest;
import checker.services.*;
import checker.webapp.FileMetaData;
import checker.webapp.kafka.KafkaProducerConfig;
import checker.webapp.service.DefaultFileStorageService;

//import it.zielke.moji.MossException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Controller
public class FileUploadController extends PageController {

    @Autowired
    DefaultFileStorageService fileStorageService;
    @Autowired
    MinIOService minIOService;
    @Autowired
    ResultService resultService;
    @Autowired
    TestService testService;
    @Autowired
    UserService userService;
    @Autowired
     SubjectService subjectService;
//    @Autowired
//    MojiService mojiService;
    @Autowired
    TaskService taskService;
    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    @GetMapping("/upload-file/{userId}")
    public String studentGetFilePage(final Model model, @PathVariable Long userId ) {
        User user =userService.getUserById(userId);

        if(user.getRole().getRoleType().name().equals("STUDENT"))
            return "uploadFile";
        else return "uploadFileForProfessor";

    }


    @PostMapping(value="/upload-file/{userId}/task/{taskId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String studentFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,
                                    Model model,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId) throws NoSuchAlgorithmException, IOException, InvalidKeyException {

        User user = userService.getUserById(userId);

        FileMetaData data = fileStorageService.store(file);
        data.setUrl(fileDownloadUrl(data.getFileName(), "/media/download/"));

        model.addAttribute("uploadedFile", data);
        model.addAttribute("userId",userId);
        model.addAttribute("taskId",taskId);

        // fileStorageService.storeForMoji(file);

        ZipFile zipFile = new ZipFile("./checker/uploads/"+data.getFileName());
        Enumeration zipEntries = zipFile.entries();
        String extension=zipFile.stream().map(ZipEntry::getName).collect(Collectors.toList()).get(0).split("\\.",2)[1];

        List<String> homeworkUrl = uploadFileForStudent(data.getFileName(), user.getUserId(),taskId);

       if(extension!="") {
            kafkaProducerConfig.config(
                    extension + "-topic",
                    user.getEmail(),
                    homeworkUrl.get(0),
                    homeworkUrl.get(1)
            );

            return "success";
       }else throw new ZipException();

    }
    @PostMapping(value="/upload-file/{userId}" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String professorFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @PathVariable Long userId) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        User user = userService.getUserById(userId);

        FileMetaData data = fileStorageService.store(file);
        data.setUrl(fileDownloadUrl(data.getFileName(), "/media/download/"));

        model.addAttribute("uploadedFile", data);
        model.addAttribute("userId",userId);
        String testUrl=uploadTestForProfessor(data.getFileName());
        Test test=new Test(testUrl);
        testService.addTest(test);
        model.addAttribute("testLink",testUrl);

        return "uploadFileForProfessor";
    }

    @GetMapping("/add-task")
    public String addTask(final Model model){
        return "addTasks";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute(name="addTaskForm") AddTaskRequest taskRequest, Model m){
        Task task=new Task();
        Subject subject=subjectService.getSubjectByName(taskRequest.getSubjectName());
        Test test = testService.getTestByLink(taskRequest.getTestLink());

        task.setSubject(subject);
        task.setTest(test);
        task.setDeadLine(taskRequest.getDeadLine());
        task.setRequirement(taskRequest.getRequirement());

        subjectService.addTaskToSubject(subject.getName(),task);
        testService.assignTestToTask(task,test);

        taskService.addTask(task);
        return "success";
    }



    private List<String> uploadFileForStudent(String fileName, Long userId,Long taskId) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        String homeworkUrl;
        FileMetaData fileData = fileStorageService.getFile(fileName);

//        try {
//            String results=mojiService.checkWithMoji();
//            System.out.println("Results available at " + results.toString());
//
//        } catch (MossException | IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            homeworkUrl = minIOService.GetUrlsFromMinIO(fileName);
            minIOService.WriteToMinIO(fileName);
        } catch (InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        List<String> results = new ArrayList<>();

        ResultDTO resultDTO = new ResultDTO(homeworkUrl, taskId);
        User user = userService.getUserById(userId);
        Task task = userService.getUsersTask(user.getUserId(), resultDTO.getTaskId());
        //Task task=taskService.getTask(taskId);
        results.add(homeworkUrl);
        results.add(task.getTest().getLink());

        Result result = new Result(resultDTO.getSourceLink());
        resultService.addResultToUser(result, user, task);

        deleteFileFromServer(fileName);

        return results;

    }

    private String uploadTestForProfessor(String fileName){

        String testUrl;
        FileMetaData fileData = fileStorageService.getFile(fileName);
        try {
            testUrl = minIOService.GetUrlsFromMinIO(fileName);
            minIOService.WriteToMinIO(fileName);
        } catch (InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        deleteFileFromServer(fileName);
        return testUrl;
    }

    private void deleteFileFromServer(String fileName) {
        fileName = "./checker/uploads/" + fileName;
        File f = new File(fileName);
        if (f.delete())
            System.out.println("Succesfully deleted file from server after uploading to MinIO");
        else System.out.println("Couldnt delete file");
    }
}