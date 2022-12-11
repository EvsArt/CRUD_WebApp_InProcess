package org.sfedueye.crudwebappsfedueye.web.data.service;

import org.sfedueye.crudwebappsfedueye.web.data.model.Person;
import org.sfedueye.crudwebappsfedueye.web.data.model.Photo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class PersonService {

    private final File uploadPhotoDir;

    public PersonService(File uploadPhotoDir) {
        this.uploadPhotoDir = uploadPhotoDir;
    }

    public void uploadPhoto(Person person) throws IOException {

        MultipartFile photo = person.getPhotoReq();

        String fileName = person.getEmail().split("@")[0] + ".png";  // Email without domain

        photo.transferTo(new File(uploadPhotoDir.getPath() + "/" + fileName));

        person.setPhoto(Photo.newPhotoWithName(fileName));
    }
}
