package com.crudmaster.authentication.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudmaster.authentication.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    Optional<Role> findByName(String name);

}
