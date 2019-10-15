package pers.jssd.dao.impl;

import pers.jssd.dao.PositionDao;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Position;
import pers.jssd.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class PositionDaoImpl implements PositionDao {
    @Override
    public List<Position> getPositions() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Position> positions = new ArrayList<>();


        try {
            String sql = "select * from POSITION";
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Position position = new Position(rs.getInt(1), rs.getString(2), rs.getString(3));
                positions.add(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return positions;
    }

    @Override
    public int insertPosition(Position position) {
        String sql = "insert into POSITION(posId, pName, pDesc) values (?, ?, ?)";
        Object[] params = new Object[]{position.getPosId(), position.getpName(), position.getpDesc()};

        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int delPosition(int posId) {
        Object[] objs = new Object[]{posId};
        String sql = "delete POSITION where posId=?";

        return DBUtil.executeUpdate(sql, objs);
    }

    @Override
    public Position getPositionById(int posId) {
        Object[] objs = new Object[]{posId};

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Position position = null;

        try {
            String sql = "select PNAME, PDESC from POSITION where POSID = ?";
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, posId);
            rs = statement.executeQuery();

            while (rs.next()) {
                position = new Position();
                position.setpName(rs.getString("pName"));
                position.setpDesc(rs.getString("pDesc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return position;
    }

    @Override
    public int updatePosition(Position position) {
        Object[] objs = new Object[]{position.getpName(), position.getpDesc(), position.getPosId()};
        String sql = "update POSITION set pName=?, pDesc=? where posId = ?";

        return DBUtil.executeUpdate(sql, objs);
    }
}
