package core.models;

import lombok.Data;

@Data
public class ResponsePostsPageLimit {
    public int userId;
    public int id;
    public String title;
    public String body;
}
