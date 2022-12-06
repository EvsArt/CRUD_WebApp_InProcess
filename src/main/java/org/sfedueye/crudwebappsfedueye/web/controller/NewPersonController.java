package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.model.NotAcceptedPerson;
import org.sfedueye.crudwebappsfedueye.web.model.NotAcceptedPhoto;
import org.sfedueye.crudwebappsfedueye.web.repository.NotAcceptedPersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/newperson")
public class NewPersonController {

    NotAcceptedPersonRepository notAcceptedPersonRepository;

    public NewPersonController(NotAcceptedPersonRepository notAcceptedPersonRepository){
        this.notAcceptedPersonRepository = notAcceptedPersonRepository;
    }

    @GetMapping
    public String newPerson(){
        return "newPerson";
    }

    @ModelAttribute(name = "person")
    public NotAcceptedPerson person(){
        return new NotAcceptedPerson();
    }

    @ModelAttribute(name = "photo")
    public NotAcceptedPhoto photo(){
        return new NotAcceptedPhoto();
    }


    @PostMapping
    public String processPerson(@ModelAttribute("person") @Valid NotAcceptedPerson person,
                                Errors errors,
                                @ModelAttribute("photo") NotAcceptedPhoto photo,
                                SessionStatus sessionStatus){

        if(errors.hasErrors()){
            return "newPerson";
        }

        photo.setName("NotRealised");
        person.setPhoto(photo);

        notAcceptedPersonRepository.save(person);

        sessionStatus.setComplete();

        return "redirect:/newperson";
    }

}
