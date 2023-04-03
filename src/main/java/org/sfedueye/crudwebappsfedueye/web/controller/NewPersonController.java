package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserInfoRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.PhotoRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PhotoValidator photoValidator;
    private final PersonService personService;

    public NewPersonController(UserRepository userRepository,
                               PhotoValidator photoValidator,
                               PersonService personService){
        this.userRepository = userRepository;
        this.photoValidator = photoValidator;
        this.personService = personService;
    }

    @GetMapping
    public String newPerson(){
        return "newPerson";
    }

    @ModelAttribute(name = "person")
    public UserInfo person(){
        return new UserInfo();
    }


    @PostMapping
    public String processPerson(@ModelAttribute("person") @Valid User user,
                                Errors errors,
                                SessionStatus sessionStatus) throws IOException {


        photoValidator.validate(user.getUserInfo().getPhotoReq(), errors);
        if(errors.hasErrors()){
            return "newPerson";
        }

        user.getUserInfo().setAddingTime(new Date());
        personService.uploadPhoto(user);

        if(userRepository.existsByEmail(user.getEmail())) {
            user.setId(userRepository.findByEmail(user.getEmail()).getId());
        }   // Rewriting old entry if this email has already exist
        userRepository.save(user);

        sessionStatus.setComplete();

        return "redirect:/newperson";
    }

}
