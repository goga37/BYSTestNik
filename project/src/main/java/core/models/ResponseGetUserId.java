package core.models;

import lombok.Data;

@Data
public class ResponseGetUserId {
    public int userId;
    public int id;
    public String title;
    public String body;
}
