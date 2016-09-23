package com.dev.spring.common.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public void createDirectory(String directoryName){
		
		File theDir = new File(directoryName);
		
		if (!theDir.exists()){
			if(theDir.mkdirs() == false){
				logger.info("Create Directory Fail!!");
			}
		}
	}
	
	public void writeFile(MultipartFile upload, String pathNm, String saveNm) throws Exception{
		
		createDirectory(pathNm);
		
		File file = new File(pathNm + "/" + saveNm);
		
		upload.transferTo(file);
	}
	
	public void deleteFile(String pathNm, String saveNm) throws Exception{
		
		File file = new File(pathNm + "/" + saveNm);
		
		if(!file.delete()){
			logger.info("File Delete Fail!!");
		}
	}
}