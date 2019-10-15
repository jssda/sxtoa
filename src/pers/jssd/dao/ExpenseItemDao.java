package pers.jssd.dao;

import pers.jssd.entity.ExpenseItem;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface ExpenseItemDao {

    /**
     * 添加一个报销明细
     * @param expenseItem 一个报销明细实体类
     * @return 返回添加的条数
     * @throws Exception 返回持久层产生的异常
     */
    int insertExpenseItem(ExpenseItem expenseItem) throws Exception;

    /**
     * 查询所有关于指定报销单的报销明细
     *
     * @param expId 指定的报销单单号
     * @return 返回包含所有查询到的所有报销单明细的list容器
     * @throws SQLException 持久层产生的sql异常
     */
    List<ExpenseItem> listExpenseItemsByExpId(int expId) throws SQLException;
}
