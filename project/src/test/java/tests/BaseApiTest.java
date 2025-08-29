package tests;

import core.clients.APIClient;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {
    protected static APIClient apiClient;

    @BeforeAll
    static void initClientOnce() {
        apiClient = new APIClient();
    }
}

