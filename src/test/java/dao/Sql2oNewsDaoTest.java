package dao;

import models.Department_News;
import models.Departments;
import models.News;
import models.Users;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oNewsDaoTest {

    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Sql2oNewsDao sql2oNewsDao;
    private static Connection conn;

    @BeforeEach
    public void setUp() throws Exception {
        //uncomment the two lines below to run locally and change to your  credentials
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/organisational_news_portal_test",
                "moringa", "pass123");
        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        sql2oNewsDao=new Sql2oNewsDao(sql2o);
        System.out.println("connected to database");
        conn=sql2o.open();
    }
    @AfterEach
    public void tearDown() throws Exception {
        sql2oDepartmentsDao.clearAll();
        sql2oUsersDao.clearAll();
        sql2oNewsDao.clearAll();
        System.out.println("clearing database");
    }
    @AfterAll
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addNews() {
        Users users=setUpNewUsers();
        sql2oUsersDao.add(users);
        Departments departments=setUpDepartment();
        sql2oDepartmentsDao.add(departments);
        News news=new News("Meeting","Meeting to set activities for team building",users.getId());
        sql2oNewsDao.addNews(news);

        assertEquals(users.getId(),sql2oNewsDao.findById(news.getId()).getUser_id());
        assertEquals(news.getDepartment_id(),sql2oNewsDao.findById(news.getId()).getDepartment_id());
    }

    @Test
    public void addDepartmentNews() {
        Users users=setUpNewUsers();
        sql2oUsersDao.add(users);
        Departments departments=setUpDepartment();
        sql2oDepartmentsDao.add(departments);
        Department_News department_news =new Department_News("Meeting","To nominate new chairman",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        assertEquals(users.getId(),sql2oNewsDao.findById(department_news.getId()).getUser_id());
        assertEquals(department_news.getDepartment_id(),sql2oNewsDao.findById(department_news.getId()).getDepartment_id());

    }

    @Test
    public void getAll() {
        Users users=setUpNewUsers();
        sql2oUsersDao.add(users);
        Departments departments=setUpDepartment();
        sql2oDepartmentsDao.add(departments);
        Department_News department_news =new Department_News("Meeting","To nominate new chairman",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        News news=new News("Meeting","Meeting to set activities for team building",users.getId());
        sql2oNewsDao.addNews(news);
        assertEquals(2,sql2oNewsDao.getAll().size());
    }

    private Departments setUpDepartment() {
        return new Departments("Editing","editing of books");
    }
    private Users setUpNewUsers() {
        return new Users("Tevin Otieno","CTO","Editor");
    }

}
