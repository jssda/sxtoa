package pers.jssd.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 数据库报销单实体类
 *
 * @author jssdjing@gmail.com
 */
public class Expense implements Serializable {

    /**
     * 报销单id
     */
    private int expId;

    /**
     * 报销用户id
     */
    private String empId;

    /**
     * 报销总额
     */
    private int totalAmount;

    /**
     * 报销日期
     */
    private Date expTime;

    /**
     * 报销单描述
     */
    private String expDesc;

    /**
     * 下一个审核人员
     */
    private String nextAuditor;

    /**
     * 最终结果
     */
    private String lastResult;

    /**
     * 审核状态 1：审核中   2 审核结束通过  3 审核结束驳回
     */
    private String status;

    /**
     * 报销单用户
     */
    private Employee employee;

    /**
     * 下一个审核人
     */
    private Employee auditor;

    public Employee getAuditor() {
        return auditor;
    }

    public void setAuditor(Employee auditor) {
        this.auditor = auditor;
    }

    /**
     *  报销单详情
     */
    private List<ExpenseItem> expenseItems = new ArrayList<>();

    @Override
    public String toString() {
        return "Expense{" +
                "expId=" + expId +
                ", empId='" + empId + '\'' +
                ", totalAmount=" + totalAmount +
                ", expTime=" + expTime +
                ", expDesc='" + expDesc + '\'' +
                ", nextAuditor='" + nextAuditor + '\'' +
                ", lastResult='" + lastResult + '\'' +
                ", status='" + status + '\'' +
                ", employee=" + employee +
                ", expenseItems=" + expenseItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expense expense = (Expense) o;
        return expId == expense.expId &&
                totalAmount == expense.totalAmount &&
                Objects.equals(empId, expense.empId) &&
                Objects.equals(expTime, expense.expTime) &&
                Objects.equals(expDesc, expense.expDesc) &&
                Objects.equals(nextAuditor, expense.nextAuditor) &&
                Objects.equals(lastResult, expense.lastResult) &&
                Objects.equals(status, expense.status) &&
                Objects.equals(employee, expense.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expId, empId, totalAmount, expTime, expDesc, nextAuditor, lastResult, status, employee);
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    public String getNextAuditor() {
        return nextAuditor;
    }

    public void setNextAuditor(String nextAuditor) {
        this.nextAuditor = nextAuditor;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Expense(int expId, String empId, int totalAmount, Date expTime, String expDesc, String nextAuditor, String lastResult, String status, Employee employee) {
        this.expId = expId;
        this.empId = empId;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.nextAuditor = nextAuditor;
        this.lastResult = lastResult;
        this.status = status;
        this.employee = employee;
    }

    public Expense() {
    }

    public List<ExpenseItem> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(List<ExpenseItem> expenseItems) {
        this.expenseItems = expenseItems;
    }
}
