package sql;

import dao.DaoFactory;
import dto.PhotoDTO;
import dto.VideoDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestVideoDAO {
    @Test
    public void testGetSelectQuery() throws Exception {
        VideoDAO videoDAO = new VideoDAO(DaoFactory.getConnection());
        String selectQuery = videoDAO.getSelectQuery();
        assertEquals("SELECT * FROM videos", selectQuery);
    }

    @Test
    public void testParseResultSet() throws Exception {
        VideoDAO videoDAO = new VideoDAO(DaoFactory.getConnection());
        List<VideoDTO> audioDTOList = videoDAO.parseResultSet(DaoFactory.getConnection().prepareStatement(videoDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}