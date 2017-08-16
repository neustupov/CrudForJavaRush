package ru.neustupov.service.impl;

import org.springframework.stereotype.Service;

import ru.neustupov.dao.UserDao;
import ru.neustupov.model.User;
import ru.neustupov.service.UserService;

import java.util.List;

/**
 * Service - Аннотация обьявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 */

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public String edit(User user) {
        return userDao.edit(user);
    }

    @Override
    public String delete(int userId) {
        return userDao.delete(userId);
    }

    @Override
    public User getUser(int userId) {
        return userDao.getUser(userId);
    }

    @Override
    public int getAllUserNumber() {
        return userDao.getAllUserNumber();
    }

    @Override
    public List showOnePage(int page, int recordsPerPage) {
        return userDao.showOnePage(page, recordsPerPage);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }
}
