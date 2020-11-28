package com.board.notification.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.notification.dao.AllFilesRepo;
import com.board.notification.model.AllFiles;
import com.board.notification.model.dto.FileDTO;
import com.board.notification.service.FileService;
import com.board.notification.utils.NotificationUtils;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private AllFilesRepo allFilesRepo;

	@Override
	public FileDTO saveFile(FileDTO files) {
		AllFiles allFiles = new AllFiles();
		files.setCreatedDate(NotificationUtils.getUKTime());
		BeanUtils.copyProperties(files, allFiles);
		allFilesRepo.save(allFiles);
		files.setFileId(allFiles.getFileId());
		return files;
	}
}
