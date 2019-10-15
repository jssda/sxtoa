package pers.jssd.dao.impl;

import pers.jssd.dao.IncomeDao;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Income;
import pers.jssd.util.DBUtil2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class IncomeDaoImpl implements IncomeDao {
    @Override
    public int insertIncome(Income income) throws Exception {
        String sql = "insert into income values(income_seq.nextval, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{
                income.getAmount(),
                new java.sql.Timestamp(income.getIcDate().getTime()),
                income.getIcType(),
                income.getInDesc(),
                income.getUserId()
        };

        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<Income> listIncomeBy(Date start, Date end, String icType) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Income> incomeList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select i.*, E.REALNAME\n" +
                "from income i\n" +
                "         join EMPLOYEE E on i.USERID = E.EMPID\n" +
                "where 1 = 1");
        conn = DBUtil2.getConnection();

        if (start != null) {
            sql.append(" and to_char(icDate, 'yyyy-MM-dd') > ?");
        }
        if (end != null) {
            sql.append(" and to_char(icDate, 'yyyy-MM-dd' <= ?");
        }
        if (!"0".equals(icType)) {
            sql.append(" and icType = ?");
        }

        ps = conn.prepareStatement(sql.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int i = 1;
        if (start != null) {
            ps.setString(i ++, format.format(start));
        }
        if (end != null) {
            ps.setString(i ++, format.format(end));
        }
        if (!"0".equals(icType)) {
            ps.setString(i, icType);
        }

        rs = ps.executeQuery();
        while (rs.next()) {
            Income income = new Income();
            income.setIcId(rs.getInt("icId"));
            income.setInDesc(rs.getString("inDesc"));
            income.setIcType(rs.getString("icType"));
            income.setIcDate(rs.getDate("icDate"));
            income.setAmount(rs.getInt("amount"));

            income.setUserId(rs.getString("userId"));
            Employee user = new Employee(rs.getString("userId"), rs.getString("realName"));
            income.setUser(user);

            incomeList.add(income);
        }

        return incomeList;
    }

    @Override
    public List<Object[]> getStaticData() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> objectList = new ArrayList<>();
        String sql = "select icType, sum(amount)\n" +
                "from INCOME\n" +
                "group by icType";
        conn = DBUtil2.getConnection();

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Object[] objects = new Object[2];
            objects[0] = rs.getString(1);
            objects[1] = rs.getString(2);
            objectList.add(objects);
        }
        DBUtil2.closeAll(rs, ps, conn);

        return objectList;
    }
}
