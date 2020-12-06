package com.board.notification.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobProperties;
import com.board.notification.utils.NotificationConstants;

@Service
public class AzureBlobAdapter {

	@Autowired
	BlobClientBuilder client;

	public String upload(MultipartFile file, String prefixName) throws IOException {
		if (file != null && file.getSize() > 0) {
			// implement your own file name logic.
			String fileName = prefixName + UUID.randomUUID().toString() + file.getOriginalFilename();
			client.blobName(fileName).buildClient().upload(file.getInputStream(), file.getSize());
			return fileName;
		}
		return null;
	}

	public byte[] getFile(String name) throws IOException {
		if (name == null || name.isEmpty() || "NULL".equalsIgnoreCase(name)) {
			throw new IOException(NotificationConstants.INVALID_FILE_NAME + name);
		}
		File temp = new File("/home/temp/" + name);
		if (!temp.exists()) {
			BlobProperties properties = client.blobName(name).buildClient().downloadToFile(temp.getPath());
		}
		byte[] content = Files.readAllBytes(Paths.get(temp.getPath()));
		temp.delete();
		return content;
	}

	public boolean deleteFile(String name) {
		try {
			client.blobName(name).buildClient().delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}