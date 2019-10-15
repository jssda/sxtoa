package pers.jssd.dao.impl;

import pers.jssd.dao.ExpenseDao;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
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
public class ExpenseDaoImpl implements ExpenseDao {

    @Override
    public int getNextVal() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int value = -1;
        try {
            String sql = "select expense_seq.nextval from dual";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return value;
    }

    @Override
    public int insertExpense(Expense expense) throws Exception {
        String sql = "insert into expense(EXPID, EMPID, TOTALAMOUNT, EXPTIME, EXPDESC, NEXTAUDITOR, STATUS) values(?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{
                expense.getExpId(),
                expense.getEmpId(),
                expense.getTotalAmount(),
                new java.sql.Timestamp(expense.getExpTime().getTime()),
                expense.getExpDesc(),
                expense.getNextAuditor(),
                expense.getStatus() == null || "".equals(expense.getStatus().trim()) ? 1 : expense.getStatus()
        };

        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<Expense> listExpensesByEmpId(String empId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> expenseList = new ArrayList<>();

        try {
            String sql = "select ex.*, e.REALNAME, e.MGRID mgr, E2.REALNAME auditRealName\n" +
                    "from expense ex\n" +
                    "         join EMPLOYEE E on ex.EMPID = E.EMPID\n" +
                    "         left join EMPLOYEE E2 on ex.NEXTAUDITOR = E2.EMPID\n" +
                    "where E.EMPID = ?";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpId(rs.getInt("expId"));
                expense.setEmpId(empId);
                expense.setTotalAmount(rs.getInt("totalAmount"));
                expense.setExpTime(rs.getTimestamp("expTime"));
                expense.setExpDesc(rs.getString("expDesc"));

                // 添加审核人信息
                expense.setNextAuditor(rs.getString("nextAuditor"));
                Employee auditor = new Employee();
                auditor.setEmpId(rs.getString("nextAuditor"));
                auditor.setRealName(rs.getString("auditRealName"));
                expense.setAuditor(auditor);

                expense.setLastResult(rs.getString("lastResult"));
                expense.setStatus(rs.getString("status"));

                // 添加报销单发起人信息
                Employee employee = new Employee(empId, rs.getString("realName"));
                employee.setMgr(new Employee(rs.getString("mgr")));
                expense.setEmployee(employee);

                expenseList.add(expense);
            }
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return expenseList;
    }

    @Override
    public List<Expense> listExpensesByEmpId(String empId, int startRow, int endRow) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> expenseList = new ArrayList<>();

        try {
            String sql = "select *\n" +
                    "from (select ROWNUM rn, temp.*\n" +
                    "      from (" +
                    "select ex.*, e.REALNAME, e.MGRID mgr, E2.REALNAME auditRealName\n" +
                    "from expense ex\n" +
                    "         join EMPLOYEE E on ex.EMPID = E.EMPID\n" +
                    "         left join EMPLOYEE E2 on ex.NEXTAUDITOR = E2.EMPID\n" +
                    "where E.EMPID = ?"
                    + ") temp\n" +
                    "      where ROWNUM <= ?)\n" +
                    "where rn > ?";

            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, empId);
            ps.setInt(2, endRow);
            ps.setInt(3, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpId(rs.getInt("expId"));
                expense.setEmpId(empId);
                expense.setTotalAmount(rs.getInt("totalAmount"));
                expense.setExpTime(rs.getTimestamp("expTime"));
                expense.setExpDesc(rs.getString("expDesc"));

                // 添加审核人信息
                expense.setNextAuditor(rs.getString("nextAuditor"));
                Employee auditor = new Employee();
                auditor.setEmpId(rs.getString("nextAuditor"));
                auditor.setRealName(rs.getString("auditRealName"));
                expense.setAuditor(auditor);

                expense.setLastResult(rs.getString("lastResult"));
                expense.setStatus(rs.getString("status"));

                // 添加报销单发起人信息
                Employee employee = new Employee(empId, rs.getString("realName"));
                employee.setMgr(new Employee(rs.getString("mgr")));
                expense.setEmployee(employee);

                expenseList.add(expense);
            }
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return expenseList;
    }

    @Override
    public List<Expense> listExpensesByNextAudit(String nextAuditor) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> expenseList = new ArrayList<>();

        try {
            String sql = "select ex.*, e.REALNAME, e.MGRID mgr from expense ex\n" +
                    "join EMPLOYEE E on ex.EMPID = E.EMPID\n" +
                    "where ex.NEXTAUDITOR = ? and ex.STATUS = '1'";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, nextAuditor);
            rs = ps.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpId(rs.getInt("expId"));
                expense.setEmpId(rs.getString("empId"));
                expense.setTotalAmount(rs.getInt("totalAmount"));
                expense.setExpTime(rs.getTimestamp("expTime"));
                expense.setExpDesc(rs.getString("expDesc"));
                expense.setNextAuditor(rs.getString("nextAuditor"));
                expense.setLastResult(rs.getString("lastResult"));
                expense.setStatus(rs.getString("status"));

                Employee employee = new Employee(rs.getString("empId"), rs.getString("realName"));
                employee.setMgr(new Employee(rs.getString("mgr")));
                expense.setEmployee(employee);

                expenseList.add(expense);
            }
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return expenseList;
    }

    @Override
    public int updateExpense(Expense expense) throws Exception {
        String sql = "update EXPENSE set NEXTAUDITOR = ?, LASTRESULT=?, STATUS=? where EXPID = ?";
        Object[] params = new Object[]{
                expense.getNextAuditor(),
                expense.getLastResult(),
                expense.getStatus(),
                expense.getExpId()
        };
        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public Expense getExpenseByExpId(int expId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Expense expense = null;

        try {
            String sql = "select ex.*, e.REALNAME, e.MGRID mgr from expense ex\n" +
                    "join EMPLOYEE E on ex.EMPID = E.EMPID\n" +
                    "where ex.EXPID = ?";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, expId);
            rs = ps.executeQuery();

            while (rs.next()) {
                expense = new Expense();
                expense.setExpId(expId);
                expense.setEmpId(rs.getString("empId"));
                expense.setTotalAmount(rs.getInt("totalAmount"));
                expense.setExpTime(rs.getTimestamp("expTime"));
                expense.setExpDesc(rs.getString("expDesc"));
                expense.setNextAuditor(rs.getString("nextAuditor"));
                expense.setLastResult(rs.getString("lastResult"));
                expense.setStatus(rs.getString("status"));

                Employee employee = new Employee(rs.getString("empId"), rs.getString("realName"));
                employee.setMgr(new Employee(rs.getString("mgr")));
                expense.setEmployee(employee);
            }
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return expense;
    }

    @Override
    public int getExpenseSumBy(String empId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            String sql = "select count(*)" +
                    "from expense ex\n" +
                    "         join EMPLOYEE E on ex.EMPID = E.EMPID\n" +
                    "         left join EMPLOYEE E2 on ex.NEXTAUDITOR = E2.EMPID\n" +
                    "where E.EMPID = ?";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, empId);
            rs = ps.executeQuery();

            rs.next();
            count = rs.getInt(1);
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }

        return count;
    }
}
