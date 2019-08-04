package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdoptionServiceImplTest {

    private  PetPooledConnection connection;
    private AdoptionService service;

    @BeforeMethod
    public void setUp() throws SQLException,ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"),resourceBundle.getString("dbPassword")));
    }

    @AfterMethod
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testGet() {
        AdoptionService service = new AdoptionServiceImpl(connection);
        try {
            Adoption actual = service.get(1);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAll() {
    }

    @Test
    public void testGetAllBetweenDates() {
    }

    @Test
    public void testGetAllPetName() {
    }

    @Test
    public void testGetAllCurrentUser() {
    }

    @Test
    public void testGetAllCountCurrentUser() {
    }

    @Test
    public void testGetCountPetName() {
    }

    @Test
    public void testGetAllPetNameCurrentUser() {
    }

    @Test
    public void testGetCountPetNameCurrentUser() {
    }

    @Test
    public void testGetAllBetweenDatesCurrentUser() {
    }

    @Test
    public void testGetAllCount() {
    }

    @Test
    public void testGetCountBetweenDates() {
    }

    @Test
    public void testGetCountBetweenDatesCurrentUser() {
    }

    @Test
    public void testAdopt() {
    }

    @Test
    public void testUpdateAdoption() {
    }

    @Test
    public void testDeleteAdoption() {
    }
}