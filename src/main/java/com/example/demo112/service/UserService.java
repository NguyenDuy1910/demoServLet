package com.example.demo112.service;

import com.example.demo112.components.JwtTokenUtils;
import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.Role;
import com.example.demo112.models.User;
import com.example.demo112.repositories.RoleRepository;
import com.example.demo112.repositories.UserRepository;

import java.util.Optional;

public class UserService implements IUserService {
    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();
    private final JwtTokenUtils jwtTokenUtil = new JwtTokenUtils();



    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();


        Optional<Role> optionalRole = roleRepository.findById(userDTO.getRoleId());
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();

            if (role.getName().equalsIgnoreCase("Admin")) {
                throw new Exception("Admin is not registered");
            }

            if (userRepository.existsByPhoneNumber(phoneNumber)) {
                throw new Exception("Phone number already exists");
            }

            System.out.printf(role.getName());
            System.out.println(role.getId());
            User user = new User();
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setFullName(userDTO.getFullName());
            user.setPassword(userDTO.getPassword());
            user.setAddress(userDTO.getAddress());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setFacebookAccountId(userDTO.getFacebookAccountId());
            user.setGoogleAccountId(userDTO.getGoogleAccountId());
            user.setActive(true);

            user.setRole(role);
            return userRepository.save(user);
        }

        throw new Exception("Role not found");
    }

    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByPhoneNumber(phoneNumber);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Kiểm tra mật khẩu
            if (!existingUser.getPassword().equals(password)) {
                throw new Exception("Invalid password");
            }

            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();

                if (!existingUser.isActive()) {
                    throw new Exception("User is not active");
                }

                // Perform any other necessary actions for a successful login
                System.out.println(jwtTokenUtil.generateToken(existingUser));

                return jwtTokenUtil.generateToken(existingUser);
            } else {
                throw new Exception("Role not found");
            }
        } else {
            throw new Exception("Phone number not found");
        }
    }
}