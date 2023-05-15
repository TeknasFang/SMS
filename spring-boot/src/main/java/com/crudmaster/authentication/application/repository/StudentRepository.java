package com.crudmaster.authentication.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudmaster.authentication.application.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
    
}
