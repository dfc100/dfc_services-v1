package com.dfc.network.controller;

import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.service.FileStorageService;
import com.dfc.network.service.LoanAdminService;
import com.dfc.network.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;

@RestController
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private LoanAdminService loanAdminService;


    @PostMapping("/upload_file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id, @RequestParam("transaction_id") String transactionId, @RequestParam("transaction_date") String transactionDate) throws CustomMessageException{
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        if (fileDownloadUri != null) {
            if (null != userPaymentService.processPaymentScreenshotDetails(id, fileName, transactionId, transactionDate)) {
                return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
            } else{
                throw new CustomMessageException(HttpStatus.NOT_FOUND, "The record could not be found. Please contact Admin");
            }
        }
        return new ResponseEntity<>("File Upload Failed. Please contact Admin",HttpStatus.BAD_REQUEST);

    }



    /*@PostMapping("/uploadMultipleFiles")
    public List<UserPayment> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Integer id) throws CustomMessageException{
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, id))
                .collect(Collectors.toList());
    }*/

    @GetMapping("/download_file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("id") final Integer id, HttpServletRequest request) throws CustomMessageException{
        // Load file as Resource
        String fileName = userPaymentService.getFileName(id);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new CustomMessageException(HttpStatus.NOT_FOUND, "The requested file could not be found. Please contact Admin");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
