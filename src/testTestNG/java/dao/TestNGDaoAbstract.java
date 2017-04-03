package dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import sql.AudioDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public class TestNGDaoAbstract {

    @Test
    public void testGetAll() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testNGConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery());
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", String.valueOf(audioDTOList));
    }

    @Test
    public void testGetEntityByK() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testNGConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery()+" WHERE id = ?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", String.valueOf(audioDTOList));
    }

}