package pers.jssd.service.impl;

import pers.jssd.dao.IncomeDao;
import pers.jssd.dao.PaymentDao;
import pers.jssd.dao.impl.IncomeDaoImpl;
import pers.jssd.dao.impl.PaymentDaoImpl;
import pers.jssd.entity.Income;
import pers.jssd.entity.Payment;
import pers.jssd.service.InPayService;
import pers.jssd.util.DBUtil2;
import pers.jssd.util.PageBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class InPayServiceImpl implements InPayService {

    private IncomeDao incomeDao = new IncomeDaoImpl();
    private PaymentDao paymentDao = new PaymentDaoImpl();

    @Override
    public int addIncome(Income income) {
        Connection conn = null;
        int count = 0;
        try {
            conn = DBUtil2.getConnection();
            conn.setAutoCommit(false);

            // 添加收入信息
            count = incomeDao.insertIncome(income);


            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }


        return count;
    }

    @Override
    public List<Income> findIncomeBy(Date start, Date end, String icType) {
        Connection conn = null;
        List<Income> incomeList = null;
        try {
            conn = DBUtil2.getConnection();
            conn.setAutoCommit(false);

            // 添加收入信息
            incomeList = incomeDao.listIncomeBy(start, end, icType);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }

        return incomeList;
    }

    @Override
    public void findIncomeBy(Date start, Date end, String icType, PageBean<Income> pageBean) {
        Connection conn = null;
        List<Income> incomeList = null;
        try {
            conn = DBUtil2.getConnection();
            conn.setAutoCommit(false);

            // 设置每页显示的数据量
            pageBean.setSize(13);
            // 设置默认的显示页码数组长度
            pageBean.setDefaultNumberLength(5);

            int sum = incomeDao.getIncomeSumBy(start, end, icType);
            // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
            pageBean.setTotalCount(sum);

            int startRow = pageBean.getStartRow();
            int endRow = pageBean.getEndRow();

            // 添加收入信息
            incomeList = incomeDao.listIncomeBy(start, end, icType,startRow, endRow);
            pageBean.setList(incomeList);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }
    }

    @Override
    public String getIncomeStaticStr() {
        StringBuilder sb = new StringBuilder();
        try {
            List<Object[]> list = incomeDao.getStaticData();

            //  var jsonStr = {icType: ["人员外包", "项目开发", "报名费", "学费"], amount: ["100", "200", "300", "400"]};
            sb.append("{");
            sb.append("icType:[");
            for (int i = 0; i < list.size(); i++) {
                if ("1".equals(list.get(i)[0])) {
                    sb.append("\"人员外包\"");
                } else if ("2".equals(list.get(i)[0])) {
                    sb.append("\"项目开发\"");
                } else if ("3".equals(list.get(i)[0])) {
                    sb.append("\"报名费\"");
                } else if ("4".equals(list.get(i)[0])) {
                    sb.append("\"学费\"");
                }
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("], ");

            sb.append("amount:[");
            for (int i = 0; i < list.size(); i++) {
                sb.append("\"").append(list.get(i)[1]).append("\"");
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]}");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public List<Payment> findPaymentBy(Date start, Date end, String payEmpId, String paymentType) {
        Connection conn = null;
        List<Payment> paymentList = null;
        try {
            conn = DBUtil2.getConnection();
            conn.setAutoCommit(false);

            // 添加支出信息
            paymentList = paymentDao.listPaymentBy(start, end, payEmpId, paymentType);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }

        return paymentList;
    }

    @Override
    public void findPaymentBy(Date start, Date end, String payEmpId, String paymentType, PageBean<Payment> pageBean) {
        Connection conn = null;
        List<Payment> paymentList = null;
        try {
            conn = DBUtil2.getConnection();
            conn.setAutoCommit(false);

            // 设置每页显示的数据量
            pageBean.setSize(13);
            // 设置默认的显示页码数组长度
            pageBean.setDefaultNumberLength(5);

            int sum = paymentDao.getPaymentSumBy(start, end, payEmpId, paymentType);

            // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
            pageBean.setTotalCount(sum);

            int startRow = pageBean.getStartRow();
            int endRow = pageBean.getEndRow();

            // 添加支出信息
            paymentList = paymentDao.listPaymentBy(start, end, payEmpId, paymentType, startRow, endRow);
            pageBean.setList(paymentList);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }
    }

    @Override
    public String getPaymentStaticStr(String val) {
        StringBuilder sb = new StringBuilder();
        try {
            List<Object[]> list = paymentDao.getStaticData(val);

            //  "[{value: 335, name: '通信费用'},{value: 310, name: '办公室耗材'},{value: 234, name: '住宿费用'},
            //    {value: 135, name: '房租水电'},{value: 1548, name: '其他'}]";
            sb.append("[");
            for (int i = 0; i < list.size(); i++) {
                sb.append("{value:");
                sb.append(list.get(i)[1]);
                sb.append(", name: \"");
                if ("1".equals(list.get(i)[0])) {
                    sb.append("通信费用");
                } else if ("2".equals(list.get(i)[0])){
                    sb.append("办公室耗材");
                } else if ("3".equals(list.get(i)[0])){
                    sb.append("住宿费用");
                }else if ("4".equals(list.get(i)[0])){
                    sb.append("房租水电");
                }else if ("5".equals(list.get(i)[0])){
                    sb.append("其他");
                }
                sb.append("\"}");
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
