package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entities.Business;
import com.example.demo.service.BusinessService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BusinessPageController {
    
    @Autowired    
    private final BusinessService businessService;


        @GetMapping("/businesses/{id}")
        public String getBusiness(@PathVariable long id, Model model){

                model.addAttribute("businesses", businessService.getById(id));

            return "business";

        }
        @GetMapping("/businesses/list")
        public String getAllBusinessCards(Model model){

            
            model.addAttribute("allBusinesses", businessService.getAll());    

            
            return "business";
        }

}
