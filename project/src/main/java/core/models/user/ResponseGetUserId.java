package core.models.user;

import lombok.Data;

@Data
public class ResponseGetUserId {
    public int userId;
    public int id;
    public String title;
    public String body;
}
