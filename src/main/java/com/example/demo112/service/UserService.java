package com.example.demo112.service;

import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.User;
import com.example.demo112.repositories.UserRepository;

import java.util.Optional;
import java.util.zip.DataFormatException;

public class UserService implements IUserService {
    private final  UserRepository userRepository=new UserRepository();



    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber=userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber))
        {
            throw new Exception("Phone number already exists");
        }
        else {
            User user = new User(userDTO.getFullName(), userDTO.getPhoneNumber(),
                    userDTO.getAddress(), userDTO.getPassword(), userDTO.getDateOfBirth());

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