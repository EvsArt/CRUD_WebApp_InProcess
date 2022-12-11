package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.service.PhotoValidator;
import org.sfedueye.crudwebappsfedueye.web.data.model.Person;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PersonRepository;
import org.sfedueye.crudwebappsfedueye.web.data.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/newperson")
public class NewPersonController {

    private final PersonRepository notAcceptedPersonRepository;
    private final PhotoValidator photoValidator;
    private final PersonService personService;

    public NewPersonController(PersonRepository personRepository,
                               File uploadPhotoDir,
                               PhotoValidator photoValidator,
                               PersonService personService){
        this.notAcceptedPersonRepository = personRepository;
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

        personService.uploadPhoto(person);

        notAcceptedPersonRepository.save(person);

        sessionStatus.setComplete();

        return "redirect:/newperson";
    }

}
