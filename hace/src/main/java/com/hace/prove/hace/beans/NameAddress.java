package com.hace.prove.hace.beans;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "NAME_ADDRESS")
public class NameAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_name;

    @Column(name ="name")
    private String name;
    @Column(name ="house_number")
    private String houseNumber;
    @Column(name ="city")
    private String city;
    @Column(name ="province")
    private String province;
    @Column(name ="postal_code")
    private String postalCode;
}
