package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.Root;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests {
    private APIClient apiClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();
    }
    @Test
    void testGetUsers() throws JsonProcessingException {
        Response response = apiClient.getUsers();

        step("Проверка статус-кода");
        assertEquals(200,response.getStatusCode(),"Статус код должен быть 200");

        step("Десериализация JSON-ответа в объект SingleUserResponse");
        String responseBody = response.getBody().asString();
        List<Root> roots = objectMapper.readValue(responseBody, new TypeReference<List<Root>>() {});

        step("Проверяем, что список не пустой");
        assertThat(roots).isNotEmpty();

        for (Root root : roots){
            assertNotNull(root.getName());
            assertNotNull(root.getEmail());
        }
    }
}
