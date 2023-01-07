package org.sfedueye.crudwebappsfedueye.web.data.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_generator")
    @SequenceGenerator(name = "photo_generator", sequenceName = "photo_seq", allocationSize = 1)
    private long id;

    private String name;

    private Date addingTime;

    private boolean accepted;

    @PrePersist
    private void addingTime(){
        this.addingTime = new Date();
    }

    public static Photo newPhotoWithName(String name){
        Photo photo = new Photo();
        photo.setName(name);
        return photo;
    }

}
