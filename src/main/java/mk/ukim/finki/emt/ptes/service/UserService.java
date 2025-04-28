package mk.ukim.finki.emt.ptes.service;

import mk.ukim.finki.emt.ptes.model.User;

import java.util.List;

public interface UserService {
    User createFactory();
    List<User> findAll();
    List<User> createUsers(int number);
    List<User> buy();
    void addHistoryLog(int a, int b);
}
