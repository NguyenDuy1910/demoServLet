package com.example.demo112.service.impl;
import com.example.demo112.dao.impl.UserDaoImpl;
import com.example.demo112.models.User;
import com.example.demo112.service.UserService;
import com.example.demo112.dao.UserDao;
import java.util.List;

public class UserServiceImpl implements UserService {
UserDao userDao= new UserDaoImpl();
    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
    @Override
    public void edit(User newUser) {
        User oldUser = userDao.get(newUser.getId());

        oldUser.setEmail(newUser.getEmail());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRoleId(newUser.getRoleId());

            // THEM ANH MOI
//            oldUser.setAvatar(newUser.getAvatar());


        userDao.edit(oldUser);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public List<User> search(String username) {
        return userDao.search(username);
    }

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public boolean register(String username,String email, String password ) {
//        if (userDao.checkExistUsername(username)) {
//            return false;
//        }
//        else
//        {
            userDao.insert(new User(username, email, password));
            return true;
//       }
    }


    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }
}

