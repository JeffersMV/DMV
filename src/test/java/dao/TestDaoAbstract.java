package dao;

import org.junit.Assert;
import org.junit.Test;
import sql.AudioDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public class TestDaoAbstract {




    @Test
    public void getAll() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery());
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);

    }

    @Test
    public void getEntityByK() throws Exception {
        Properties prop = new Properties();
        prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("testConnect.properties"));
        Connection connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        AudioDAO audioDAO = new AudioDAO(connection);
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery()+" WHERE id = ?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}