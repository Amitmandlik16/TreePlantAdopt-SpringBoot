package com.tree.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // FileName
        String name = file.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        // FullPath
        String filePath = path + File.separator + fileName;

        // Create Folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        // FileCopy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws IOException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        // db logic to return input stream
        return is;
    }

    @Override
    public boolean deleteImage(String path, String fileName) {
        String fullPath = path + File.separator + fileName;
        File file = new File(fullPath);

        // Check if the file exists and delete it
        if (file.exists()) {
            return file.delete();
        }
        
        // Return false if the file doesn't exist
        return false;
    }
}
