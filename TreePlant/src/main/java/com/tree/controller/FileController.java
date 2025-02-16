package com.tree.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tree.service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Value("${project.image.treeowners}")
    private String treeownersPath;

    @Value("${project.image.tree}")
    private String treePath;

    @Value("${project.image.treescan}")
    private String treeScanPath;

    // Mapping for different image categories
    private final Map<String, String> imagePaths = Map.of(
        "treeowners", treeownersPath,
        "tree", treePath,
        "treescan", treeScanPath
    );

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
        try {
            String fileName = fileService.uploadImage(path, image);
            return ResponseEntity.ok(new FileResponse(fileName, "Image uploaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new FileResponse(null, "Image upload failed!"));
        }
    }

    @GetMapping("/{category}/images/{imageName}")
    public void viewImage(@PathVariable String category, @PathVariable String imageName, HttpServletResponse response) throws IOException {
        String folderPath = imagePaths.get(category);
        
        if (folderPath == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image category");
            return;
        }

        Path imagePath = Path.of(folderPath, imageName);
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline");

        try (InputStream resource = fileService.getResource(folderPath, imageName)) {
            if (resource == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading image");
        }
    }
}
