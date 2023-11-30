package com.example.demo112.service;

import com.example.demo112.components.JwtTokenUtils;
import com.example.demo112.dtos.UpdateUserDTO;
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

    @Override

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
//            if(userDTO.getFacebookAccountId()==0&&userDTO.getGoogleAccountId()==0)
//            {
//                String password=userDTO.getPassword();
//                String encodedPassword=passwordEnc
//            }
            return userRepository.save(user);
        }

        throw new Exception("Role not found");
    }

    @Override
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

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> user = userRepository.findUserByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId);

        // Check if the phone number is being changed and if it already exists for another user
        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
                userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new Exception("Phone number already exists");
        }


        // Update user information based on the DTO
        if (updatedUserDTO.getFullName() != null) {
            existingUser.setFullName(updatedUserDTO.getFullName());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (updatedUserDTO.getAddress() != null) {
            existingUser.setAddress(updatedUserDTO.getAddress());
        }
        if (updatedUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
        }
        if (updatedUserDTO.getFacebookAccountId() > 0) {
            existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
        }
        if (updatedUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
        }

        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if (!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new Exception("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            existingUser.setPassword(newPassword);
        }
        //existingUser.setRole(updatedRole);
        // Save the updated user
        return userRepository.update(existingUser);
    }
}