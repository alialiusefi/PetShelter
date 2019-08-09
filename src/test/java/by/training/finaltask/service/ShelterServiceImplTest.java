package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Shelter;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.ShelterService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShelterServiceImplTest {

    private PetPooledConnection connection;
    private ShelterService service;

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"), resourceBundle.getString("dbPassword")));
        service = new ShelterServiceImpl(connection);
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.getConnection().close();
    }

    @Test
    public void testGetAll() throws PersistentException {
        List<Shelter> expected = new ArrayList<>();
        expected.add(new Shelter(1, "Manhattan Shelter", "New York"));
        expected.add(new Shelter(2, "Carbon County", "New Jersey"));
        expected.add(new Shelter(3, "Silver County", "Washington"));
        expected.add(new Shelter(4, "Scranton Main Shelter", "Scranton"));
        List<Shelter> actual = service.getAll();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetByID() throws PersistentException {
        Shelter expected = new Shelter(2, "Carbon County", "New Jersey");
        Shelter actual = service.getByID(2);
        Assert.assertEquals(actual, expected);
    }
}