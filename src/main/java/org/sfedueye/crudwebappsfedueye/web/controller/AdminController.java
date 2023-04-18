package org.sfedueye.crudwebappsfedueye.web.controller;

import org.sfedueye.crudwebappsfedueye.web.data.repository.UserInfoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserInfoRepository userInfoRepository;

    public AdminController(UserInfoRepository userInfoRepository){
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping()
    public String admin(){
        return "redirect:/admin/userslist";
    }

    @GetMapping("/userslist")
    public String adminPage(Model model,
                            @PageableDefault(size = 15) Pageable pageable
    ){

        model.addAttribute("page", userInfoRepository.findAllByOrderBySurname(pageable));
        model.addAttribute("url", "/admin/userslist");
        model.addAttribute("total", userInfoRepository.countAllBy());
        model.addAttribute("pageNum", pageable.getPageNumber());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "adminPage";
    }

}
