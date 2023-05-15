package com.crudmaster.authentication.application.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="student")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private int enroll_id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String sex;
    @Column
    private String adress;
    @Column
    private String phone;
    @Column
    private String dob;
    @Column
    private Date dateOfJoin;
    
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Parent> parentList=new ArrayList<>();
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="standard")
    private Standard standard;
    

}
