package pers.jssd.dao;

import pers.jssd.entity.Income;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface IncomeDao {


    /**
     * 插入一条收入数据
     *
     * @param income 收入实体类
     * @return 返回插入几条数据
     * @throws Exception 数据库操作过程中产生得异常
     */
    int insertIncome(Income income) throws Exception;

    /**
     * 查询所有指定条件的收入
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param icType 类型
     * @return 返回所有查询到的数据集合
     * @throws SQLException sql异常
     */
    List<Income> listIncomeBy(Date start, Date end, String icType) throws SQLException;

    /**
     * 分页查询所有指定条件的收入
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param icType 类型
     * @param startRow 分页记录开始位置
     * @param endRow 分页记录结束位置
     * @return 返回所有查询到的数据集合
     * @throws SQLException sql异常
     */
    List<Income> listIncomeBy(Date start, Date end, String icType, int startRow, int endRow) throws SQLException;

    /**
     * 取得收入统计数据
     *
     * @return 返回一个存储Object数组得容器
     */
    List<Object[]> getStaticData() throws SQLException;

    /**
     * 查询在指定条件下所有的收入记录数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param icType 类型
     * @return 返回一共有多少条数据
     */
    int getIncomeSumBy(Date start, Date end, String icType) throws SQLException;
}
