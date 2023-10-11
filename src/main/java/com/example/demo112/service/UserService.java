package com.example.demo112.service;
import com.example.demo112.models.User;
import java.util.List;

public interface UserService {
    void insert(User user);

    void edit(User user);

    void delete(int id);

    User get(String username);

    User get(int id);

    User login(String username, String password);

    boolean register( String username,String email, String password);

    List<User> getAll();

    List<User> search(String keyword);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);

}
