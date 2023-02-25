package org.sfedueye.crudwebappsfedueye.web.data.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.sfedueye.crudwebappsfedueye.web.data.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class RegistrationForm {

    private RoleRepository roleRepository;

    public RegistrationForm(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public RegistrationForm getEmptyForm(){
        return new RegistrationForm(roleRepository);
    }

    @Size(max = 500, message = "Длина поля не более 500 символов!")
    @Pattern(regexp = "^[a-z]+@sfedu\\.ru$", message = "Логин должен являться почтой @sfedu.ru")
    private String username;


    @Size(min = 8, message = "Не менее 8 символов!")
    @Size(max = 40, message = "Слишком длинный пароль!")
    private String password;

    @Size(min = 8, message = "Не менее 8 символов!")
    @Size(max = 40, message = "Слишком длинный пароль!")
    private String confirmPassword;

    public User toUser(RoleRepository repo, PasswordEncoder passwordEncoder){
        return new User(username, passwordEncoder.encode(password), repo.findRoleByName("USER"));
    }

}
