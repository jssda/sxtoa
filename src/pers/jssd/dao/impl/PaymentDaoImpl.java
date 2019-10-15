package pers.jssd.dao.impl;

import pers.jssd.dao.PaymentDao;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
import pers.jssd.entity.ExpenseItem;
import pers.jssd.entity.Payment;
import pers.jssd.util.DBUtil2;
import pers.jssd.util.DateUtil;

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
public class PaymentDaoImpl implements PaymentDao {

    @Override
    public int insertPayment(Payment payment) throws Exception {
        String sql = "insert into payment(PID, payEmpId, AMOUNT, payTime, EXPID, EMPID)\n" +
                "values (pay_seq.nextval, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{
                payment.getPayEmpId(),
                payment.getAmount(),
                new java.sql.Timestamp(payment.getPayTime().getTime()),
                payment.getExpId(),
                payment.getEmpId()
        };

        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<Payment> listPaymentBy(Date start, Date end, String payEmpId, String paymentType) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Payment> paymentList = new ArrayList<>();

        conn = DBUtil2.getConnection();
        StringBuilder sql = new StringBuilder("select p.pid,\n" +
                "       p.payempid,\n" +
                "       e.realName as \"payEmpRealName\",\n" +
                "       p.paytime,\n" +
                "       p.expid,\n" +
                "       p.empid,\n" +
                "       e2.REALNAME   \"payedRealName\",\n" +
                "       ei.type,\n" +
                "       ei.AMOUNT,\n" +
                "       ei.itemdesc\n" +
                "from payment p\n" +
                "         join expenseitem ei\n" +
                "              on p.expid = ei.expid\n" +
                "         join employee e\n" +
                "              on e.empid = p.payempid\n" +
                "         left join employee e2\n" +
                "                   on p.empid = e2.EMPID\n" +
                "where 1 = 1");

        if (start != null) {
            sql.append(" and to_char(p.paytime, 'yyyy-MM-dd') > ?");
        }
        if (end != null) {
            sql.append(" and to_char(p.paytime, 'yyyy-MM-dd' <= ?");
        }
        if (payEmpId != null && !"".equals(payEmpId.trim())) {
            sql.append(" and e.realName = ?");
        }
        if (!"0".equals(paymentType)) {
            sql.append(" and ei.type = ?");
        }

        ps = conn.prepareStatement(sql.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int i = 1;
        if (start != null) {
            ps.setString(i++, format.format(start));
        }
        if (end != null) {
            ps.setString(i++, format.format(end));
        }
        if (payEmpId != null && !"".equals(payEmpId.trim())) {
            ps.setString(i++, payEmpId);
        }
        if (!"0".equals(paymentType)) {
            ps.setString(i, paymentType);
        }

        rs = ps.executeQuery();
        while (rs.next()) {
            /*
            类型 	金额 	备注 	    支出人 	支出时间   	 操作
            项目开发 	30000 	财务管理系统 	成林 	2013-09-08 	 查看
             */
            Payment payment = new Payment();
            payment.setpId(rs.getInt("pId"));
            payment.setPayTime(rs.getTimestamp("payTime"));
            payment.setPayEmpId(rs.getString("payEmpId"));
            payment.setEmpId(rs.getString("empId"));
            payment.setExpId(rs.getInt("expId"));

            Employee payEmp = new Employee(rs.getString("payEmpId"), rs.getString("payEmpRealName"));
            payment.setPayEmp(payEmp);

            Employee emp = new Employee(rs.getString("empId"), rs.getString("payedRealName"));
            payment.setEmp(emp);

            ExpenseItem expenseItem = new ExpenseItem();
            expenseItem.setType(rs.getString("type"));
            expenseItem.setAmount(rs.getInt("AMOUNT"));
            expenseItem.setItemDesc(rs.getString("itemdesc"));
            List<ExpenseItem> list = new ArrayList<>();
            list.add(expenseItem);

            Expense expense = new Expense();
            expense.setExpId(rs.getInt("expId"));
            expense.setExpenseItems(list);

            payment.setExpense(expense);

            paymentList.add(payment);
        }

        return paymentList;
    }

    @Override
    public List<Object[]> getStaticData(String val) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> objectList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select E.type, sum(E.AMOUNT) from PAYMENT P\n" +
                "join EXPENSEITEM E on P.EXPID = E.EXPID\n" +
                "where 1 = 1\n");

        if ("2".equals(val)) {
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') > '").append(DateUtil.getNowMonthBeginTime()).append("'");
        } else if ("3".equals(val)) {
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') > '").append(DateUtil.getNowYearBeginTime()).append("'");
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') <= '").append(DateUtil.getNowYearEndTime()).append("'");
        } else if ("4".equals(val)) {
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') > '").append(DateUtil.getNowYearEndTime()).append("'");
        } else if ("5".equals(val)) {
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') > '").append(DateUtil.getLastYearBeginTime()).append("'");
            sql.append(" and to_char(p.PAYTIME, 'yyyy-mm-dd HH24:MI:SS') <= '").append(DateUtil.getLastYearEndTime()).append("'");
        }
        sql.append("group by E.TYPE");

        conn = DBUtil2.getConnection();

        ps = conn.prepareStatement(sql.toString());
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
