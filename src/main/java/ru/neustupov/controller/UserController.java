package ru.neustupov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
     *  CustomDateEditor формат представления даты как строки. При отправке формы на сервер, текстовое значение
     *  поля someDate
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

    @RequestMapping(value="/user.do", method=RequestMethod.POST)
    public String doActions(@ModelAttribute User user,
                            BindingResult result,
                            @RequestParam String action,
                            Map<String, Object> map){


        page=1;

        User userResult = new User();
        switch(action.toLowerCase()){	//only in Java7 you can put String in switch
            case "add":
                if (user.getName().length()==0||!user.getName().matches( "^[a-zA-Z\\s]*$")){
                    user=new User();
                    map.put("alert","Is your user Daemon? ;) ");
                }
                else if(user.getAge()<=0||user.getAge()>100){
                    user=new User();
                    map.put("alert","PLEASE input correct: Age");
                }
                else if(userService.getUserByName(user.getName())!=null){
                    user=new User();
                    String ss="This  user already exists in dataBase :(";
                    map.put("alert",ss);

                }
                else{

                    userService.add(user);
                    map.put("alert","You added user succesfully. Here we go!");
                }
                userResult = user;
                break;
            case "edit":
                try {
                    if (user.getName().length()==0||!user.getName().matches( "^[a-zA-Z\\s]*$")){
                        user=new User();
                        map.put("alert","Is your user Daemon? ;) ");
                    }
                    else if(user.getAge()<=0||user.getAge()>100){
                        user=new User();
                        map.put("alert","PLEASE input correct: Age");
                    }
                    map.put("alert",userService.edit(user));
                } catch (Exception e) {
                    map.put("alert","some problems with your input :(");
                    // TODO: handle exception
                }

                userResult = user;
                break;
            case "delete":
                map.put("alert",userService.delete(user.getId()));

                userResult = new User();
                break;
            case "search by id":
                User searcheduser;
                try {
                    searcheduser = userService.getUser(user.getId());
                    if (searcheduser==null)
                        map.put("alert","some problems with your \"ID\" input :(");
                } catch (Exception e) {
                    // TODO: handle exception
                    searcheduser = new User();
                    map.put("alert","some problems with your \"ID\" input :(");
                }

                userResult = searcheduser!=null ? searcheduser : new User();
                break;

            case "search by name":
                User searcheduserByName;
                try {
                    searcheduserByName = userService.getUserByName(user.getName());
                    if (searcheduserByName==null)
                        map.put("alert"," No such user :(");
                } catch (Exception e) {
                    // TODO: handle exception
                    searcheduserByName = new User();
                    map.put("alert","some problems with your \"NAME\" input :(");
                }

                userResult = searcheduserByName!=null ? searcheduserByName : new User();
                break;

        }

        allDataSize=userService.getAllUserNumber();
        numberOfPages=allDataSize%recordsPerPage==0?allDataSize/recordsPerPage:allDataSize/recordsPerPage+1;


        map.put("currentPage", getPage());
        map.put("user", userResult);
        map.put("userList", userService.showOnePage(page, recordsPerPage));
        map.put("noOfPages", numberOfPages);


        return "user";
    }
}
