package core.models;

import lombok.Data;

@Data
public class Root {
    public int id;
    public String name;
    public String username;
    public String email;
    public Address address;
    public String phone;
    public String website;
    public Company company;
}
