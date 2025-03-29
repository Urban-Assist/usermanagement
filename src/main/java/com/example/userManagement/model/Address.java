package com.example.userManagement.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String address;
    private String pincode;
    private String city;
    private String province;
}