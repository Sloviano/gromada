package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Business;
import com.example.demo.repository.BusinessRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class BusinessServiceImpl implements BusinessService {



    @Autowired
    private BusinessRepository businessRepository;


    @Override
    public List<Business> getAll() {


    List<Business> businessList = businessRepository.findAll();
    
    if(businessList.isEmpty()){

            throw new NoSuchElementException();

    }else {
        return businessList;
    }
    }

    @Override
    public Business getById(long id) {
    
        Optional<Business> business = businessRepository.findById(id);    
        
        return business.orElseThrow(() -> new EntityNotFoundException("Business not found: " + id));

    }

    @Override
    public Business save(Business business) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Business> search(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }
    




}
