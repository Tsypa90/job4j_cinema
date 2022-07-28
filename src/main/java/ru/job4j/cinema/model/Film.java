package ru.job4j.cinema.model;


import java.util.*;
import java.util.stream.IntStream;

public class Film {
    private int id;
    private String name;

    private List<Integer> rows = new ArrayList<>();

    private List<Integer> seats = new ArrayList<>();

    public Film() {
    }

    public Film(int id, String name) {
        this.id = id;
        this.name = name;
        IntStream.range(1, 4).forEach(i -> {
            rows.add(i);
            seats.add(i);
        });
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Integer> getRows() {
        return rows;
    }

    public Collection<Integer> getSeats() {
        return seats;
    }
}
