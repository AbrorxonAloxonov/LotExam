package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class  Lot extends BaseModel{
    private String model;
    private String description;
    private Double start_price;
    private User user;
    private String date;
    private boolean sold;

    public Lot(Integer id,String model, String description, Double start_price) {
        super.setId(id);
        this.model = model;
        this.description = description;
        this.start_price = start_price;
    }
    public Lot(Integer id ,String model, String description, Double start_price,String date,boolean sold){
        super.setId(id);
        this.model = model;
        this.description = description;
        this.start_price = start_price;
        this.date = date;
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }
    public Integer getId() {
        return super.getId();
    }
}
