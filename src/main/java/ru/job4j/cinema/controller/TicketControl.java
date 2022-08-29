package ru.job4j.cinema.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TicketControl {
    private final TicketService service;
    private final SessionService sessionService;

    @GetMapping("/selectFilm")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        model.addAttribute("user", user);
        model.addAttribute("films", sessionService.findAll());
        return "selectFilm";
    }

    @GetMapping("/selectRow")
    public String selectRow(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        int filmId = (Integer) session.getAttribute("filmId");
        model.addAttribute("user", user);
        model.addAttribute("rows", sessionService.getCinemaRows(filmId));
        return "selectRow";
    }

    @GetMapping("/selectSeat")
    public String selectSeat(Model model, HttpSession session,
                             @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        int filmId = (int) session.getAttribute("filmId");
        int row = (Integer) session.getAttribute("row");
        model.addAttribute("user", user);
        model.addAttribute("seats", sessionService.getCinemaSeats(filmId, row));
        model.addAttribute("fail", fail != null);
        return "selectSeat";
    }

    @GetMapping("/myTickets")
    public String myTicket(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        model.addAttribute("user", user);
        model.addAttribute("myTickets", service.findTicketsByUserId(user.getId()));
        model.addAttribute("film", sessionService);
        return "myTickets";
    }

    @PostMapping("/selectFilm")
    public String selectFilm(@RequestParam("sessionId") Integer id, HttpSession session) {
        session.setAttribute("filmId", id);
        return "redirect:/selectRow";
    }

    @PostMapping("/selectRow")
    public String selectRow(@RequestParam("row") Integer row, HttpSession session) {
        session.setAttribute("row", row);
        return "redirect:/selectSeat";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam("seat") Integer id, HttpSession session) {
        Ticket ticket = new Ticket();
        User user = (User) session.getAttribute("user");
        int filmId = (int) session.getAttribute("filmId");
        int row = (int) session.getAttribute("row");
        ticket.setUserId(user.getId());
        ticket.setSessionId(filmId);
        ticket.setRow(row);
        ticket.setCell(id);
        Optional<Ticket> buyTicket = service.buyTicket(ticket);
        if (buyTicket.isEmpty()) {
            return "redirect:/selectSeat?fail=true";
        }
        sessionService.deleteSeat(ticket);
        return "redirect:/myTickets";
    }
}
