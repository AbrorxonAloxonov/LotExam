package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel{
    private String name;
    private String last_name;
    private String username;
    private boolean role;
    private String password;
    private String phone_number;

    public User(Integer id ,String name, String last_name, String username,String phone_number,boolean role) {
        super.setId(id);
        this.name = name;
        this.last_name = last_name;
        this.username = username;
        this.phone_number = phone_number;
        this.role = role;
    }
    public User(Integer id ,String name, String last_name, String username,String phone_number){
        super.setId(id);
        this.name = name;
        this.last_name = last_name;
        this.username = username;
        this.phone_number = phone_number;
    }

    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
    }
}

