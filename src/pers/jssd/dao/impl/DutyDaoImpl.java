package pers.jssd.dao.impl;

import pers.jssd.dao.DutyDao;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Duty;
import pers.jssd.entity.Employee;
import pers.jssd.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DutyDaoImpl implements DutyDao {
    @Override
    public boolean getDuty(String empId, Date dtDate) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Duty> duties = new ArrayList<>();
        boolean flag = false;

        try {
            String sql = "select * from duty where emprId = ? and dtDate=?";

            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, empId);
            statement.setDate(2, new java.sql.Date(dtDate.getTime()));
            rs = statement.executeQuery();
            if (rs.next())
                flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return flag;
    }

    @Override
    public int insertDuty(Duty duty) {
        String sql = "insert into duty values(duty_seq.nextval, ?, ?, ?, ?)";
        Object[] params = new Object[]{duty.getEmpId(), new java.sql.Date(duty.getDtDate().getTime()), duty.getSignInTime(), null};

        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int updateDuty(Duty duty) {
        String sql = "update duty set signOutTime=? where emprId=? and dtDate=?";
        Object[] params = new Object[]{duty.getSignOutTime(), duty.getEmpId(), new java.sql.Date(duty.getDtDate().getTime())};

        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Duty> listDutiesBy(String empId, int deptNo, String sDtDate) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> duties = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("select d.dtId, emprId, dtDate, signInTime, signOutTime, e.realName, dp.deptName, dp.deptNo\n" +
                    "from duty d\n" +
                    "         join employee e\n" +
                    "              on d.emprId = e.empId\n" +
                    "         join dept dp\n" +
                    "              on e.deptNo = dp.deptNo\n" +
                    "where 1 = 1\n");

            connection = DBUtil.getConnection();

            if (empId != null && !"".equals(empId.trim())) {
                sql.append("  and d.emprId = '").append(empId).append("'");
            }
            if (sDtDate != null && !"".equals(sDtDate.trim())) {
                sql.append(" and to_char(d.dtDate, 'yyyy-MM-dd') >='").append(sDtDate).append("'");
            }
            if (deptNo != 0) {
                sql.append(" and dp.DEPTNO = ").append(deptNo);
            }

            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                Duty duty = new Duty();

                duty.setDtId(rs.getInt("dtId"));
                duty.setDtDate(rs.getDate("dtDate"));
                duty.setEmpId(rs.getString("emprId"));
                duty.setSignInTime(rs.getString("signInTime"));
                duty.setSignOutTime(rs.getString("signOutTime"));

                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emprId"));
                employee.setRealName(rs.getString("realName"));

                Dept dept = new Dept();
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));

                employee.setDept(dept);
                duty.setEmployee(employee);

                duties.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, ps, connection);
        }

        return duties;
    }

    @Override
    public List<Duty> listDuties(String empId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> duties = new ArrayList<>();

        try {
            String sql = "select * from duty where emprId = ?";

            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, empId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Duty duty = new Duty();

                duty.setDtId(rs.getInt("dtId"));
                duty.setDtDate(rs.getDate("dtDate"));
                duty.setEmpId(rs.getString("emprId"));
                duty.setSignInTime(rs.getString("signInTime"));
                duty.setSignOutTime(rs.getString("signOutTime"));

                duties.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, ps, connection);
        }

        return duties;
    }

    @Override
    public List<Duty> listDuties(String empId, int startRow, int endRow) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> duties = new ArrayList<>();

        try {
            String sql = "select *\n" +
                    "from (select ROWNUM rn, temp.*\n" +
                    "      from (select * from duty where emprId = ?) temp\n" +
                    "      where ROWNUM <= ?)\n" +
                    "where rn > ?";

            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, empId);
            ps.setInt(2, endRow);
            ps.setInt(3, startRow);

            rs = ps.executeQuery();
            while (rs.next()) {
                Duty duty = new Duty();

                duty.setDtId(rs.getInt("dtId"));
                duty.setDtDate(rs.getDate("dtDate"));
                duty.setEmpId(rs.getString("emprId"));
                duty.setSignInTime(rs.getString("signInTime"));
                duty.setSignOutTime(rs.getString("signOutTime"));

                duties.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, ps, connection);
        }

        return duties;
    }

    @Override
    public int getDutySum(String empId, int deptNo, String sDtDate) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            StringBuilder sql = new StringBuilder("select count(*)\n" +
                    "from duty d\n" +
                    "         join employee e\n" +
                    "              on d.emprId = e.empId\n" +
                    "         join dept dp\n" +
                    "              on e.deptNo = dp.deptNo\n" +
                    "where 1 = 1\n");

            connection = DBUtil.getConnection();

            if (empId != null && !"".equals(empId.trim())) {
                sql.append("  and d.emprId = '").append(empId).append("'");
            }
            if (sDtDate != null && !"".equals(sDtDate.trim())) {
                sql.append(" and to_char(d.dtDate, 'yyyy-MM-dd') >='").append(sDtDate).append("'");
            }
            if (deptNo != 0) {
                sql.append(" and dp.DEPTNO = ").append(deptNo);
            }

            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, ps, connection);
        }

        return count;
    }

    @Override
    public List<Duty> listDutiesBy(String empId, int deptNo, String sDtDate, int startRow, int endRow) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> duties = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("select *\n" +
                    "from (select ROWNUM rn, temp.*\n" +
                    "      from (select d.dtId,\n" +
                    "                   emprId,\n" +
                    "                   dtDate,\n" +
                    "                   signInTime,\n" +
                    "                   signOutTime,\n" +
                    "                   e.realName,\n" +
                    "                   dp.deptName,\n" +
                    "                   dp.deptNo\n" +
                    "            from duty d\n" +
                    "                     join employee e\n" +
                    "                          on d.emprId = e.empId\n" +
                    "                     join dept dp\n" +
                    "                          on e.deptNo = dp.deptNo\n" +
                    "            where 1 = 1\n");

            connection = DBUtil.getConnection();

            if (empId != null && !"".equals(empId.trim())) {
                sql.append("  and d.emprId = '").append(empId).append("'");
            }
            if (sDtDate != null && !"".equals(sDtDate.trim())) {
                sql.append(" and to_char(d.dtDate, 'yyyy-MM-dd') >='").append(sDtDate).append("'");
            }
            if (deptNo != 0) {
                sql.append(" and dp.DEPTNO = ").append(deptNo);
            }

            sql.append(") temp\n" +
                    "      where ROWNUM <= ?)\n" +
                    "where rn > ?");

            ps = connection.prepareStatement(sql.toString());

            ps.setInt(1, endRow);
            ps.setInt(2, startRow);

            rs = ps.executeQuery();
            while (rs.next()) {
                Duty duty = new Duty();

                duty.setDtId(rs.getInt("dtId"));
                duty.setDtDate(rs.getDate("dtDate"));
                duty.setEmpId(rs.getString("emprId"));
                duty.setSignInTime(rs.getString("signInTime"));
                duty.setSignOutTime(rs.getString("signOutTime"));

                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emprId"));
                employee.setRealName(rs.getString("realName"));

                Dept dept = new Dept();
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));

                employee.setDept(dept);
                duty.setEmployee(employee);

                duties.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, ps, connection);
        }

        return duties;
    }

}
