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
     * @param start 查询的时间范围开始
     * @param end 查询的时间范围结束
     * @param payEmpId 支出人员id
     * @param paymentType 支出类型
     * @return 返回封装所有查询到的数据的list
     */
    List<Payment> listPaymentBy(Date start, Date end, String payEmpId, String paymentType) throws SQLException;

    List<Object[]> getStaticData(String val) throws SQLException;
}
