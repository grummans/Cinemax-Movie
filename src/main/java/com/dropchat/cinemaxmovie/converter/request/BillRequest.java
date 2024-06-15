package com.dropchat.cinemaxmovie.converter.request;

import java.util.Date;
import java.util.List;

import com.dropchat.cinemaxmovie.entity.BillStatus;
import com.dropchat.cinemaxmovie.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BillRequest {

    private int id;
    private double totalMoney;
    private String tradingCode;
    private Date createTime;
    private String name;
    private Date updateTime;
    private User users;
    private int promotion;
    private BillStatus billStatus;

    private List<FoodQuantityPair> foodItems;
    private List<TicketQuantityPair> ticketItems;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FoodQuantityPair {
        private int quantity;
        private int food;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TicketQuantityPair {
        private int quantity;
        private int ticket;
    }
}
