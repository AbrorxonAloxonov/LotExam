package org.example.model;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseModel {
    private Integer id;
    private Date created_date;
}
