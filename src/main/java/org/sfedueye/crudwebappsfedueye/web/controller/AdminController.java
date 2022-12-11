package org.sfedueye.crudwebappsfedueye.web.controller;

import org.sfedueye.crudwebappsfedueye.web.data.repository.PersonRepository;
import org.sfedueye.crudwebappsfedueye.web.data.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonRepository personRepository;

    public AdminController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @ModelAttribute(name = "unacceptedList")
    public List<Person> unacceptedList(){
        List<Person> personList = personRepository.findAllByAcceptedIsTrue();
        return personList.subList(0, Math.min(personList.size(), 30));
    }

    @GetMapping("")
    public String admin(){
        return "redirect:/admin/unacceptedlist";
    }

    @GetMapping("/unacceptedlist")
    public String adminPage(){
        return "adminPage";
    }

}
