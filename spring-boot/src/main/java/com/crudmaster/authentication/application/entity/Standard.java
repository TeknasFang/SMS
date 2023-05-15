package com.crudmaster.authentication.application.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="standard")
public class Standard{
    
    @Id
    private Integer standardId;
    @Column
    private String number_of_student;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "standard",cascade = CascadeType.ALL)
    private List<Student> studentList;
    @ToString.Exclude
    @OneToMany(mappedBy = "standard",cascade = CascadeType.ALL)
    private List<Subject> subjectList;
}
