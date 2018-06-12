package com.java.Fileserverproject.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.Fileserverproject.file.dao.FileDaoInterface;
import com.java.Fileserverproject.util.HttpUtil;

@Service
public class FileService implements FileServiceInterface {
	
	@Autowired
	FileDaoInterface fdi;

	@Override
	public HashMap<String, Object> fileUpload(MultipartFile[] files, String dir, HttpServletRequest req) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		System.out.println(param);
		
		
		for(int i = 0; i < files.length; i++) {
			HashMap<String, Object> fileMap = new HashMap<String, Object>();
			String fileNm = files[i].getOriginalFilename();
			
			try {
				byte[] bytes = files[i].getBytes();
//				String path = "D:/GDJ10/IDE/workspace/FileServer/src/main/webapp/resources/" + dir + "/"; //윈도우 경로이다.프로젝트 자체.
				String path = "/var/www/html/resources/" + dir + "/"; //웹서버에 올린다(리눅스)
//				String path = req.getSession().getServletContext().getRealPath("/") + "resources/" + dir + "/"; //톰캣서버자체에 올린다
				String dns = "http://gudi.iptime.org:10030/";
				
				File dirF = new File(path);
				
				if(!dirF.exists()) {
					dirF.mkdirs();
				}
				
				File f = new File(path + fileNm);
				OutputStream out = new FileOutputStream(f);
				out.write(bytes);
				out.close();
				
				
				fileMap.put("fileName", fileNm);
				fileMap.put("filePath", path);
				fileMap.put("fileURL", dns + "resources/" + dir + "/" + fileNm);
				
				fileMap.put("boardNo", param.get("boardNo"));
				fileMap.put("userNo", param.get("userNo"));
				
				/***********************************************************************************************/
				fdi.insert(fileMap);
				/***********************************************************************************************/
				
				list.add(fileMap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		map.put("upload", list);
		
		return map;
	}

}
