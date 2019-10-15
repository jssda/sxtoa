package pers.jssd.dao.impl;

import pers.jssd.dao.DeptDao;
import pers.jssd.entity.Dept;
import pers.jssd.util.DBUtil;
import pers.jssd.util.DBUtil2;
import pers.jssd.util.PageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DeptDaoImpl implements DeptDao {

    @Override
    public int insertDept(Dept dept) {

        Object[] objs = new Object[]{dept.getDeptNo(), dept.getDeptName(), dept.getLocation()};
        String sql = "insert into DEPT(DEPTNO, DEPTNAME, LOCATION) values (?, ?, ?)";

        return DBUtil.executeUpdate(sql, objs);
    }

    @Override
    public List<Dept> listDepts() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Dept> list = new ArrayList<>();

        try {
            String sql = "select * from DEPT";
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                Dept dept = new Dept();
                dept.setDeptNo(rs.getInt(1));
                dept.setDeptName(rs.getString(2));
                dept.setLocation(rs.getString(3));

                list.add(dept);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return list;
    }

    @Override
    public int delDept(int deptNo) {
        Object[] objs = new Object[]{deptNo};
        String sql = "delete DEPT where deptno=?";

        return DBUtil.executeUpdate(sql, objs);
    }

    @Override
    public Dept getDeptById(int deptNo) {
        Object[] objs = new Object[]{deptNo};

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Dept dept = null;

        try {
            String sql = "select deptname, location from DEPT where deptno = ?";
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, deptNo);
            rs = statement.executeQuery();

            while (rs.next()) {
                dept = new Dept();
                dept.setDeptName(rs.getString("deptname"));
                dept.setLocation(rs.getString("location"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return dept;
    }

    @Override
    public int updateDept(Dept dept) {
        Object[] objs = new Object[]{dept.getDeptName(), dept.getLocation(), dept.getDeptNo()};
        String sql = "update DEPT set deptname=?, location=? where DEPTNO = ?";

        return DBUtil.executeUpdate(sql, objs);
    }

    @Override
    public int getDeptSum() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int sum = 0;

        try {
            conn = DBUtil2.getConnection();
            String sql = "select count(*) from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            sum = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(rs, ps, conn);
        }
        return sum;
    }

    @Override
    public List<Dept> listDepts(int startRow, int endRow) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Dept> list = new ArrayList<>();

        try {
            String sql = "select *\n" +
                    "from (select d.*, ROWNUM rn from (select * from dept order by DEPTNO) d where ROWNUM <= ?)\n" +
                    "where rn > ?";
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, endRow);
            statement.setInt(2, startRow);

            rs = statement.executeQuery();

            while (rs.next()) {
                Dept dept = new Dept();
                dept.setDeptNo(rs.getInt(1));
                dept.setDeptName(rs.getString(2));
                dept.setLocation(rs.getString(3));

                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }
        return list;
    }
}
