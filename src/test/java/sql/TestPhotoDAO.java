package sql;

import dao.DaoFactory;
import dto.PhotoDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestPhotoDAO {
    @Test
    public void testGetSelectQuery() throws Exception {
        PhotoDAO photoDAO = new PhotoDAO(DaoFactory.getConnection());
        String selectQuery = photoDAO.getSelectQuery();
        assertEquals("SELECT * FROM photos", selectQuery);
    }

    @Test
    public void testParseResultSet() throws Exception {
        PhotoDAO photoDAO = new PhotoDAO(DaoFactory.getConnection());
        List<PhotoDTO> audioDTOList = photoDAO.parseResultSet(DaoFactory.getConnection().prepareStatement(photoDAO.getSelectQuery()).executeQuery());
        Assert.assertNotNull("Список AudioDTO не ноль!", audioDTOList);
    }

}
