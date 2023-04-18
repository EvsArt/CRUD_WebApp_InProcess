package org.sfedueye.crudwebappsfedueye.web.data.service;

import org.sfedueye.crudwebappsfedueye.web.data.model.Photo;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class UserService {

    private final File uploadPhotoDir;
    private final PhotoRepository photoRepository;

    public UserService(File uploadPhotoDir,
                       PhotoRepository photoRepository) {
        this.uploadPhotoDir = uploadPhotoDir;
        this.photoRepository = photoRepository;
    }

    public void uploadPhoto(UserInfo userInfo) throws IOException {

        MultipartFile photo = userInfo.getPhotoReq();
        String fileName = userInfo.getEmail().split("@")[0] + ".png";  // Email without domain

        if(!photo.getContentType().equals("application/octet-stream")) {

            photo.transferTo(new File(uploadPhotoDir.getPath() + "/" + fileName));

            Photo photoEnt = Photo.newPhotoWithName(fileName);
            if(photoRepository.existsPhotoByName(fileName)){
                photoEnt.setId(photoRepository.findByName(fileName).getId());
            }
            photoEnt.setAddingTime(new Date());

            userInfo.setPhoto(photoEnt);
        }else{
            userInfo.setPhoto(photoRepository.findByName(fileName));
        }
    }
}
