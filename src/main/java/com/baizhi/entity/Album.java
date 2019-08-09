package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Album {
    private String id;
    private String title;
    private String author;
    private String score;
    private String cover_img;
    private String broadcast;
    private Integer count;
    private String centent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pub_date;

}
