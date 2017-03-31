package dao;

import java.sql.*;
import java.util.List;

public abstract class DaoAbstract<E>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    protected DaoAbstract(Connection connection) throws DaoException, SQLException {
        this.connection = connection;
    }

    protected abstract String getSelectQuery();
    protected abstract List<E> parseResultSet(ResultSet rs) throws DaoException;


    public List<E> getAll() throws DaoException {
        List<E> entityList;
        preparedStatement = getPrepareStatement(getSelectQuery(), preparedStatement);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            entityList = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return entityList;
    }


    private PreparedStatement getPrepareStatement(String sql, PreparedStatement preparedStatement) throws DaoException {
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        return preparedStatement;
    }
}
