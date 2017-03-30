package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try (ResultSet rs = preparedStatement.executeQuery()) {
            entityList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return entityList;
    }


    private PreparedStatement getPrepareStatement(String sql, PreparedStatement preparedStatement) throws DaoException {
        if (preparedStatement == null) {
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {
            try {
                preparedStatement.clearParameters();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return preparedStatement;
    }
}
