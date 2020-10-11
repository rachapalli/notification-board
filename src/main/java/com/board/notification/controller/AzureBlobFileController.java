package com.board.notification.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.board.notification.service.AzureBlobAdapter;

@RestController
public class AzureBlobFileController {
	@Autowired
	AzureBlobAdapter azureAdapter;

	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Map<String, String> uploadFile(@RequestPart(value = "file", required = true) MultipartFile files) {
		String name = azureAdapter.upload(files, "prefix");
		Map<String, String> result = new HashMap<>();
		result.put("key", name);
		return result;
	}

	@GetMapping(path = "/download")
	public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
		byte[] data = azureAdapter.getFile(file);
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + file + "\"").body(resource);

	}
}