package com.mungta.user.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void initDir() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }
    @Override
    public void cleanDir() {
        try {
            Files.list(Paths.get(uploadPath)).forEach(file->{
                if(!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    }catch(IOException ex) {
                        System.out.println("Could not delete file : " + file);
                    }
                }
            });
        }catch(IOException ex2) {
            System.out.println("Could not list directory : " + uploadPath);
        }
    }

    @Override
    public String store(final String userId, final MultipartFile file) {
        String fileName = "";
        try {
            if (file.isEmpty()) {
                throw new Exception("ERROR : File is empty.");
            }
            // 파일명 생성
            fileName = UUID.randomUUID().toString() +"_"+
                       userId+"."+
                       file.getOriginalFilename().substring( file.getOriginalFilename().lastIndexOf(".")+1);
            // 파일명 체크 (동일파일시 error)
            File filecheck = new File(uploadPath + "\\" +fileName);
            if(filecheck.exists()) {
                //filecheck.delete();
                throw new Exception("ERROR : File already existed.");
            }

            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                initDir();
            }
            try (InputStream inputStream = file.getInputStream()) {

                Files.copy(inputStream, root.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return fileName;
    }

    @Override
    public Stream<Path> loadAll() {
        log.debug("here 3");
        return null;
    }

    @Override
    public Path load(String filename) {
        log.debug("here 4");
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        log.debug("here 5");
        return null;
    }

    @Override
    public void deleteAll() {
        log.debug("here 6");

    }

}
