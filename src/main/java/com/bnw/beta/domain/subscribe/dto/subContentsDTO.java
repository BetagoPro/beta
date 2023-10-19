package com.bnw.beta.domain.subscribe.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

//나의 구독상품 조회
@Data
public class subContentsDTO {
    private int game_no;
    private int pay_no;
    private int group_no;
    private int game_price;
    private Date pay_date;
    private String game_date;
    private String pay_type;
    private String member_id;
    private String group_name;
    private String game_title;
    private String game_level;
    private LocalDate endDate;
}
