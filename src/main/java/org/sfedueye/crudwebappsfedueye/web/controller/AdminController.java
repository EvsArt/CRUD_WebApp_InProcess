package org.sfedueye.crudwebappsfedueye.web.controller;

import org.sfedueye.crudwebappsfedueye.web.data.repository.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonRepository personRepository;

    public AdminController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @GetMapping()
    public String admin(){
        return "redirect:/admin/unacceptedlist";
    }

    @GetMapping("/unacceptedlist")
    public String adminPage(Model model,
                            @PageableDefault(size = 15) Pageable pageable
    ){

        model.addAttribute("page", personRepository.findAllByAcceptedIsTrueOrderBySurname(pageable));
        model.addAttribute("url", "/admin/unacceptedlist");
        model.addAttribute("total", personRepository.countAllByAcceptedIsTrue());
        model.addAttribute("pageNum", pageable.getPageNumber());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "adminPage";
    }

}
