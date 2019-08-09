package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.UserInfoService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class UserInfoServiceImplTest {

    private PetPooledConnection connection;
    private UserInfoService service;

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"), resourceBundle.getString("dbPassword")));
        service = new UserInfoServiceImpl(connection);
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.getConnection().close();
    }


    @Test
    public void testFindAll() throws PersistentException {
        List<UserInfo> expected = new ArrayList<>(2);
        expected.add(new UserInfo(1, "abresson0@mozilla.org", "Aile",
                "Bresson", new GregorianCalendar(1985, Calendar.FEBRUARY, 23)
                , "41637 Packers Trail", 4124700460L));
        expected.add(new UserInfo(2, "bboays1@t-online.de", "Bartholomeus",
                "Boays", new GregorianCalendar(1996, 8, 25),
                "6835 Bashford Center", 5044524503L));
        List<UserInfo> actual = service.findAll(0, 2);
        Assert.assertEquals(actual, expected);

    }

    @Test
    public void testGet() throws PersistentException {
        UserInfo expected = new UserInfo(1, "abresson0@mozilla.org", "Aile",
                "Bresson", new GregorianCalendar(1985, Calendar.FEBRUARY, 23)
                , "41637 Packers Trail", 4124700460L);
        UserInfo actual = service.get(expected.getId());
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAdd() throws PersistentException, InvalidFormDataException {
        User user = new User(null, "ali111",
                "5f4dcc3b5aa765d61d8327deb882cf99", Role.GUEST);
        UserServiceImpl userService = new UserServiceImpl(connection);
        int id = userService.register(user);
        UserInfo expected = new UserInfo(id, "email@live.com",
                "Ali", "Ghanem",
                new GregorianCalendar(1985, Calendar.FEBRUARY, 23)
                , "41637 Minsk Belarus", 4111200460L);
        service.add(expected);
        UserInfo actual = service.get(id);
        userService.delete(id);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdate() throws PersistentException {
        UserInfo expected = new UserInfo(1, "abresson0@mozilla.org",
                "Ali", "Bresson",
                new GregorianCalendar(1985, Calendar.FEBRUARY, 23),
                "41637 Packers Trail", 4124700460L);
        service.update(expected);
        UserInfo actual = service.get(expected.getId());
        UserInfo revert = new UserInfo(1, "abresson0@mozilla.org",
                "Aile", "Bresson",
                new GregorianCalendar(1985, Calendar.FEBRUARY, 23),
                "41637 Packers Trail", 4124700460L);
        service.update(revert);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testDelete() throws PersistentException {
        UserInfo revert = new UserInfo(1, "abresson0@mozilla.org",
                "Aile", "Bresson",
                new GregorianCalendar(1985, Calendar.FEBRUARY, 23),
                "41637 Packers Trail", 4124700460L);
        service.delete(revert.getId());
        UserInfo actual = service.get(revert.getId());
        service.add(revert);
        Assert.assertNull(actual);
    }

    @Test
    public void testFindAllStaff() throws PersistentException {
        List<UserInfo> expected = new ArrayList<>();
        expected.add(new UserInfo(2, "bboays1@t-online.de", "Bartholomeus",
                "Boays", new GregorianCalendar(1996, 8, 25),
                "6835 Bashford Center", 5044524503L));
        expected.add(new UserInfo(5, "mknell4@quantcast.com", "Maje",
                "Knell", new GregorianCalendar(2000, 5, 12),
                "6914 Kingsford Plaza", 3595407361L));
        List<UserInfo> actual = service.findAllStaff(0, 2);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindAllStaffByFirstName() throws PersistentException {
        List<UserInfo> expected = new ArrayList<>();
        expected.add(new UserInfo(2, "bboays1@t-online.de", "Bartholomeus",
                "Boays", new GregorianCalendar(1996, 8, 25),
                "6835 Bashford Center", 5044524503L));
        List<UserInfo> actual = service.findAllStaffByFirstName("%Bartholomeus%", 0, 1);
        Assert.assertEquals(actual, expected);

    }

    @Test
    public void testFindAllStaffByPhone() throws PersistentException {
        List<UserInfo> expected = new ArrayList<>();
        expected.add(new UserInfo(2, "bboays1@t-online.de", "Bartholomeus",
                "Boays", new GregorianCalendar(1996, 8, 25),
                "6835 Bashford Center", 5044524503L));
        List<UserInfo> actual = service.findAllStaffByPhone(5044524503L, 0, 1);
        Assert.assertEquals(actual, expected);

    }

}