package com.kh.unknown.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String store(MultipartFile file);
}