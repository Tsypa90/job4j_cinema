package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.SessionDbStore;

import java.util.*;

@Service
@ThreadSafe
public class SessionService {
    private final SessionDbStore store;

    public SessionService(SessionDbStore store) {
        this.store = store;
    }

    public List<Film> findAll() {
        return store.findAll();
    }

    public Film findSessionById(int id) {
        return store.findSessionById(id);
    }

    public Collection<Integer> getCinemaRows(int id) {
        return store.findRows(id);
    }

    public List<Integer> getCinemaSeats(int id, int row) {
        return store.findSeats(id, row);
    }

    public void deleteSeat(Ticket ticket) {
        store.deleteSeat(ticket.getSessionId(), ticket.getRow(), ticket.getCell());
    }
}
