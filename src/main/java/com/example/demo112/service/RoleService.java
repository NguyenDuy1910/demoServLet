package com.example.demo112.service;

import com.example.demo112.models.Role;
import com.example.demo112.repositories.RoleRepository;

import java.util.List;

public class RoleService implements IRoleService {
    private final RoleRepository roleRepository = new RoleRepository();

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
