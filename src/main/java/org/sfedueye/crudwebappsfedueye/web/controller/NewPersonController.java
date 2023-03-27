package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserInfoRepository;
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

    private final UserInfoRepository userInfoRepository;
    private final PhotoValidator photoValidator;
    private final PersonService personService;

    public NewPersonController(UserInfoRepository userInfoRepository,
                               PhotoRepository photoRepository,
                               PhotoValidator photoValidator,
                               PersonService personService){
        this.userInfoRepository = userInfoRepository;
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
    public String processPerson(@ModelAttribute("person") @Valid UserInfo userInfo,
                                Errors errors,
                                SessionStatus sessionStatus) throws IOException {


        photoValidator.validate(userInfo.getPhotoReq(), errors);
        if(errors.hasErrors()){
            return "newPerson";
        }

        userInfo.setAddingTime(new Date());
        personService.uploadPhoto(userInfo);

        if(userInfoRepository.existsByEmail(userInfo.getEmail())) {
            userInfo.setId(userInfoRepository.findByEmail(userInfo.getEmail()).getId());
        }   // Rewriting old entry if this email has already exist
        userInfoRepository.save(userInfo);

        sessionStatus.setComplete();

        return "redirect:/newperson";
    }

}
