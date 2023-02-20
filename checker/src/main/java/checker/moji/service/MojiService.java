//package checker.moji.service;
//
//import it.zielke.moji.MossException;
//import it.zielke.moji.SocketClient;
//import org.apache.commons.io.FileUtils;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.net.URL;
//import java.util.*;
//@Service
//public class MojiService {
//    private final Long userId=632200311L;
//    private final String compilingLanguage="c";
//    private final String basePath="/home/osboxes/nou/checker-c114e/checker/moji/base";
//    private final String studentsPath="/home/osboxes/nou/checker-c114e/checker/moji/students";
//    public String checkWithMoji() throws MossException, IOException {
//        // a list of students' source code files located in the prepared
//        // directory.
//        Collection<File> files = FileUtils.listFiles(new File(
//                this.studentsPath), new String[] { "c" }, true);
//
//        // a list of base files that was given to the students for this
//        // assignment.
//        Collection<File> baseFiles = FileUtils.listFiles(new File(
//                this.basePath), new String[] { "c" }, true);
//
//        // get a new socket client to communicate with the MOSS server
//        // and set its parameters.
//        SocketClient socketClient = new SocketClient();
//
//        // set your MOSS user ID
//        socketClient.setUserID(this.userId.toString());
//        // socketClient.setOpt...
//
//        // set the programming language of all student source codes
//        socketClient.setLanguage(this.compilingLanguage);
//
//        // initialize connection and send parameters
//        socketClient.run();
//
//        // upload all base files
//        for (File f : baseFiles) {
//            socketClient.uploadBaseFile(f);
//        }
//
//        // upload all source files of students
//        for (File f : files) {
//            socketClient.uploadFile(f);
//        }
//
//        // finished uploading, tell server to check files
//        socketClient.sendQuery();
//
//        // get URL with MOSS results and do something with it
//        URL results = socketClient.getResultURL();
//        return results.toString();
//    }
//}
