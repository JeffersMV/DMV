package sql;

import dao.DaoFactory;
import dto.AudioDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestAudioDAO {
    @Test
    public void getSelectQuery() throws Exception {
        AudioDAO audioDAO = new AudioDAO(DaoFactory.getConnection());
        String selectQuery = audioDAO.getSelectQuery();
        assertEquals("SELECT * FROM audios", selectQuery);
    }

    @Test
    public void parseResultSet() throws Exception {
        AudioDAO audioDAO = new AudioDAO(DaoFactory.getConnection());
        List<AudioDTO> audioDTOList = audioDAO.parseResultSet(DaoFactory.getConnection().prepareStatement(audioDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }
}