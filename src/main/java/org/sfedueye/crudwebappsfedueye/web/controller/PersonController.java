package org.sfedueye.crudwebappsfedueye.web.controller;

import org.sfedueye.crudwebappsfedueye.web.data.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @GetMapping("/{id}")
    public String personPage(Model model,
                             @PathVariable Long id){
        model.addAttribute("person", personRepository.findById(id).get());
        return "personPage";
    }

    @GetMapping("/{id}/edit")
    public String personEdit(Model model,
                             @PathVariable Long id){
        model.addAttribute("person", personRepository.findById(id).get());
        return "newPerson";
    }

    @GetMapping("/{id}/delete")

    public String personDelete(@PathVariable Long id){
        personRepository.deleteById(id);
        return "redirect:/admin";
    }

}
