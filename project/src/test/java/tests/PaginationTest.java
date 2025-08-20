package tests;

import core.clients.APIClient;
import core.models.ResponsePostsPageLimit;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginationTest {
    private APIClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
    }

    Set<Integer> allPostIds = new HashSet<>();

    @Test
    public void testPagination() {
        final int limit = 5;
        for (int page = 1; page <= 3; page++) {
            Response response = apiClient.getPostsPage(page, limit);

            step("Проверка статус-кода");
            assertEquals(200, response.getStatusCode(), "Статус код должен быть 200");

            step("Извлекаем id всех постов на странице");
            List<ResponsePostsPageLimit> posts = response.as(new TypeRef<List<ResponsePostsPageLimit>>() {
            });

            step("Проверяем, что кол-во постов равно лимиту");
            assertEquals(limit, posts.size(), "Должно быть " + limit + " постов на странице " + page);

            step("Проверяем, что посты не повторяются между страницами");
            for (Integer id : posts.stream().map(ResponsePostsPageLimit::getId).toList()) {
                assertTrue(allPostIds.add(id), "Дублирующийся пост найден: " + id + " (страница " + page + ")");
            }
        }

        step("Итог: проверяем общее кол-во уникальных постов за 3 страницы");
        assertEquals(3 * limit, allPostIds.size(), "Ожидалось " + (3 * limit) + " уникальных постов");
    }
}

