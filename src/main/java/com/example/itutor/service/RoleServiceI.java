package com.example.itutor.service;

import com.example.itutor.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleServiceI {

    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Role createRole(Role role);
    Role updateRole(Long id, Role updatedRole);
    boolean deleteRole(Long id);


}
