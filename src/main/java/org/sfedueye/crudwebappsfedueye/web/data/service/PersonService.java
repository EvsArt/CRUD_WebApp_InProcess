package org.sfedueye.crudwebappsfedueye.web.data.service;

import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.model.Photo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class PersonService {

    private final File uploadPhotoDir;
    private final PhotoRepository photoRepository;

    public PersonService(File uploadPhotoDir,
                         PhotoRepository photoRepository) {
        this.uploadPhotoDir = uploadPhotoDir;
        this.photoRepository = photoRepository;
    }

    public void uploadPhoto(User user) throws IOException {

        MultipartFile photo = user.getUserInfo().getPhotoReq();
        String fileName = user.getEmail().split("@")[0] + ".png";  // Email without domain

        if(!photo.getContentType().equals("application/octet-stream")) {

            photo.transferTo(new File(uploadPhotoDir.getPath() + "/" + fileName));

            Photo photoEnt = Photo.newPhotoWithName(fileName);
            if(photoRepository.existsPhotoByName(fileName)){
                photoEnt.setId(photoRepository.findByName(fileName).getId());
            }
            photoEnt.setAddingTime(new Date());

            user.getUserInfo().setPhoto(photoEnt);
        }else{
            user.getUserInfo().setPhoto(photoRepository.findByName(fileName));
        }
    }
}
