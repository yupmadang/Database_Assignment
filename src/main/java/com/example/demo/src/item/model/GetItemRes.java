package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetItemRes {
    private int itemIdx;
    private int userIdx;
    private int categoryIdx;
    private String itemName;
    private int price;
    private String itemDescription;
    private int like;
    private int viewed;
    private int chat;
    private int tradeType;
    private String initDate;
    private String editDate;
    private int status;
}
