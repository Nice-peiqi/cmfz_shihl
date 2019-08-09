package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article implements Serializable {
    private String id;
    private String title;
    private String insert_img;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date up_date;
    private String guru_id;
    private String status;

}
