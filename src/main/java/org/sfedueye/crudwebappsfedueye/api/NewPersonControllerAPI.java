package org.sfedueye.crudwebappsfedueye.api;

import org.sfedueye.crudwebappsfedueye.web.data.model.RegistrationForm;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.repository.RoleRepository;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/newperson",
                produces = "application/json")
public class NewPersonControllerAPI {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public NewPersonControllerAPI(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewPerson(@RequestBody RegistrationForm form){
        return userRepository.save(form.toUser(roleRepository, passwordEncoder));
    }

}
