package pers.jssd.dao.impl;

import pers.jssd.dao.AuditingDao;
import pers.jssd.entity.Auditing;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
import pers.jssd.util.DBUtil2;
import pers.jssd.util.PageBean;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class AuditingDaoImpl implements AuditingDao {

    @Override
    public int insertAuditing(Auditing auditing) throws Exception {
        String sql = "insert into auditing(auditId, expId, empId, result, auditDesc, time)\n" +
                "VALUES (audit_seq.nextval, ?, ?, ?, ?, ?)";

        Object[] params = new Object[]{
                auditing.getExpId(),
                auditing.getEmpId(),
                auditing.getResult(),
                auditing.getAuditDesc(),
                new java.sql.Timestamp(auditing.getTime().getTime())
        };
        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<Auditing> listAuditingByExpId(int expId) throws SQLException {
        List<Auditing> auditingList = new ArrayList<>();
        String sql = "select a.AUDITID,\n" +
                "       a.EXPID,\n" +
                "       a.EMPID,\n" +
                "       a.RESULT,\n" +
                "       a.AUDITDESC,\n" +
                "       a.TIME,\n" +
                "       e.REALNAME,\n" +
                "       E2.TOTALAMOUNT,\n" +
                "       E2.EXPTIME,\n" +
                "       E2.EXPDESC,\n" +
                "       E2.STATUS,\n" +
                "       E2.EMPID expEmpId,\n" +
                "       E3.REALNAME expRealName\n" +
                "from AUDITING a\n" +
                "         join EMPLOYEE E on a.EMPID = E.EMPID\n" +
                "         join EXPENSE E2 on a.EXPID = E2.EXPID\n" +
                "         join EMPLOYEE E3 on E3.EMPID = E2.EMPID\n" +
                "     where a.EXPID = ?";
        Connection connection = DBUtil2.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, expId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Auditing auditing = new Auditing();
            auditing.setAuditId(rs.getInt("auditId"));
            auditing.setExpId(rs.getInt("expId"));

            // 添加审核人信息
            auditing.setEmpId(rs.getString("empId"));
            Employee employee = new Employee();
            employee.setEmpId(rs.getString("empId"));
            employee.setRealName(rs.getString("realName"));
            auditing.setEmployee(employee);

            // 添加审核的报销单信息
            Expense expense = new Expense();
            // 添加审核人信息
            expense.setAuditor(employee);
            expense.setStatus(rs.getString("STATUS"));
            expense.setTotalAmount(rs.getInt("TOTALAMOUNT"));
            // 添加报销人信息
            Employee expenseEmp = new Employee();
            expenseEmp.setEmpId(rs.getString("expEmpId"));
            expenseEmp.setRealName(rs.getString("expRealName"));
            expense.setEmployee(expenseEmp);
            auditing.setExpense(expense);

            auditing.setResult(rs.getString("result"));
            auditing.setAuditDesc(rs.getString("auditDesc"));
            auditing.setTime(rs.getTimestamp("time"));

            auditingList.add(auditing);
        }


        return auditingList;
    }

    @Override
    public List<Auditing> listAuditingByEmpId(String empId) throws SQLException {
        List<Auditing> auditingList = new ArrayList<>();
        String sql = "select a.AUDITID,\n" +
                "       a.EXPID,\n" +
                "       a.EMPID,\n" +
                "       a.RESULT,\n" +
                "       a.AUDITDESC,\n" +
                "       a.TIME,\n" +
                "       e.REALNAME,\n" +
                "       E2.TOTALAMOUNT,\n" +
                "       E2.EXPTIME,\n" +
                "       E2.EXPDESC,\n" +
                "       E2.STATUS,\n" +
                "       E2.EMPID expEmpId,\n" +
                "       E3.REALNAME expRealName\n" +
                "from AUDITING a\n" +
                "         join EMPLOYEE E on a.EMPID = E.EMPID\n" +
                "         join EXPENSE E2 on a.EXPID = E2.EXPID\n" +
                "         join EMPLOYEE E3 on E3.EMPID = E2.EMPID\n" +
                "     where a.EMPID = ?";
        Connection connection = DBUtil2.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, empId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Auditing auditing = new Auditing();
            auditing.setAuditId(rs.getInt("auditId"));
            auditing.setExpId(rs.getInt("expId"));

            // 添加审核人信息
            auditing.setEmpId(empId);
            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setRealName(rs.getString("realName"));
            auditing.setEmployee(employee);

            // 添加审核的报销单信息
            Expense expense = new Expense();
            // 添加审核人信息
            expense.setAuditor(employee);
            expense.setStatus(rs.getString("STATUS"));
            expense.setTotalAmount(rs.getInt("TOTALAMOUNT"));
            // 添加报销人信息
            Employee expenseEmp = new Employee();
            expenseEmp.setEmpId(rs.getString("expEmpId"));
            expenseEmp.setRealName(rs.getString("expRealName"));
            expense.setExpTime(rs.getTimestamp("expTime"));
            expense.setExpDesc(rs.getString("expDesc"));
            expense.setEmployee(expenseEmp);
            auditing.setExpense(expense);

            auditing.setResult(rs.getString("result"));
            auditing.setAuditDesc(rs.getString("auditDesc"));
            auditing.setTime(rs.getTimestamp("time"));

            auditingList.add(auditing);
        }

        return auditingList;
    }

    @Override
    public List<Auditing> listAuditingByEmpId(String empId, int startRow, int endRow) throws SQLException {
        List<Auditing> auditingList = new ArrayList<>();
        String sql = "select *\n" +
                "from (select ROWNUM rn, temp.*\n" +
                "      from (" +
                "select a.AUDITID,\n" +
                "       a.EXPID,\n" +
                "       a.EMPID,\n" +
                "       a.RESULT,\n" +
                "       a.AUDITDESC,\n" +
                "       a.TIME,\n" +
                "       e.REALNAME,\n" +
                "       E2.TOTALAMOUNT,\n" +
                "       E2.EXPTIME,\n" +
                "       E2.EXPDESC,\n" +
                "       E2.STATUS,\n" +
                "       E2.EMPID expEmpId,\n" +
                "       E3.REALNAME expRealName\n" +
                "from AUDITING a\n" +
                "         join EMPLOYEE E on a.EMPID = E.EMPID\n" +
                "         join EXPENSE E2 on a.EXPID = E2.EXPID\n" +
                "         join EMPLOYEE E3 on E3.EMPID = E2.EMPID\n" +
                "     where a.EMPID = ?"
                + ") temp\n" +
                "      where ROWNUM <= ?)\n" +
                "where rn > ?";

        Connection connection = DBUtil2.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, empId);
        ps.setInt(2, endRow);
        ps.setInt(3, startRow);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Auditing auditing = new Auditing();
            auditing.setAuditId(rs.getInt("auditId"));
            auditing.setExpId(rs.getInt("expId"));

            // 添加审核人信息
            auditing.setEmpId(empId);
            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setRealName(rs.getString("realName"));
            auditing.setEmployee(employee);

            // 添加审核的报销单信息
            Expense expense = new Expense();
            // 添加审核人信息
            expense.setAuditor(employee);
            expense.setStatus(rs.getString("STATUS"));
            expense.setTotalAmount(rs.getInt("TOTALAMOUNT"));
            // 添加报销人信息
            Employee expenseEmp = new Employee();
            expenseEmp.setEmpId(rs.getString("expEmpId"));
            expenseEmp.setRealName(rs.getString("expRealName"));
            expense.setExpTime(rs.getTimestamp("expTime"));
            expense.setExpDesc(rs.getString("expDesc"));
            expense.setEmployee(expenseEmp);
            auditing.setExpense(expense);

            auditing.setResult(rs.getString("result"));
            auditing.setAuditDesc(rs.getString("auditDesc"));
            auditing.setTime(rs.getTimestamp("time"));

            auditingList.add(auditing);
        }

        return auditingList;
    }

    @Override
    public int getAuditingSumBy(String empId) throws SQLException {
        int count = 0;
        String sql = "select count(*) " +
                "from AUDITING a\n" +
                "         join EMPLOYEE E on a.EMPID = E.EMPID\n" +
                "         join EXPENSE E2 on a.EXPID = E2.EXPID\n" +
                "         join EMPLOYEE E3 on E3.EMPID = E2.EMPID\n" +
                "     where a.EMPID = ?";
        Connection connection = DBUtil2.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, empId);

        ResultSet rs = ps.executeQuery();
        rs.next();
        count = rs.getInt(1);

        return count;
    }
}
