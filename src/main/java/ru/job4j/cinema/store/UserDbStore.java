package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserDbStore {
    private static final Logger LOG = Logger.getLogger(UserDbStore.class.getName());
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> addUser(User user) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("insert into users (username, email, phone) values (?, ?, ?)",
        PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user.getId() == 0 ? Optional.empty() : Optional.of(user);
    }

    public Optional<User> findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("select * from users where email like ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(rs.getInt("id"), rs.getString("username"),
                            rs.getString("email"), rs.getString("phone")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
