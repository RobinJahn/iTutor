package com.example.itutor.service.impl;

import com.example.itutor.domain.Role;
import com.example.itutor.repository.RoleRepository;
import com.example.itutor.service.RoleServiceI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleServiceI {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        if (roleRepository.existsById(id)) {
            updatedRole.setId(id);
            return roleRepository.save(updatedRole);
        }
        return null; // Or Error-Handling
    }

    @Override
    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false; // Or Error-Handling
    }
}
