package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDbStore {
    private static final Logger LOG = LogManager.getLogger(SessionDbStore.class.getName());
    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> buyTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("insert into ticket (session_id, pos_row, cell, user_id) values (?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ticket.getId() == 0 ? Optional.empty() : Optional.of(ticket);
    }

    public List<Ticket> findTicketsByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("select * from ticket where user_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"), rs.getInt("session_id"), rs.getInt("pos_row"),
                            rs.getInt("cell"), rs.getInt("user_id")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }
}
