package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @EqualsAndHashCode.Include
    private int id;
    private int sessionId;
    private int row;
    private int cell;
    private int userId;
}
