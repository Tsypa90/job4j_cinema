package ru.job4j.cinema.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDbStore;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDbStore store;

    public Optional<Ticket> buyTicket(Ticket ticket) {
        return store.buyTicket(ticket);
    }

    public List<Ticket> findTicketsByUserId(int id) {
        return store.findTicketsByUserId(id);
    }
}
