package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.service.serviceinterface.ShelterService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
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
    public void testGetAll() {
    }

    @Test
    public void testGetByID() {
    }
}