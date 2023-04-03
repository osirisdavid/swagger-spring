package com.example.swaggerspring.dto;

public class PersonResponse {
    public Long id;
    public String firstName;
    public PersonResponse(){}

    public PersonResponse(Long id, String firstName){
        this.id = id;
        this.firstName = firstName;
    }

}
