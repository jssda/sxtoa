package pers.jssd.dao;

import pers.jssd.entity.Expense;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface ExpenseDao {

    /**
     * 取得序列中的下一个值
     *
     * @return 返回序列中的下一个值
     */
    int getNextVal();

    /**
     * 添加一个expense考勤信息
     * @param expense 需要添加的考勤实体类
     * @return 返回添加的条数
     * @throws Exception 抛出所有产生的异常到业务层处理
     */
    int insertExpense(Expense expense) throws Exception;


    /**
     * 查询所有的指定empId的expense信息
     *
     * @param empId 需要查询的empId
     * @return 返回存储所有指定查询的List容器
     * @throws SQLException 持久层产生的sql异常
     */
    List<Expense> listExpensesByEmpId(String empId) throws SQLException;

    /**
     * 查询所有需要当前用户审核的报销单
     *
     * @param nextAuditor 当前用户的Id
     * @return 返回存储所有指定查询的List容器
     * @throws SQLException 持久层产生的sql异常
     */
    List<Expense> listExpensesByNextAudit(String nextAuditor) throws SQLException;

    /**
     * 更新报销单
     *
     * @param expense 需要更新的报销单
     * @return 返回更新的数据条数
     * @throws Exception 更新过程中产生的异常
     */
    int updateExpense(Expense expense) throws Exception;

    /**
     * 通过ExpenseId查找到Expense信息
     * @param expId 需要查找到的expense的id
     * @return 返回一个Expense对象
     * @throws SQLException 查找过程中产生的SQL异常
     */
    Expense getExpenseByExpId(int expId) throws SQLException;
}
