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

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class AdoptionServiceImplTest {

    private PetPooledConnection connection;
    private AdoptionService service;

    @BeforeMethod
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"), resourceBundle.getString("dbPassword")));
        service = new AdoptionServiceImpl(connection);
    }

    @AfterMethod
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testGet() throws PersistentException {
        Adoption expected = new Adoption(1,
                1,
                new GregorianCalendar(2021, 8, 2),
                null, 1);
        Adoption actual = service.get(1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAll() throws PersistentException {
        List<Adoption> expected = new ArrayList<>();
        expected.add(new Adoption(1,
                1,
                new GregorianCalendar(2021, 8, 2),
                null, 1));
        expected.add(new Adoption(2, 1,
                new GregorianCalendar(2020, 0, 10),
                new GregorianCalendar(2020, 1, 2),
                2));
        List<Adoption> actual = service.getAll(0, 2);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] gettestGetAllBetweenDatesData() {
        //Test For Interval around adoption end
        GregorianCalendar start1 = new GregorianCalendar
                (2020, Calendar.FEBRUARY, 1);
        GregorianCalendar end1 = new GregorianCalendar
                (2020, Calendar.FEBRUARY, 3);
        List<Adoption> expectedList1 = new ArrayList<>();
        expectedList1.add(new Adoption
                (2, 1,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10)
                        , new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        2));
        expectedList1.add(new Adoption
                (4, 2,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        1));
        expectedList1.add(new Adoption
                (7, 4,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        3));
        //Test for interval around adoption start
        GregorianCalendar start2 = new GregorianCalendar
                (2020, Calendar.JANUARY, 9);
        GregorianCalendar end2 = new GregorianCalendar
                (2020, Calendar.JANUARY, 11);
        List<Adoption> expectedList2 = new ArrayList<>();
        expectedList2.add(new Adoption
                (2, 1,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10)
                        , new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        2));
        expectedList2.add(new Adoption
                (4, 2,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        1));
        expectedList2.add(new Adoption(5,
                3,
                new GregorianCalendar(2020, Calendar.JANUARY, 10),
                new GregorianCalendar(2020, Calendar.APRIL, 2),
                1));
        expectedList2.add(new Adoption
                (7, 4,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        3));
        //Test for interval around both adoption start and adoption end
        GregorianCalendar start3 = new GregorianCalendar(2020, 1, 9);
        GregorianCalendar end3 = new GregorianCalendar(2020, 2, 3);
        List<Adoption> expectedList3 = new ArrayList<>();
        expectedList3.add(new Adoption(3, 1,
                new GregorianCalendar(2020, Calendar.FEBRUARY, 10),
                new GregorianCalendar(2020, Calendar.MARCH, 2),
                3));
        return new Object[][]{
                {new Object[]{start1, end1}, expectedList1},
                {new Object[]{start2, end2}, expectedList2},
                {new Object[]{start3, end3}, expectedList3}
        };
    }

    @Test(dataProvider = "gettestGetAllBetweenDatesData")
    public void testGetAllBetweenDates(Object[] actualParam, List<Adoption> expected)
            throws PersistentException, InvalidFormDataException {
        List<Adoption> actual = service.getAllBetweenDates(
                (GregorianCalendar) actualParam[0],
                (GregorianCalendar) actualParam[1],
                0, 5);
        Assert.assertEquals(actual, expected);
    }


    @Test(dataProvider = "gettestGetAllBetweenDatesData")
    public void testGetCountBetweenDates(Object[] actualParam, List<Adoption> expected) throws PersistentException, InvalidFormDataException {
        int actualSize = service.getCountBetweenDates(
                (GregorianCalendar) actualParam[0],
                (GregorianCalendar) actualParam[1]);
        Assert.assertEquals(actualSize, expected.size());
    }


    @Test
    public void testGetAllPetName() throws PersistentException {
        List<Adoption> expected = new ArrayList<>();
        expected.add(new Adoption(1,
                1,
                new GregorianCalendar(2021, 8, 2),
                null, 1));
        expected.add(new Adoption(2, 1,
                new GregorianCalendar(2020, 0, 10),
                new GregorianCalendar(2020, 1, 2),
                2));
        expected.add(new Adoption(3, 1,
                new GregorianCalendar(2020, Calendar.FEBRUARY, 10),
                new GregorianCalendar(2020, Calendar.MARCH, 2),
                3));
        List<Adoption> actual = service.getAllPetName("%Ely", 0, 3);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllCurrentUser() throws PersistentException {
        List<Adoption> expected = new ArrayList<>();
        expected.add(new Adoption
                (7, 4,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        3));
        List<Adoption> actual = service.getAllCurrentUser(7, 0, 1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllCountCurrentUser() throws PersistentException {
        int expected = 1;
        int actual = service.getAllCountCurrentUser(1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetCountPetName() throws PersistentException {
        int expected = 3;
        int actual = service.getCountPetName("%Ely%");
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllPetNameCurrentUser() throws PersistentException {
        List<Adoption> expected = new ArrayList<>();
        expected.add(new Adoption
                (7, 4,
                        new GregorianCalendar(2020, Calendar.JANUARY, 10),
                        new GregorianCalendar(2020, Calendar.FEBRUARY, 2),
                        3));
        List<Adoption> actual = service.getAllPetNameCurrentUser(4, "%Debora%", 0, 1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetCountPetNameCurrentUser() throws PersistentException {
        int expected = 1;
        int actual = service.getCountPetNameCurrentUser(4, "%Debora%");
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllBetweenDatesCurrentUser() {

    }

    @Test
    public void testGetCountBetweenDatesCurrentUser() {
    }

    @Test
    public void testGetAllCount() throws PersistentException {
        int expected = 7;
        int actual = service.getAllCount();
        Assert.assertEquals(expected, actual);

    }

    //Test for overlap around start
    //Test for overlap around end
    //Test for overlap inside
    //Test for overlap outside
    //Test for overlap with null
    @Test
    public void testAdopt() {

    }

    @Test
    public void testUpdateAdoption() throws PersistentException,
            InvalidFormDataException {
        //setup
        Adoption adoption = new Adoption(0, 5,
                new GregorianCalendar(2020, 5, 2),
                new GregorianCalendar(2020, 6, 3),
                5
        );
        int id = service.adopt(adoption);
        Adoption expected = new Adoption(0, 5,
                new GregorianCalendar(2020, 5, 2),
                new GregorianCalendar(2020, 7, 3),
                5
        );
        adoption.setId(id);
        expected.setId(id);
        //test
        adoption.setAdoptionEnd(new GregorianCalendar(2020,
                7, 4));
        service.updateAdoption(adoption);
        Adoption actual = service.get(id);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testDeleteAdoption() throws PersistentException, InvalidFormDataException {
        //setup
        Adoption adoption = new Adoption(0, 5,
                new GregorianCalendar(2020, 5, 2),
                new GregorianCalendar(2020, 6, 3),
                5
        );
        int id = service.adopt(adoption);
        adoption.setId(id);
        //test
        service.deleteAdoption(id);
        Adoption actual = service.get(id);
        Assert.assertNull(actual);
    }
}