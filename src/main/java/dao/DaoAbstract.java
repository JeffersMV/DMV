package dao;

import java.sql.*;
import java.util.List;

public abstract class DaoAbstract<E, K> {
    private Connection connection;
    private PreparedStatement preparedStatementSelAll;
    private PreparedStatement preparedStatementSelId;

    protected DaoAbstract(Connection connection) throws DaoException, SQLException {
        this.connection = connection;
    }

    protected abstract String getSelectQuery() throws DaoException;

    protected abstract List<E> parseResultSet(ResultSet rs) throws DaoException, SQLException;

    public List<E> getAll() throws DaoException {
        List<E> entityList;
        preparedStatementSelAll = getPrepareStatement(getSelectQuery(), preparedStatementSelAll);
        System.out.println(preparedStatementSelAll.toString());
        try (ResultSet resultSet = preparedStatementSelAll.executeQuery()) {
            entityList = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return entityList;
    }

    public E getEntityByK(K key) throws DaoException {
        List<E> lst;
        try {
            preparedStatementSelId = getPrepareStatement(getSelectQuery() + " WHERE id = ?", preparedStatementSelId);
            preparedStatementSelId.setInt(1, (Integer) key);
            try (ResultSet rs = preparedStatementSelId.executeQuery()) {
                lst = parseResultSet(rs);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        if (lst == null || lst.size() == 0) {
            throw new DaoException("Запись с K " + key + " не найдена.");
        } else if (lst.size() > 1) {
            throw new DaoException("Поступило более одной записи.");
        }
        return lst.iterator().next();
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
