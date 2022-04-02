package com.tessaro.springsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="CUSTOMER")
//Codigo abaixo é necessario para que o hibernate nao bug devido ao @OneToMany quando utilizado junto do @Data que gerará automaticametne o Equals e HashCode
@EqualsAndHashCode(exclude="customer_role")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int id;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @JsonIgnore
    private String pwd;
    @Column(name = "create_dt")
    private String createDt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITIES")
    @JsonIgnore
    @OneToMany(mappedBy = "customer_authority", fetch=FetchType.EAGER)
    private Set<Authority> customer_role;
    
}
