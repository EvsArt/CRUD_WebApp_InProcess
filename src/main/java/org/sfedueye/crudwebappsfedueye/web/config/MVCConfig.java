package org.sfedueye.crudwebappsfedueye.web.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

//    @Override   // For showing new uploaded photos
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/**")
//                .addResourceLocations("file:" + uploadPath);
//    }

    @Bean
    File uploadPhotoDir(){
        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        return uploadDir;
    }

}
