package com.dh.dental_clinic.entity;

import java.util.UUID;

import com.dh.dental_clinic.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
public class HomeAddress {
    @Getter
    @Setter
    @Id
    @JsonIgnore
    @GeneratedValue
    private UUID id;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String number;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private String province;

    public HomeAddress() {
    }

    public HomeAddress(String address, String number, String location, String province) {
        this.id = IdGenerator.guid(address + number + location + province);
        this.address = address;
        this.number = number;
        this.location = location;
        this.province = province;
    }

}
