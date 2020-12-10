package com.board.notification.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.board.notification.model.dto.FileDTO;
import com.board.notification.service.AzureBlobAdapter;
import com.board.notification.service.FileService;
import com.board.notification.utils.NotificationUtils;

@RestController
@RequestMapping("/file")
public class AzureBlobFileController {

	@Autowired
	private AzureBlobAdapter azureAdapter;

	@Autowired
	private FileService fileService;

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public FileDTO uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
		String fileKey = azureAdapter.upload(file, "prefix");
		FileDTO uploadedFile = fileService.saveFile(new FileDTO(fileKey, NotificationUtils.getUKTime()));
		return uploadedFile;
	}

	@GetMapping(path = "/download")
	public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
		byte[] data = azureAdapter.getFile(file);
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + file + "\"").body(resource);

	}
}