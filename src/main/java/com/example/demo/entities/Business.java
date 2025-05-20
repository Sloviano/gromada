package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business")
@Data               // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Default constructor
@AllArgsConstructor // Constructor with all fields
public class Business {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String category;

    private String phone;

    private String location;

    private double rating;
     
    private short likes;

    private short dislikes;



}
