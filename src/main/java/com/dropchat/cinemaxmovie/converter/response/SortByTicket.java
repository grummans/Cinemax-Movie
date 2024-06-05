package com.dropchat.cinemaxmovie.converter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortByTicket {
    private int id;
    private String name;
    private int quantity;
}
