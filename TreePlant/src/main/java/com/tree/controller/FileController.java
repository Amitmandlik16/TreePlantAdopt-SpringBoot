package com.tree.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tree.service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController()
public class FileController {
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	@Value("${project.image.treeowners}")
	private String treeownerspath;

	@PostMapping("/upload")
	ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
		String fileName = null;
		try {
			fileName = this.fileService.uploadImage(path, image);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "Image is not Uploaded sucessfully!!"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new FileResponse(fileName, "Image is Uploaded sucessfully!!"), HttpStatus.OK);
	}

	// method to serve files
//	@GetMapping("/images/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse)
//			throws IOException {
//		InputStream resource = this.fileService.getResource(path, imageName);
//		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(resource, httpServletResponse.getOutputStream());
//	}
	
	@GetMapping("/images/{imageName}")
	public void viewImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
	    Path imagePath = Path.of(treeownerspath, imageName);
	    
	    // Get content type dynamically
	    String contentType = Files.probeContentType(imagePath);
	    if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	    
	    response.setContentType(contentType);  // Ensure correct content type
	    response.setHeader("Content-Disposition", "inline"); // Ensure image is displayed in browser
	    
	    try (InputStream resource = fileService.getResource(treeownerspath, imageName)) {
	        if (resource == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
	            return;
	        }
	        StreamUtils.copy(resource, response.getOutputStream());
	    } catch (IOException e) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading image");
	    }
	}


	// localhots:8080/images/abc.png
}
