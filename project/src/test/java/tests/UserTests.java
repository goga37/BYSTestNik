package tests;

import io.restassured.common.mapper.TypeRef;
import core.models.ResponseGetUser;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SoftAssertionsExtension.class)
public class UserTests extends BaseApiTest {

    @Test
    void testGetUsers(SoftAssertions softly) {
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

        for (int i = 0; i < responseGetUsers.size(); i++) {
            ResponseGetUser user = responseGetUsers.get(i);

            softly.assertThat(user.getName())
                    .as("User[%s].name должен быть заполнен", i)
                    .isNotBlank();

            softly.assertThat(user.getEmail())
                    .as("User[%s].email должен быть заполнен", i)
                    .isNotBlank();
        }
    }
}
