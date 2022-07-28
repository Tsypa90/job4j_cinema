package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TicketControl {
    private final TicketService service;
    private final SessionService sessionService;

    public TicketControl(TicketService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    @GetMapping("/buyTicket")
    public String index(Model model, HttpSession session, @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        model.addAttribute("user", user);
        model.addAttribute("films", sessionService.findAll());
        model.addAttribute("rows", sessionService.findSessionById(1).getRows());
        model.addAttribute("seats", sessionService.findSessionById(1).getSeats());
        model.addAttribute("fail", fail != null);
        return "buyTicket";
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

    @PostMapping("/buyTicket")
    public String buyTicket(@ModelAttribute Ticket ticket, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ticket.setUserId(user.getId());
        Optional<Ticket> buyTicket = service.buyTicket(ticket);
        if (buyTicket.isEmpty()) {
            return "redirect:/buyTicket?fail=true";
        }
        sessionService.deleteSeat(ticket);
        return "redirect:/myTickets";
    }
}
