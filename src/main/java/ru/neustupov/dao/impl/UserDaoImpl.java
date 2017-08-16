package ru.neustupov.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.neustupov.dao.UserDao;
import ru.neustupov.model.User;

import java.util.List;

/**
 * Repository - (Доменный слой) Аннотация показывает, что класс функционирует как репозиторий и требует наличия
 * прозрачной трансляции исключений. Преимуществом трансляции исключений является то, что слой сервиса будет иметь
 * дело с общей иерархией исключений от Spring (DataAccessException) вне зависимости от используемых технологий
 * доступа к данным в слое данных.
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired                                               //создаёт и подгружает объект SessionFactory
    private SessionFactory session;

    @Override
    public void add(User user) {
        session.getCurrentSession().save(user);
    }

    @Override
    public String edit(User user) {
        try {
            session.getCurrentSession().update(user);
        } catch (Exception e) {
            return "Problem with update the user";
        }
        return "Update complete";
    }

    @Override
    public String delete(int userId) {
        try {
            session.getCurrentSession().delete(getUser(userId));
        } catch (Exception e) {
            return "Problem with delete the user";
        }
        return "Delete user with Id " + userId + " complete";
    }

    @Override
    public User getUser(int userId) {
        return (User) session.getCurrentSession().get(User.class, userId);
    }

    /**
     * Функция COUNT(*) возвращает количество строк в указанной таблице, не отбрасывая дублированные строки.
     * Она подсчитывает каждую строку отдельно. При этом учитываются и строки, содержащие значения NULL.
     * Используем метод uniqueResult, который вернет объект Number. А в последнем есть метод intValue()
     */

    @Override
    public int getAllUserNumber() {
        return ((Long) session.getCurrentSession().createQuery("SELECT COUNT(*) from User")
                .uniqueResult()).intValue();
    }

    /**
     * Создаём объект Query, который содержит все записи таблицы User. Устанавливаем начало и конец выборки.
     * Возвращаем выбраный фрагмент в виде листа.
     */

    @Override
    public List showOnePage(int page, int recordsPerPage) {
        int begin;
        begin=(page-1)*recordsPerPage;

        Query query = session.getCurrentSession().createQuery("from User");
        query.setFirstResult(begin);
        query.setMaxResults(recordsPerPage);
        return query.list();
    }

    /**
     *  FROM - откуда ( из какой базы ), AS - как ( используется для именования результирующих столбцов при выборке
     *  элементов ), WHERE - где( применяется для задания критериев отбора ), LIKE - устанавливает соответствие
     *  символьной строки с шаблоном.
     */

    @Override
    public User getUserByName(String name) {
        String query="from User as user where user.name like '"+name+"'";
        try {
            return (User) session.getCurrentSession().createQuery(query).list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
