package pers.jssd.service;

import pers.jssd.entity.Income;
import pers.jssd.entity.Payment;

import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface InPayService {


    /**
     * 添加一条收入数据
     *
     * @param income 收入实体类
     * @return 返回添加了几条数据
     */
    int addIncome(Income income);

    /**
     * 通过条件查询收入
     *
     * @param start  开始时间范围
     * @param end    结束时间范围
     * @param icType 收入类型
     * @return 返回存储收入得容器
     */
    List<Income> findIncomeBy(Date start, Date end, String icType);

    /**
     * 取得统计数据拼接成得字符串
     *
     * @return 返回统计数据拼接成得字符串
     */
    String getIncomeStaticStr();

    /**
     * 通过条件查询支出
     *
     * @param start 查询范围开始时间
     * @param end 查询范围结束时间
     * @param payEmpId 支出人id
     * @param  paymentType 支出类型
     * @return 返回查询到得所有支出构成的容器
     */
    List<Payment> findPaymentBy(Date start, Date end, String payEmpId, String paymentType);

    /**
     * 取得支出统计数据拼接成得字符串
     * @param val 支出时间范围类型
     * @return 返回统计数据拼接成的json字符串
     */
    String getPaymentStaticStr(String val);
}
