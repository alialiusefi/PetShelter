package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.PetService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PetServiceImplTest {

    private PetPooledConnection connection;
    private PetService service;
    private String path = "C:\\Users\\Cyber\\Pictures\\Pet Pics to test\\Dogs.jpg";

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"), resourceBundle.getString("dbPassword")));
        service = new PetServiceImpl(connection);
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.getConnection().close();
    }


    @Test
    public void testGetAllByStatus() throws InvalidFormDataException,
            PersistentException {
        /*Blob pic = getPictureBlob(path);
        List<Pet> expected = new ArrayList<>();
        expected.add(new Pet(1, "Ely", pic,
                new GregorianCalendar(2012, Calendar.MAY, 29),
                11,
                new GregorianCalendar(2014, Calendar.AUGUST, 1),
                1, 4, PetStatus.SHELTERED));
        expected.add(new Pet(4, "Stephie", pic,
                new GregorianCalendar(2014, Calendar.MAY, 14)
                , 14, new GregorianCalendar(2015, Calendar.JANUARY,
                10), 4, 1, PetStatus.SHELTERED));
        List<Pet> actual = service.getAllByStatus(PetStatus.SHELTERED, 0, 2);
        Assert.assertEquals(actual, expected);*/
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testGetAllByPetName() {
    }

    @Test
    public void testGetAllCountByPetName() {
    }

    @Test
    public void testGetAllByShelter() {
    }

    @Test
    public void testGetAllByBreed() {
    }

    @Test
    public void testGetAllByBirthDate() {
    }

    @Test
    public void testGetAllCountByStatus() {
    }

    @Test
    public void testGetAllCountByShelter() {
    }

    @Test
    public void testGetAllCountByBreed() {
    }

    @Test
    public void testGetAllCountByBirthDate() {
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() {
    }

    private Blob getPictureBlob(String picturePath)
            throws InvalidFormDataException {
        File pictureFile = new File(picturePath);
        try {
            byte[] pictureBytes = Files.readAllBytes(pictureFile.toPath());
            if (pictureBytes.length == 0) {
                return null;
            }
            return new SerialBlob(pictureBytes);
        } catch (IOException | SQLException e) {
            throw new InvalidFormDataException("fileUploadError");
        }
    }
}