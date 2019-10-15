package pers.jssd.dao.impl;

import pers.jssd.dao.ExpImageDao;
import pers.jssd.entity.Employee;
import pers.jssd.entity.ExpImage;
import pers.jssd.util.DBUtil2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class ExpImageDaoImpl implements ExpImageDao {

    @Override
    public int insertExpImage(ExpImage image) throws Exception {
        String sql = "insert into expImage(IMGID, EXPID, REALNAME, FILENAME, FILETYPE)\n" +
                "values (expImage_seq.nextval, ?, ?, ?, ?)";
        Object[] params = new Object[]{
                image.getExpId(),
                image.getRealName(),
                image.getFileName(),
                image.getFileType()
        };

        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<ExpImage> listExpImages(int expId) {
        List<ExpImage> expImages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select * from expImage where expId = ?";
            conn = DBUtil2.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, expId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ExpImage expImage = new ExpImage();
                expImage.setImgId(rs.getInt("imgId"));
                expImage.setExpId(rs.getInt("expId"));
                expImage.setRealName(rs.getString("realName"));
                expImage.setFileName(rs.getString("fileName"));
                expImage.setFileType(rs.getString("fileType"));
                expImages.add(expImage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expImages;
    }
}
