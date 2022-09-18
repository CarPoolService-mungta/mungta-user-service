package com.mungta.user.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mungta.user.dto.FileInfo;

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
                    }catch(IOException err) {
                        log.error("Could not delete file :  ",err);
                    }
                }
            });
        }catch(IOException e) {
            log.error("Could not list directory : ",uploadPath,e);
        }
    }

    @Override
    public FileInfo store(final String userId, final MultipartFile file) {

        String oldFileName ="";
        String fileName = "";
        String fileExtension ="";

        try {
            if (file.isEmpty()) {
                throw new Exception("ERROR : File is empty.");
            }
            // 파일명 생성
            oldFileName = file.getOriginalFilename();
            fileExtension = oldFileName.substring( oldFileName.lastIndexOf(".")+1);
            fileName = UUID.randomUUID().toString() +"_"+
                       userId+"."+
                       fileExtension;
            // 파일명 체크 (동일파일시 error)
            File filecheck = new File(uploadPath + "\\" +fileName);
            if(filecheck.exists()) {
                //filecheck.delete();
                throw new Exception("ERROR : File already existed.");
            }
            // 파일 업로드
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
        return FileInfo.builder().userFileName(fileName).userFileOriName(oldFileName).userFileSize(0).userFileUrl(uploadPath).fileExtension(fileExtension).build();
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }

}
