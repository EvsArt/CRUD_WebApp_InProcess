package org.sfedueye.crudwebappsfedueye.web.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name = "person_generator", sequenceName = "person_seq", allocationSize = 1)
    private long id;

    private String name;

    private Date addingTime;

    private boolean isAccepted;

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
