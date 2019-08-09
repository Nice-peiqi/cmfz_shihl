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
public class Chapter {
    private String id;
    private String url;
    private String size;
    private String duration;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date up_date;
    private String headline;
    private String album_id;
}
