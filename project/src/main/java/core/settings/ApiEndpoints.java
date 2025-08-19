package core.settings;

public enum ApiEndpoints {
    USERS("/users");

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
