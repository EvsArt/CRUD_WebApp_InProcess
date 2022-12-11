package org.sfedueye.crudwebappsfedueye.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name = "person_generator", sequenceName = "person_seq", allocationSize = 1)
    private long id;

    @Transient

    private MultipartFile photoReq;  // Field for getting photo from request

    @OneToOne(cascade = CascadeType.ALL)
    private Photo photo; // Field for Photo table

    private Date addingTime;

    private boolean isAccepted;

    @PrePersist
    private void addingTime(){
        this.addingTime = new Date();
    }


    // Common text fields
    @Size(max = 500, message = "Длина поля не более 500 символов!")
    @Pattern(regexp = "^[a-z]+@sfedu\\.ru$", message = "Введите почту @sfedu.ru")
    protected String email;

    @Pattern(regexp = "^[A-Z|А-Я][a-z|а-я]*$", message = "Только русские или английские буквы, первая буква заглавная!")
    @Size(max = 200, message = "Длина поля не более 200 символов!")
    @NotBlank(message = "Поле не может быть пустым")
    protected String name;

    @Pattern(regexp = "^[A-Z|А-Я][a-z|а-я]*$", message = "Только русские или английские буквы, первая буква заглавная!")
    @Size(max = 200, message = "Длина поля не более 200 символов!")
    @NotBlank(message = "Поле не может быть пустым")
    protected String surname;

    @Pattern(regexp = "^[A-Z|А-Я][a-z|а-я]*$", message = "Только русские или английские буквы, первая буква заглавная!")
    @Size(max = 200, message = "Длина поля не более 200 символов!")
    protected String patronymic;

    @Size(max = 200, message = "Длина поля не более 200 символов!")
    @NotBlank(message = "Поле не может быть пустым")
    protected String role;

    @Size(max = 100, message = "Длина поля не более 100 символов!")
    @NotBlank(message = "Поле не может быть пустым")
    protected String division;

    @Size(max = 100, message = "Длина поля не более 100 символов!")
    @NotBlank(message = "Поле не может быть пустым")
    protected String faculty;

    @Size(max = 100, message = "Длина поля не более 100 символов!")
    protected String specialization;

    @NotNull(message = "Поле не может быть пустым")
    @Min(value = 1, message = "Для начала нужно поступить в вуз...")
    @Max(value = 6, message = "Введите число не более 6")
    protected Short course;

    @NotNull(message = "Поле не может быть пустым")
    @Min(value = 0, message = "Какая отрицательная группа? Ты чево...")
    @Max(value = 1000, message = "Число должно быть не более 1000")
    protected Short groupNum;

    @Size(max = 2000, message = "Не более 2000 символов!")
    protected String info;

}
