package sql;

import dao.DaoFactory;
import dto.AudioDTO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class TestNGAudioDAO{

    @Test
    public void testGetSelectQuery() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testNGConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        String selectQuery = audioDAO.getSelectQuery();
        Assert.assertEquals("SELECT * FROM audios", selectQuery);
    }

    @Test
    public void testParseResultSet() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testNGConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        List<AudioDTO> audioDTOList = audioDAO.parseResultSet(connection.prepareStatement(audioDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", String.valueOf(audioDTOList));
    }

}