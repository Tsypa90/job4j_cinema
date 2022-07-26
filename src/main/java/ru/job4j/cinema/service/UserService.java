package ru.job4j.cinema.service;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.UserDbStore;

import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class UserService {
    private final UserDbStore store;

    public Optional<User> addUser(User user) {
        return store.addUser(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return store.findUserByEmail(email);
    }
}
