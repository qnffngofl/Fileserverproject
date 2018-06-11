package com.java.Fileserverproject.file;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {
	
	public HashMap<String, Object> fileUpload(MultipartFile[] files, String dir, HttpServletRequest req);

}
