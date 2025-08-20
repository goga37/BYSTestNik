package tests;

import core.clients.APIClient;
import core.models.ResponseGetUserId;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class GetPostsId {
    private APIClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
    }

    static IntStream ids() {
        return IntStream.rangeClosed(1, 100);
    }

    @ParameterizedTest(name = "GET /posts/{0} должен вернуть 200 и поля title/body")
    @MethodSource("ids")
    void shouldReturnPostWithTitleAndBody(int id) {
        step("Отправка запроса на получения информации о пользователе");
        Response response = apiClient.getPostsId(id);

        step("Проверка статус-кода");
        assertEquals(200, response.getStatusCode(), "Статус код должен быть 200");

        step("Десериализация JSON-ответа в объект ResponseGetUserId");
        ResponseGetUserId responseGetUserId = response.as(ResponseGetUserId.class);

        step("У пользователя заполнены title и body");
        assertNotNull(responseGetUserId.getTitle());
        assertNotNull(responseGetUserId.getBody());
    }

    @Test
    void shouldReturn404AndEmptyBodyForIdGreaterThan100() {
        step("Отправка запроса на получения информации о несуществующем пользователе");
        Response response = apiClient.getPostsId(101);

        step("Проверка статус-кода");
        assertEquals(404, response.getStatusCode(), "Статус код должен быть 404");

        step("Допускаем пустую строку или пустой JSON-объект");
        String body = response.asString();
        assertTrue(body == null || body.isBlank() || body.trim().equals("{}"),
                "Тело ответа должно быть пустым или {}. Actual:");
    }
}
