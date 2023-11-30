package com.example.demo112.service;

import com.example.demo112.dtos.UpdateUserDTO;
import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.User;

public interface IUserService {
     User createUser(UserDTO userDTO)throws Exception ;
    String login(String phoneNumber, String password, Long roleId) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

}
