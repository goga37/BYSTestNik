package tests;

import core.models.post.ResponsePostsPageLimit;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginationTest extends BaseApiTest {
    private static final int LIMIT = 5;
    private static final int TOTAL_PAGES = 3;
    Set<Integer> allPostIds = new HashSet<>();

    @Test
    public void testPagination() {
        for (int page = 1; page <= TOTAL_PAGES; page++) {
            Response response = apiClient.getPostsPage(page, LIMIT);

            step("Проверка статус-кода");
            assertEquals(200, response.getStatusCode(), "Статус код должен быть 200");

            step("Извлекаем id всех постов на странице");
            List<ResponsePostsPageLimit> posts = response.as(new TypeRef<List<ResponsePostsPageLimit>>() {
            });

            step("Проверяем, что кол-во постов равно лимиту");
            assertEquals(LIMIT, posts.size(), "Должно быть " + LIMIT + " постов на странице " + page);

            step("Проверяем, что посты не повторяются между страницами");
            for (Integer id : posts.stream().map(ResponsePostsPageLimit::getId).toList()) {
                assertTrue(allPostIds.add(id), "Дублирующийся пост найден: " + id + " (страница " + page + ")");
            }
        }

        step("Итог: проверяем общее кол-во уникальных постов за 3 страницы");
        assertEquals(TOTAL_PAGES * LIMIT, allPostIds.size(), "Ожидалось " + (TOTAL_PAGES * LIMIT) + " уникальных постов");
    }
}

