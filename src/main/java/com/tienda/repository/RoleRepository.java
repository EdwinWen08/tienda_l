package com.tienda.repository;

import com.tienda.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, String>{
    
}
