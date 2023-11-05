package com.example.demo112.service;

import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.Role;
import com.example.demo112.models.User;
import com.example.demo112.repositories.RoleRepository;
import com.example.demo112.repositories.UserRepository;

import java.util.Optional;
import java.util.zip.DataFormatException;

public class UserService implements IUserService {
    private final  UserRepository userRepository=new UserRepository();
    private final RoleRepository roleRepository=new RoleRepository();


    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber=userDTO.getPhoneNumber();
        Long adminId=0L;
        Long userId=1L;
        if (phoneNumber.equals("0793584279") ){

            userDTO.setRoleId(adminId);
        }
        else
        {
            userDTO.setRoleId(userId=1L);
        }

        Role role =roleRepository.findById(userDTO.getRoleId());



        if (role.getName().equalsIgnoreCase("Admin")) {
            System.out.println("Admin");
        }


        if (userRepository.existsByPhoneNumber(phoneNumber))
        {
            throw new Exception("Phone number already exists");
        }
        else {
          User user=new User();
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


    }

    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByPhoneNumber(phoneNumber);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Kiểm tra mật khẩu
            if (user.getPassword().equals(password)) {
                // Kiểm tra vai trò (role) của người dùng
                if (roleId == 1) {
                    // Logic đăng nhập thành công
                    return "Login successful";
                } else {
                    throw new Exception("Invalid role");
                }
            } else {
                throw new Exception("Invalid password");
            }
        } else {
            throw new Exception("Phone number not found");
        }
    }
}