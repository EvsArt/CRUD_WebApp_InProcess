package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.Person;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PersonRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PhotoRepository;
import org.sfedueye.crudwebappsfedueye.web.data.service.PersonService;
import org.sfedueye.crudwebappsfedueye.web.data.service.PhotoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/newperson")
public class NewPersonController {

    private final PersonRepository personRepository;
    private final PhotoValidator photoValidator;
    private final PersonService personService;

    public NewPersonController(PersonRepository personRepository,
                               PhotoRepository photoRepository,
                               PhotoValidator photoValidator,
                               PersonService personService){
        this.personRepository = personRepository;
        this.photoValidator = photoValidator;
        this.personService = personService;
    }

    @GetMapping
    public String newPerson(){
        return "newPerson";
    }

    @ModelAttribute(name = "person")
    public Person person(){
        return new Person();
    }


    @PostMapping
    public String processPerson(@ModelAttribute("person") @Valid Person person,
                                Errors errors,
                                SessionStatus sessionStatus) throws IOException {


        photoValidator.validate(person.getPhotoReq(), errors);
        if(errors.hasErrors()){
            return "newPerson";
        }

        person.setAddingTime(new Date());
        personService.uploadPhoto(person);

        if(personRepository.existsByEmail(person.getEmail())) {
            person.setId(personRepository.findByEmail(person.getEmail()).getId());
        }   // Rewriting old entry if this email has already exist
        personRepository.save(person);

        sessionStatus.setComplete();

        return "redirect:/newperson";
    }

}
