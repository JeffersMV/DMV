package sql;

import dao.DaoFactory;
import dto.PhotoDTO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestPhotoDAO {
    @Test
    public void testGetSelectQuery() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        PhotoDAO photoDAO = new PhotoDAO(connection);
        String selectQuery = photoDAO.getSelectQuery();
        assertEquals("SELECT * FROM photos", selectQuery);
    }

    @Test
    public void testParseResultSet() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        PhotoDAO photoDAO = new PhotoDAO(connection);
        List<PhotoDTO> audioDTOList = photoDAO.parseResultSet(connection.prepareStatement(photoDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}
