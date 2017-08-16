package ru.neustupov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.neustupov.model.User;
import ru.neustupov.service.UserService;

import java.util.Map;

/**
 * Controller - (Слой представления) Аннотация для маркировки java класса, как класса контроллера.
 * Данный класс представляет собой компонент, похожий на обычный сервлет (HttpServlet) (работающий с объектами
 * HttpServletRequest и HttpServletResponse), но с расширенными возможностями от Spring Framework.
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private int recordsPerPage = 3;
    private int numberOfPages, allDataSize, page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * RequestMapping - Аннотация используется для маппинга урл-адреса запроса на указанный метод или класс.
     * Можно указывать конкретный HTTP-метод, который будет обрабатываться (GET/POST), передавать параметры запроса.
     *
     * ModelAttribute - Аннотация, связывающая параметр метода или возвращаемое значение метода с атрибутом модели,
     * которая будет использоваться при выводе jsp-страницы.
     */

    @RequestMapping("/")
    public String someAction(@ModelAttribute User model, Map<String, Object> map) {
        page = 1;
        User user = new User();

        allDataSize = userService.getAllUserNumber();

        numberOfPages = allDataSize % recordsPerPage == 0 ? allDataSize / recordsPerPage :
                allDataSize / recordsPerPage + 1;

        map.put("user", user);
        map.put("userList", userService.showOnePage(page, recordsPerPage));
        map.put("currentPage", getPage());
        map.put("noOfPages", numberOfPages);

        return "user";
    }
}
