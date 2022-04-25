package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = Util.getSessionFactory();
    private Session session;

    public UserDaoHibernateImpl() {
        openConnection();
    }

    @Override
    public void openConnection() {
        session = sessionFactory.openSession();
    }

    @Override
    public void closeConnection() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT not NULL, " +
                    " name VARCHAR(255), " +
                    " lastname VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY (id))";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            userList = session.createQuery("select u from " + User.class.getSimpleName() + " u", User.class).getResultList();
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
            userList = Collections.emptyList();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from " +  User.class.getSimpleName()).executeUpdate();
            transaction.commit();
        }catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
