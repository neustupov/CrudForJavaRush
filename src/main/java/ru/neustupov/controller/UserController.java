package ru.neustupov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.model.User;
import ru.neustupov.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     *
     * return "user" - возвращаем имя представления
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

    /**
     *  В SpringFramework существует специальный класс CustomDateEditor , в котором реализована логика
     *  преобразования нашего объекта типа Date в строку и наоборот. Первый параметр в конструкторе
     *  CustomDateEditor формат представления даты как строки. Этот класс
     *  позаботиться что-бы nowDate из первого примера, преобразовался в
     *  указанный формат и вывелся на форму, при отправке формы на сервер, текстовое значение поля someDate
     *  будет также обрабатывать класс CustomDateEditor и преобразует его в правильную дату.
     *  Второй параметр определяет допускаются ли пустые значения в этом поле.
     *  InitBinder - проводит валидацию поступаемых данных.
     */


    @InitBinder
    public void initBinder(WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     *
     *
     *
     *
     *
     *
     */

    @RequestMapping(value="/user.do", method= RequestMethod.GET)
    public String doActions2(
            @ModelAttribute User user,
            Map<String, Object> map,
            @RequestParam("page") String inputPage
    ){

        try {
            if (inputPage.length()==0|| inputPage==null)setPage(1);
            else{
                setPage(Integer.parseInt(inputPage));
                if (getPage()<=0)setPage(1);
            }

        } catch (Exception e) {
            System.out.println("PAGE not corrected value");
        }


        allDataSize=userService.getAllUserNumber();

        numberOfPages=allDataSize%recordsPerPage==0?allDataSize/recordsPerPage:allDataSize/recordsPerPage+1;

        map.put("currentPage", getPage());
        map.put("noOfPages", numberOfPages);
        map.put("user", user);
        map.put("userList", userService.showOnePage(page, recordsPerPage));


        return "user";

    }
}
