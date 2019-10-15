package pers.jssd.dao;

import pers.jssd.entity.Payment;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface PaymentDao {

    /**
     * 插入支出数据
     *
     * @param payment 需要插入的支出
     * @return 返回一共插入了多少条数据
     * @throws Exception sql执行过程中产生的异常
     */
    int insertPayment(Payment payment) throws Exception;

    /**
     * 通过条件查询所有的支出信息
     *
     * @param start       查询的时间范围开始
     * @param end         查询的时间范围结束
     * @param payEmpId    支出人员id
     * @param paymentType 支出类型
     * @return 返回封装所有查询到的数据的list
     */
    List<Payment> listPaymentBy(Date start, Date end, String payEmpId, String paymentType) throws SQLException;

    /**
     * 通过条件分页查询所有的支出信息
     *
     * @param start       查询的时间范围开始
     * @param end         查询的时间范围结束
     * @param payEmpId    支出人员id
     * @param paymentType 支出类型
     * @param startRow    分页开始的记录位置
     * @param endRow      分页结束的日志位置
     * @return 返回封装所有查询到的数据的list
     */
    List<Payment> listPaymentBy(Date start, Date end, String payEmpId, String paymentType, int startRow, int endRow) throws SQLException;

    /**
     * 取得统计数据
     *
     * @param val 类型
     * @return 返回统计的数据
     * @throws SQLException SQL异常
     */
    List<Object[]> getStaticData(String val) throws SQLException;

    /**
     * 查询在指定条件下一共有多少条数据
     *
     * @param start       开始时间
     * @param end         结束时间
     * @param payEmpId    支付人的id
     * @param paymentType 支付人类型
     * @return 返回一共多少条数据
     * @throws SQLException 产生的SQL异常
     */
    int getPaymentSumBy(Date start, Date end, String payEmpId, String paymentType) throws SQLException;


}
