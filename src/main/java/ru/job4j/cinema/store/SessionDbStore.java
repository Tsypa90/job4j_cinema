package ru.job4j.cinema.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository
@ThreadSafe
public class SessionDbStore {
    private static final Logger LOG = LogManager.getLogger(SessionDbStore.class.getName());
    private final BasicDataSource pool;

    public SessionDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from sessions order by id ASC")
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film(rs.getInt("id"), rs.getString("name"));
                    films.add(film);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return films;
    }
    
    public Film findSessionById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from sessions where id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Film(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public List<Integer> findRows(int id) {
        List<Integer> rows = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select distinct row from cinema where session_id = ? order by row asc")
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(rs.getInt("row"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rows;
    }

    public List<Integer> findSeats(int id, int row) {
        List<Integer> seats = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("select distinct seat from cinema where row = ? and session_id = ? order by seat asc")
        ) {
            ps.setInt(1, row);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seats.add(rs.getInt("seat"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return seats;
    }

    public void deleteSeat(int id, int row, int seat) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from cinema where session_id = ? and row = ? and seat = ?")
        ) {
            ps.setInt(1, id);
            ps.setInt(2, row);
            ps.setInt(3, seat);
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
