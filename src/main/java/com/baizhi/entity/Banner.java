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
public class Banner {
    private String id;
    private String title;
    private String img_path;
    private String description;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date up_date;
}
