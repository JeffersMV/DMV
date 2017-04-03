package sql;

import dao.DaoFactory;
import dto.AudioDTO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class TestAudioDAO {
    @Test
    public void getSelectQuery() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        String selectQuery = audioDAO.getSelectQuery();
        assertEquals("SELECT * FROM audios", selectQuery);
    }

    @Test
    public void parseResultSet() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        List<AudioDTO> audioDTOList = audioDAO.parseResultSet(connection.prepareStatement(audioDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }
}