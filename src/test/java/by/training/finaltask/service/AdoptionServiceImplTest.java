package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

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
        service = new AdoptionServiceImpl(connection);
    }

    @AfterMethod
    public void tearDown() throws SQLException {
        connection.close();
    }

    //todo: test on this updated version: adoptions_made id 2 (1, '2019-01-10', '2019-02-02', 27)

    @Test
    public void testGet() {
        try {
            Adoption actual = service.get(4);
            GregorianCalendar expectedStart = new GregorianCalendar();
            expectedStart.setTime(new Date(119,6,1));
            GregorianCalendar expectedEnd = new GregorianCalendar();
            expectedEnd.setTime(new Date(119,6,22));
            Adoption expected = new Adoption(4,1,expectedStart,expectedEnd,14);
            Assert.assertEquals(actual,expected);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAll() {

    }

    @DataProvider
    public Object[][] gettestGetAllBetweenDatesData(){
        //Test For Interval around adoption end
        GregorianCalendar start1 = new GregorianCalendar(2019,3,1);
        GregorianCalendar end1 = new GregorianCalendar(2019,3,2);
        List<Adoption> expectedList1 = new ArrayList<>();
        expectedList1.add(new Adoption(5,7,new GregorianCalendar(2019,Calendar.JANUARY,
                10),end1,27));
        //Test for interval around adoption start

        //Test for interval inside both adoption start and adoption end

        return new Object[][]{
                {new Object[]{start1,end1},expectedList1},
        };
    }

    @Test(dataProvider = "gettestGetAllBetweenDatesData")
    public void testGetAllBetweenDates(Object[] actualParam, List<Adoption> expected)
            throws PersistentException, InvalidFormDataException {
        List<Adoption> actual = service.getAllBetweenDates(
                (GregorianCalendar) actualParam[0],
                (GregorianCalendar) actualParam[1],
                0,5);
        Assert.assertEquals(actual,expected);
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