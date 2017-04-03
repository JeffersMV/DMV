package sql;

import dao.DaoFactory;
import dto.PhotoDTO;
import dto.VideoDTO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestVideoDAO {
    @Test
    public void testGetSelectQuery() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        VideoDAO videoDAO = new VideoDAO(connection);
        String selectQuery = videoDAO.getSelectQuery();
        assertEquals("SELECT * FROM videos", selectQuery);
    }

    @Test
    public void testParseResultSet() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        VideoDAO videoDAO = new VideoDAO(connection);
        List<VideoDTO> audioDTOList = videoDAO.parseResultSet(connection.prepareStatement(videoDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}