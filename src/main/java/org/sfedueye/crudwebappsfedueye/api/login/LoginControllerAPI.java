package org.sfedueye.crudwebappsfedueye.api.login;

import org.sfedueye.crudwebappsfedueye.web.data.model.LoginForm;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/login",
        produces = "application/json")
public class LoginControllerAPI {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    LoginControllerAPI(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = "application/json")
    public LoginResponseForm login(@RequestBody LoginForm form){

        if(!userRepository.existsByEmail(form.getEmail()))
            return new LoginResponseForm(false, "Пользователь с таким email не найден!", null, null);

        User user = userRepository.findByEmail(form.getEmail());
        if(!passwordEncoder.matches(form.getPassword(), user.getPassword()))
            return new LoginResponseForm(false, "Неверный пароль!", null, null);

        return new LoginResponseForm(true, "Вход произведён успешно!", user.getId(), user.hashCode());
    }


}
