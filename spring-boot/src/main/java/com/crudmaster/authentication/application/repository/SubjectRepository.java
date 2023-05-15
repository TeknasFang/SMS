package com.crudmaster.authentication.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudmaster.authentication.application.entity.Student;
import com.crudmaster.authentication.application.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer>{
    
    
}
