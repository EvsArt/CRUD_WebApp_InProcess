package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.RegistrationForm;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.RoleRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String registrationPage(Model model, RegistrationForm registrationForm){
        model.addAttribute("registrationForm", registrationForm.getEmptyForm());
        return "registrationPage";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                      Errors errors, SessionStatus sessionStatus
                                      ){

        if(!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("confirmPassword","confirmPassword", "Пароли не совпадают!");
        }
        if (errors.hasErrors()){
            return "registrationPage";
        }
        User user = form.toUser(roleRepository, passwordEncoder);
        UserInfo info = new UserInfo();
        info.setEmail(form.getEmail());
        info.setUpdatingTime(new Date());
        user.setUserInfo(info);
        userRepository.save(user);

        sessionStatus.setComplete();
        return "redirect:/login";

    }

}
