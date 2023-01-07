package org.sfedueye.crudwebappsfedueye.web.controller;

import jakarta.validation.Valid;
import org.sfedueye.crudwebappsfedueye.web.data.model.RegistrationForm;
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

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

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

        if (errors.hasErrors()){
            return "registrationPage";
        }

        if (form.getPassword().equals(form.getConfirmPassword())) {
            userRepository.save(form.toUser(roleRepository, passwordEncoder));
        }else{
            errors.rejectValue("confirmPassword", "PasswordsEquals", "Пароли не совпадают!");
            return "registrationPage";
        }
        sessionStatus.setComplete();
        return "redirect:/login";

    }

}
