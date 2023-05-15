package com.crudmaster.authentication.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudmaster.authentication.application.entity.Parent;

public interface ParentRepository extends JpaRepository<Parent, Integer> {

}
