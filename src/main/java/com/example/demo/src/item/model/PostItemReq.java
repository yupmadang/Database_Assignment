package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostItemReq {
    private long itemIdx;
    private long userIdx;
    private long categoryIdx;
    private String itemName;
    private int price;
    private String itemDescription;
    private int likeCount;
    private int viewed;
    private int chat;
    private int tradeType;
    private String initDate;
    private String editDate;
    private int status;
}
