package com.estudos.login_screen.repositories;

import com.estudos.login_screen.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Roles extends JpaRepository<Role, Integer> {
}
