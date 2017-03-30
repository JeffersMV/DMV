package sql;

import dao.DaoAbstract;
import dao.DaoException;
import dto.AudioDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class AudioDAO extends DaoAbstract<AudioDTO> {

    public AudioDAO(Connection connection) throws DaoException, SQLException {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {return "SELECT id, name, data, audio FROM audios";}

    @Override
    protected List<AudioDTO> parseResultSet(ResultSet rs) throws DaoException {
        List<AudioDTO> lst = new LinkedList<>();
        try {
            while (rs.next()) {
                AudioDTO audioDTO = new AudioDTO();
                audioDTO.setId(rs.getInt(1));
                audioDTO.setName(rs.getString(2));
                audioDTO.setData(rs.getDate(3));
                audioDTO.setAudio(rs.getString(4));
                lst.add(audioDTO);
            }
        } catch (Exception sqlE) {
            throw new DaoException(sqlE);
        }
        return lst;
    }
}