package ru.neustupov.service;

import java.util.List;

import ru.neustupov.model.User;

public interface UserService {
    public void add(User user);
    public String edit(User user);
    public String delete(int userId);
    public User getUser(int userId);
    public int getAllUserNumber();
    public List showOnePage(int page,int recordsPerPage);
    public User getUserByName(String name);
}