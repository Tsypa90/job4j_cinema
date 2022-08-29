package ru.job4j.cinema.model;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Film {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
