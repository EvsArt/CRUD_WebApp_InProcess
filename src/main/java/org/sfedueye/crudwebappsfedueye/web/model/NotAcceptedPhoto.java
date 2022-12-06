package org.sfedueye.crudwebappsfedueye.web.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class NotAcceptedPhoto{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name = "person_generator", sequenceName = "person_seq", allocationSize = 1)
    private long id;

    private String name;

    private Date addingTime;

    @PrePersist
    private void addingTime(){
        this.addingTime = new Date();
    }

}
