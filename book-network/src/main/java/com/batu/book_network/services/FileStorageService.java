package com.batu.book_network.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile sourceFile,
                           @NonNull Long userId) {
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(MultipartFile sourceFile,
                              String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFile = new File(finalUploadPath);
        if(!targetFile.exists()) {
            boolean folderCreated = targetFile.mkdirs();
            if(!folderCreated){
                log.warn("Target folder can't be created!");
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        // targetUploadPath = ./uploads/users/userId/currenTime.fileExtension
        final String targetUploadPath = finalUploadPath + separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetUploadPath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to {}", targetUploadPath);
            return targetUploadPath;
        } catch (IOException e) {
            log.error("File was not saved!", e);
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        // If the file name is null or empty
        if(fileName == null || fileName.isEmpty())
            return "";
        int lastDotIndex = fileName.lastIndexOf(".");
        // If no extension in the fileName
        if(lastDotIndex == -1)
            return "";
        // .JPG -> .jpg (In case of take caution.).
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
