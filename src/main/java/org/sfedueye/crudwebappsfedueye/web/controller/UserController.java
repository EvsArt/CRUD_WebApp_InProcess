package org.sfedueye.crudwebappsfedueye.web.controller;

import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserInfoRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/userInfo")
public class UserController {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public UserController(UserInfoRepository userInfoRepository, UserRepository userRepository){
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String personPage(Model model,
                             @PathVariable Long id){
        UserInfo info = userInfoRepository.findById(id).get();
        model.addAttribute("isEnable", userRepository.findByEmail(info.getEmail()).isEnabled());
        model.addAttribute("isDelete", userRepository.findByEmail(info.getEmail()).isDeleted());
        model.addAttribute("userInfo", userInfoRepository.findById(id).get());
        return "userPage";
    }

    @GetMapping("/{id}/edit")
    public String personEdit(Model model,
                             @PathVariable Long id){
        model.addAttribute("userInfo", userInfoRepository.findById(id).get());
        return "updateUser";
    }

    @GetMapping("/{id}/delete")
    public String personDelete(@PathVariable Long id){
        User user = userRepository.findByUserInfoId(id);
        user.setDeleted(true);
        userRepository.save(user);
        return "redirect:/userInfo/"+id;
    }

    @GetMapping("/{id}/recover")
    public String personRecover(@PathVariable Long id){
        User user = userRepository.findByUserInfoId(id);
        user.setDeleted(false);
        userRepository.save(user);
        return "redirect:/userInfo/"+id;
    }

}