package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Business;

public interface BusinessService {


        List<Business> getAll();
        Business getById(long id);
        Business save(Business business);
        List<Business> search(String keyword);



}
