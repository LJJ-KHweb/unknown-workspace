package com.kh.unknown.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
	private final Path fileLocation;

	public FileServiceImpl() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
	}

	@Override
	public String store(MultipartFile file) {
		// 이름 바꾸기 생략
		String originalFileName = file.getOriginalFilename();
		Path targetLocation = this.fileLocation.resolve(originalFileName);

		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "http://localhost/uploads/" + originalFileName;
		} catch (IOException e) {
			throw new RuntimeException("이상한 파일입니다.");
		}
	}

}