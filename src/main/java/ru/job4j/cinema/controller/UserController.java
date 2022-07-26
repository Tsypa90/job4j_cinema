package ru.job4j.cinema.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/registration")
    public String registration(Model model, HttpSession session,
                               @RequestParam(name = "fail", required = false) Boolean fail,
                               @RequestParam(name = "length", required = false) Boolean length) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        model.addAttribute("length", length != null);
        return "registration";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = service.findUserByEmail(user.getEmail());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/selectFilm";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        if (user.getName().equals("") || user.getEmail().equals("") || user.getPhone().equals("")) {
            return "redirect:/registration?length=true";
        }
        Optional<User> regUser = service.addUser(user);
        if (regUser.isEmpty()) {
            return "redirect:/registration?fail=true";
        }
        return "redirect:/loginPage";
    }


}
