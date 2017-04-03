package dao;

import org.junit.Assert;
import org.junit.Test;
import sql.AudioDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TestDaoAbstract {

    @Test
    public void getAll() throws Exception {
        AudioDAO audioDAO = new AudioDAO(DaoFactory.getConnection());
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery());
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);

    }

    @Test
    public void getEntityByK() throws Exception {
        AudioDAO audioDAO = new AudioDAO(DaoFactory.getConnection());
        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery()+" WHERE id = ?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        List audioDTOList = audioDAO.parseResultSet(resultSet);
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}