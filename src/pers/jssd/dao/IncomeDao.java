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


    List<Income> listIncomeBy(Date start, Date end, String icType) throws SQLException;

    /**
     * 取得收入统计数据
     *
     * @return 返回一个存储Object数组得容器
     */
    List<Object[]> getStaticData() throws SQLException;
}
