package org.sfedueye.crudwebappsfedueye.api.sendUserData;

import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/data",
        produces = "application/json")
public class UserDataController {
    private final UserRepository userRepository;

    public UserDataController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(consumes = "application/json")
    public UserData sendUserData(@RequestBody UserBaseInfo info){

        UserData data = new UserData();

        User user = userRepository.findUserById(info.getId());
        if(user.hashCode() == info.getHashCode()){

            UserInfo usInfo = user.getUserInfo();

            if(usInfo.getName()!=null) data.setName(usInfo.getName());
            if(usInfo.getSurname()!=null) data.setSurname(usInfo.getSurname());
            if(usInfo.getPatronymic()!=null) data.setPatronymic(usInfo.getPatronymic());
            if(usInfo.getRole()!=null) data.setRole(usInfo.getRole());
            if(usInfo.getDivision()!=null) data.setDivision(usInfo.getDivision());
            if(usInfo.getFaculty()!=null) data.setFaculty(usInfo.getFaculty());
            if(usInfo.getSpecialization()!=null) data.setSpecialization(usInfo.getSpecialization());
            if(usInfo.getCourse()!=null) data.setCourse(usInfo.getCourse());
            if(usInfo.getGroupNum()!=null) data.setGroupNum(usInfo.getGroupNum());
            if(usInfo.getInfo()!=null) data.setInfo(usInfo.getInfo());

        }

        return data;

    }

}
