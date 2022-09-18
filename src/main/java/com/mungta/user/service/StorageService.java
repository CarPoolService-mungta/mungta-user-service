package com.mungta.user.service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mungta.user.dto.FileInfo;

public interface StorageService {

    void initDir();

    void cleanDir();

    FileInfo store(String userId,MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
