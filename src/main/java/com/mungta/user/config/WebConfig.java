package com.mungta.user.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                //.allowedOrigins("*")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(MAX_AGE_SECS);
    }
    /*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//        registry.addResourceHandler("/profileUploads/**")
//                .addResourceLocations("file:////"+ profileImagesFolder );

//        String dirName = "profileUploads";
//
//        Path profilePhotosDir = Paths.get(dirName);
//
//        String profilePhotosPath = profilePhotosDir.toFile().getAbsolutePath();
//
//        registry.addResourceHandler("/"+ dirName+"/**")
//                .addResourceLocations("file:" + profilePhotosPath+"/");

        uploadFolder("profileUploads", registry);
        uploadFolder("messageUploads", registry);
    }

    private void uploadFolder(String dirName, ResourceHandlerRegistry registry){
        Path photosDir = Paths.get(dirName);
        String photospath = photosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/"+dirName+"/**")
                .addResourceLocations("file:" + photospath + "/");
    }
    */
}
