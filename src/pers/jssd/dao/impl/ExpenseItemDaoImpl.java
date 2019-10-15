package pers.jssd.dao.impl;

import pers.jssd.dao.ExpenseItemDao;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
import pers.jssd.entity.ExpenseItem;
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
public class ExpenseItemDaoImpl implements ExpenseItemDao {


    @Override
    public int insertExpenseItem(ExpenseItem expenseItem) throws Exception {

        String sql = "insert into EXPENSEITEM(ITEMID, EXPID, TYPE, AMOUNT, ITEMDESC) values (expItem_seq.nextval, ?, ? ,?, ?)";
        Object[] params = new Object[]{
                expenseItem.getExpId(),
                expenseItem.getType(),
                expenseItem.getAmount(),
                expenseItem.getItemDesc()
        };

        return DBUtil2.executeUpdate(sql, params);
    }

    @Override
    public List<ExpenseItem> listExpenseItemsByExpId(int expId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ExpenseItem> expenseItemList = new ArrayList<>();

        try {
            String sql = "select * from expenseItem where expId=?";
            connection = DBUtil2.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, expId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ExpenseItem item = new ExpenseItem();
                item.setExpId(expId);
                item.setItemId(rs.getInt("itemId"));
                item.setType(rs.getString("type"));
                item.setAmount(rs.getInt("amount"));
                item.setItemDesc(rs.getString("itemDesc"));

                expenseItemList.add(item);
            }
        } finally {
            DBUtil2.closeAll(rs, ps, null);
        }
        return expenseItemList;
    }
}
