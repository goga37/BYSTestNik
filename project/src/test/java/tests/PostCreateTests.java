package tests;


import core.models.ResponseCreateUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostCreateTests extends BaseApiTest {

    @Test
    void shouldCreatePostWithNameAndReturnId201() {
        step("Отправка запроса на создание сущности");
        Response response = apiClient.createPostWithName("Igor");

        step("Проверка статус-кода", () ->
                assertEquals(201, response.statusCode(), "Статус код должен быть 201")
        );

        step("Десериализация JSON-ответа в ResponseCreateUser", () -> {
        });
        ResponseCreateUser responseCreateUser = response.as(ResponseCreateUser.class);

        step("Проверка, что id есть в ответе", () ->
                assertNotNull(responseCreateUser.getId(), "ID не должен быть null")
        );

        step("Проверка, что имя совпадает", () ->
                assertEquals("Igor", responseCreateUser.getName(), "Имя должно совпадать с отправленным")
        );
    }
}
