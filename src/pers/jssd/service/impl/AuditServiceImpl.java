package pers.jssd.service.impl;

import pers.jssd.dao.AuditingDao;
import pers.jssd.dao.ExpenseDao;
import pers.jssd.dao.PaymentDao;
import pers.jssd.dao.impl.AuditingDaoImpl;
import pers.jssd.dao.impl.ExpenseDaoImpl;
import pers.jssd.dao.impl.PaymentDaoImpl;
import pers.jssd.entity.Auditing;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
import pers.jssd.entity.Payment;
import pers.jssd.service.AuditService;
import pers.jssd.util.Constant;
import pers.jssd.util.DBUtil2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class AuditServiceImpl implements AuditService {

    private AuditingDao auditingDao = new AuditingDaoImpl();
    private ExpenseDao expenseDao = new ExpenseDaoImpl();
    private PaymentDao paymentDao = new PaymentDaoImpl();

    /**
     * 添加审核记录并且修改报销单
     *
     * @param auditing      需要添加的审核记录
     * @param nextAuditor   下一个审核人
     * @param expenseStatus 报销单目前状态
     */
    private void addAuditing(Auditing auditing, String nextAuditor, String expenseStatus) {
        Connection connection = null;

        try {
            connection = DBUtil2.getConnection();
            connection.setAutoCommit(false);

            // 添加审核记录
            auditingDao.insertAuditing(auditing);

            // 更改报销单
            Expense expense = new Expense();
            expense.setExpId(auditing.getExpId());
            expense.setNextAuditor(nextAuditor);
            expense.setLastResult(auditing.getResult());
            expense.setStatus(expenseStatus);

            expenseDao.updateExpense(expense);

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void audit(Auditing auditing) {

        String result = auditing.getResult();
        Connection connection = null;
        try {
            connection = DBUtil2.getConnection();

            // 如果通过审核
            if (Constant.LAST_RESULT_ADOPT.equals(result)) {
                Employee employee = auditing.getEmployee();
                int expId = auditing.getExpId();
                Expense expenseByExpId = expenseDao.getExpenseByExpId(expId);
                // 如果是财务的话
                if (employee.getPosition().getPosId() == Constant.FINANCIAL_POSITION_ID) {
                    // 通过  打款, 添加一条支出记录
                    Payment payment = new Payment();
                    payment.setPayEmpId(employee.getEmpId());
                    payment.setPayEmp(employee);
                    payment.setAmount(expenseByExpId.getTotalAmount());
                    payment.setPayTime(new Date());
                    payment.setExpId(auditing.getExpId());
                    payment.setExpense(expenseByExpId);
                    payment.setEmpId(expenseByExpId.getEmpId());
                    payment.setEmp(expenseByExpId.getEmployee());
                    // 插入一条支出数据
                    paymentDao.insertPayment(payment);

                    // 添加审核记录, 更改报销单, 下一个审核人为null
                    addAuditing(auditing, null, Constant.EXPENSE_STATUS_ADOPT);
                } else { // 不是财务

                    // 如果金额大于2500
                    if (expenseByExpId.getTotalAmount() >= Constant.MONEY_THRESHOLD) {
                        // 如果是总裁的话
                        if (employee.getPosition().getPosId() == Constant.CEO_POSITION_ID) {
                            // 添加审核记录, 下一个审核人转到财务, 修改报销单状态为通过
                            addAuditing(auditing, Constant.FINANCIAL_EMPLOYEE_ID, Constant.EXPENSE_STATUS_AUDITING);
                        } else { // 不是总裁
                            // 添加审核记录, 下一个审核人转到上一级, 修改报销单状态为通过
                            addAuditing(auditing, employee.getMgr().getEmpId(), Constant.EXPENSE_STATUS_AUDITING);
                        }
                    } else { // 不是大金额
                        // 添加审核记录, 下一个审核人转到上一级, 修改报销单状态为通过
                        addAuditing(auditing, Constant.FINANCIAL_EMPLOYEE_ID, Constant.EXPENSE_STATUS_AUDITING);
                    }
                }
            } else { // 没有通过审核
                // 添加审核记录, 下一个审核人转到上一级, 修改报销单状态为通过
                addAuditing(auditing, null, Constant.EXPENSE_STATUS_REJECT);
            }

            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Auditing> findAuditingByExpId(int expId) {

        Connection connection = null;
        List<Auditing> auditingList = null;
        try {
            connection = DBUtil2.getConnection();

            auditingList = auditingDao.listAuditingByExpId(expId);

            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }

        return auditingList;
    }

    @Override
    public List<Auditing> findAuditingByEmpId(String empId) {
        Connection connection = null;
        List<Auditing> auditingList = null;
        try {
            connection = DBUtil2.getConnection();

            auditingList = auditingDao.listAuditingByEmpId(empId);

            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }

        return auditingList;
    }
}
