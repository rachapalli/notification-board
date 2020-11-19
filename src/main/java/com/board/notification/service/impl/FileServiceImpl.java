package com.board.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.notification.dao.AllFilesRepo;
import com.board.notification.model.AllFiles;
import com.board.notification.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private AllFilesRepo allFilesRepo;
	
	@Override
	public AllFiles saveFile(AllFiles files) {
		return allFilesRepo.save(files);
	}
}
