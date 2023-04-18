package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserInfoRepository;
import org.sfedueye.crudwebappsfedueye.web.data.service.PhotoValidator;
import org.sfedueye.crudwebappsfedueye.web.data.service.UserService;
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
@RequestMapping("/updateuser")
public class UpdateUserController {

    private final UserInfoRepository userInfoRepository;
    private final PhotoValidator photoValidator;
    private final UserService userService;

    public UpdateUserController(UserInfoRepository userInfoRepository,
                                PhotoValidator photoValidator,
                                UserService userService){
        this.userInfoRepository = userInfoRepository;
        this.photoValidator = photoValidator;
        this.userService = userService;
    }

    @GetMapping
    public String newUser(){
        return "updateUser";
    }

    @ModelAttribute(name = "userInfo")
    public UserInfo userInfo(){
        return new UserInfo();
    }


    @PostMapping
    public String processUser(@ModelAttribute("userInfo") @Valid UserInfo userInfo,
                                Errors errors,
                                SessionStatus sessionStatus) throws IOException {

        if(userInfo.getPhotoReq() != null)
            photoValidator.validate(userInfo.getPhotoReq(), errors);
        if(errors.hasErrors()){
            return "updateUser";
        }

        userInfo.setUpdatingTime(new Date());
        userService.uploadPhoto(userInfo);

        if(userInfoRepository.existsByEmail(userInfo.getEmail())) {
            userInfo.setId(userInfoRepository.findByEmail(userInfo.getEmail()).getId());
            userInfoRepository.save(userInfo);
        }else{
            errors.rejectValue("email", "email", "Пользователя с данным email не существует!");
        }

        sessionStatus.setComplete();

        return "redirect:/userInfo/"+userInfo.getId();
    }

}
