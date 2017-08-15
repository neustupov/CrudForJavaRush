package ru.neustupov.service.impl;

import org.springframework.stereotype.Service;

import ru.neustupov.dao.UserDao;
import ru.neustupov.model.User;
import ru.neustupov.service.UserService;

import java.util.List;

//Аннотация обьявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    @Override
    public void add(User user) {

    }

    @Override
    public String edit(User user) {
        return null;
    }

    @Override
    public String delete(int userId) {
        return null;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public int getAllUserNumber() {
        return 0;
    }

    @Override
    public List showOnePage(int page, int recordsPerPage) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }
}
