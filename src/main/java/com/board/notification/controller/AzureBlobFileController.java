package com.board.notification.controller;

import java.io.IOException;
import java.util.Date;

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

import com.board.notification.dao.AllFilesRepo;
import com.board.notification.model.AllFiles;
import com.board.notification.service.AzureBlobAdapter;

@RestController
@RequestMapping("/file")
public class AzureBlobFileController {

	@Autowired
	private AzureBlobAdapter azureAdapter;

	@Autowired
	private AllFilesRepo allFilesRepo;

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public AllFiles uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) {
		String fileKey = azureAdapter.upload(file, "prefix");
		AllFiles uploadedFile = allFilesRepo.save(new AllFiles(file.getName(), fileKey, null, new Date()));
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