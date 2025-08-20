package tests;

import io.restassured.common.mapper.TypeRef;
import core.clients.APIClient;
import core.models.ResponseGetUser;
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

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
    }

    @Test
    void testGetUsers() {
        step("Отправка запроса на получения списка пользователей");
        Response response = apiClient.getUsers();

        step("Проверка статус-кода");
        assertEquals(200, response.getStatusCode(), "Статус код должен быть 200");

        step("Десериализация JSON-ответа в объект ResponseGetUser");
        List<ResponseGetUser> responseGetUsers = response.as(new TypeRef<List<ResponseGetUser>>() {
        });

        step("Проверяем, что список не пустой");
        assertThat(responseGetUsers).isNotEmpty();

        step("У каждого пользователя заполнены name и email");
        for (ResponseGetUser responseGetUser : responseGetUsers) {
            assertNotNull(responseGetUser.getName());
            assertNotNull(responseGetUser.getEmail());
        }
    }
}
