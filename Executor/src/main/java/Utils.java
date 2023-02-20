import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {

    private static String localFileFolder="Executor/src/main/temp_zip" ;
    private static  int counter=0;
    public static String evaluate(long testNmb, String script){
        String localFolder="Executor/src/main/resources/";
        try {
            File myRes = new File(localFolder+"temp_zip/results");
            if (myRes.createNewFile()) {
                System.out.println("File created: " + myRes.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(localFolder+"temp_zip/results");
            int counter=0;
            for (int i = 1; i <= testNmb; i++) {
                System.out.println("**********");
                Utils.runProcess("wsl.exe "+localFolder+ script + " input" + i + " output" + i +
                        " " + Integer.toString(i));
                System.out.println("**********");

                File myObj = new File(localFolder+"temp_zip/isDiff");
                Scanner myReader = new Scanner(myObj);
                String data = myReader.nextLine();
                if (data.equals("0")) {
                    myWriter.append("Test " + i + ".........." + "Passed\n");
                    counter++;
                } else {
                    myWriter.append("Test " + i + ".........." + "Failed\n");
                }

                myWriter.flush();
            }
            myWriter.append("Score.........."+counter+"/"+testNmb+"\n");
            myWriter.flush();
            return localFolder+"temp_zip/results";

        } catch (Exception e) {
            e.printStackTrace();
        }
         return null;
    }
    public static long countTests(String path){
        try (Stream<Path> files = Files.list(Paths.get(path))) {
            long count = files.count();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static void cleanupFolder(String dir) {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    cleanupFolder(f.getAbsolutePath());
                    f.delete();
                } else {
                    f.delete();
                }
            }
        }
    }
    public static void printLines(String cmd, InputStream ins) {
        try{
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runProcess(String command){
        try{
            Process pro = Runtime.getRuntime().exec(command);
            printLines(command + " stdout:", pro.getInputStream());
            printLines(command + " stderr:", pro.getErrorStream());
            pro.waitFor();
            System.out.println(command + " exitValue() " + pro.exitValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
    public static void unzip(String fileZip, String destDirStr) throws IOException {
        byte[] buffer = new byte[1024];
        File destDir = new File(destDirStr);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }


}
