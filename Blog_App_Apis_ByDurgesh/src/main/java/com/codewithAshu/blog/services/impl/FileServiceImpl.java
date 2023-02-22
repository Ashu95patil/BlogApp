package com.codewithAshu.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithAshu.blog.services.FileService;
import com.fasterxml.jackson.core.sym.Name;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		//file name
		String filename = file.getOriginalFilename();
		//abc.png
		
		
		//random name generate file
		String randomId = UUID.randomUUID().toString();
		
		String filename1 = randomId.concat(filename.substring(filename.lastIndexOf(".")));
		
		
		//Fullpath
		String filePath = path + File.separator+filename1;
		
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//filecopy
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return filename1;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
	
		String fullPath=path+File.separator+filename;
		
		InputStream is=new FileInputStream(fullPath);
		//db logic to return inputStream
		
		return is;
	}

}
