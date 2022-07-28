package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.SessionDbStore;

import java.util.*;
import java.util.stream.IntStream;

@Service
@ThreadSafe
public class SessionService {
    private final SessionDbStore store;

    private Map<Integer, List<Integer>> seats = new HashMap<>();

    public SessionService(SessionDbStore store) {
        this.store = store;
        IntStream.range(1, 4).forEach(i -> {
            seats.put(i, new ArrayList<>());
            IntStream.range(1, 4).forEach(y -> seats.get(i).add(y));
        });
    }

    public List<Film> findAll() {
        return store.findAll();
    }

    public Film findSessionById(int id) {
        return store.findSessionById(id);
    }

    public Collection<Integer> getRows() {
        return new ArrayList<>(seats.keySet());
    }

    public void deleteSeat(Ticket ticket) {
        findSessionById(ticket.getSessionId()).getSeats().remove(ticket.getCell());
    }
}
