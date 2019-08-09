package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Breed;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.BreedService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BreedServiceImplTest {

    private PetPooledConnection connection;
    private BreedService service;

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"),
                        resourceBundle.getString("dbPassword")));
        service = new BreedServiceImpl(connection);
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.getConnection().close();
    }

    @Test
    public void testGetAll() throws PersistentException {
        List<Breed> expected = new ArrayList<>();
        expected.add(new Breed(1, "Alaskan husky", "Big Ears and Strong", "Germany"));
        expected.add(new Breed(2, "Bearded Collie", "Big Ears and Strong", "USA"));
        expected.add(new Breed(3, "German Shepherd", "Big Ears and Strong", "Germany"));
        expected.add(new Breed(4, "Chihuahua", "Small, Weak and Nimble", "Mexico"));
        List<Breed> actual = service.getAll();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetByID() throws PersistentException {
        Breed expected = new Breed(1, "Alaskan husky",
                "Big Ears and Strong", "Germany");
        Breed actual = service.getByID(1);
        Assert.assertEquals(actual, expected);
    }
}