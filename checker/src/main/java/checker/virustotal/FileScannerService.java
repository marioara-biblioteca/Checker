//package checker.virustotal;
//
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import com.kanishka.virustotal.dto.FileScanReport;
//import com.kanishka.virustotal.dto.ScanInfo;
//import com.kanishka.virustotal.dto.VirusScanInfo;
//import com.kanishka.virustotal.exception.APIKeyNotFoundException;
//import com.kanishka.virustotal.exception.QuotaExceededException;
//import com.kanishka.virustotal.exception.UnauthorizedAccessException;
//import com.kanishka.virustotalv2.VirusTotalConfig;
//import com.kanishka.virustotalv2.VirustotalPublicV2;
//import com.kanishka.virustotalv2.VirustotalPublicV2Impl;
//
//import java.util.*;
//
//
//
//@Service
//public class FileScannerService {
//    public  String scanFile(String pathname) {
//        try {
//            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("ee7a8fa7ab98b3ae4e9677e5c3e936831db3bce50662f1adfd4350be354d035f");
//            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
//
//            ScanInfo scanInformation = virusTotalRef.scanFile(new File(pathname));
//
//            System.out.println("___SCAN INFORMATION___");
//            System.out.println("MD5 :\t" + scanInformation.getMd5());
//            System.out.println("Perma Link :\t" + scanInformation.getPermalink());
//            System.out.println("Resource :\t" + scanInformation.getResource());
//            System.out.println("Scan Date :\t" + scanInformation.getScanDate());
//            System.out.println("Scan Id :\t" + scanInformation.getScanId());
//            System.out.println("SHA1 :\t" + scanInformation.getSha1());
//            System.out.println("SHA256 :\t" + scanInformation.getSha256());
//            System.out.println("Verbose Msg :\t" + scanInformation.getVerboseMessage());
//            System.out.println("Response Code :\t" + scanInformation.getResponseCode());
//            System.out.println("done.");
//            return scanInformation.getResource();
//        } catch (APIKeyNotFoundException ex) {
//            System.err.println("Something Bad Happened! " + ex.getMessage());
//        } catch (QuotaExceededException ex) {
//            System.err.println("Something Bad Happened! " + ex.getMessage());
//        } catch (UnauthorizedAccessException ex) {
//            System.err.println("Something Bad Happened! " + ex.getMessage());
//        } catch (IOException ex) {
//            System.err.println("Something Bad Happened! " + ex.getMessage());
//        }
//        return null;
//    }
//    public  boolean getFileScanReport(String resource) {
//        try {
//            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("ee7a8fa7ab98b3ae4e9677e5c3e936831db3bce50662f1adfd4350be354d035f");
//            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
//
//            FileScanReport report = virusTotalRef.getScanReport(resource);
//            if(report.getTotal()!=null) {
//                System.out.println("MD5 :\t" + report.getMd5());
//                System.out.println("Perma link :\t" + report.getPermalink());
//                System.out.println("Resourve :\t" + report.getResource());
//                System.out.println("Scan Date :\t" + report.getScanDate());
//                System.out.println("Scan Id :\t" + report.getScanId());
//                System.out.println("SHA1 :\t" + report.getSha1());
//                System.out.println("SHA256 :\t" + report.getSha256());
//                System.out.println("Verbose Msg :\t" + report.getVerboseMessage());
//                System.out.println("Response Code :\t" + report.getResponseCode());
//                System.out.println("Positives :\t" + report.getPositives());
//                System.out.println("Total :\t" + report.getTotal());
//
//                Map<String, VirusScanInfo> scans = report.getScans();
//                for (String key : scans.keySet()) {
//                    VirusScanInfo virusInfo = scans.get(key);
//                    System.out.println("Scanner : " + key);
//                    System.out.println("\t\t Resut : " + virusInfo.getResult());
//                    System.out.println("\t\t Update : " + virusInfo.getUpdate());
//                    System.out.println("\t\t Version :" + virusInfo.getVersion());
//                }
//                return true;
//            }
//
//        } catch (APIKeyNotFoundException ex) {
//            System.err.println("API Key not found! " + ex.getMessage());
//        } catch (UnsupportedEncodingException ex) {
//            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
//        } catch (UnauthorizedAccessException ex) {
//            System.err.println("Invalid API Key " + ex.getMessage());
//        } catch (Exception ex) {
//            System.err.println("Something Bad Happened! " + ex.getMessage());
//        }
//        return false;
//    }
//}
