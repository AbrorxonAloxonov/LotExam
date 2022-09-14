package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Offer extends BaseModel{
    private Integer lot_id;
    private User user;
    private Double price;
    private String date;

    public Offer(Integer id ,Integer lot_id, User user, Double price) {
        super.setId(id);
        this.lot_id = lot_id;
        this.user = user;
        this.price = price;
    }

    public Offer(Integer id, Integer lot_id,User user, Double price, String date) {
        super.setId(id);
        this.lot_id = lot_id;
        this.user = user;
        this.price = price;
        this.date = date;
    }

    public Offer(Integer id, Integer lot_id,Double price, String date) {
        super.setId(id);
        this.lot_id = lot_id;
        this.price = price;
        this.date = date;
    }
    public Offer(Integer lot_id, User user, Double price) {
        this.lot_id = lot_id;
        this.user = user;
        this.price = price;
    }
}

