package pers.jssd.entity;

import java.util.Date;
import java.util.Objects;

/**
 * @author jssdjing@gmail.com
 */
public class Payment {

    private int pId;
    private String payEmpId;
    private int amount;
    private Date payTime;
    private int expId;
    private String empId;

    private Employee payEmp;
    private Expense expense;
    private Employee emp;

    public Payment(int pId, String payEmpId, int amount, Date payTime, int expId, String empId) {
        this.pId = pId;
        this.payEmpId = payEmpId;
        this.amount = amount;
        this.payTime = payTime;
        this.expId = expId;
        this.empId = empId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return pId == payment.pId &&
                amount == payment.amount &&
                expId == payment.expId &&
                Objects.equals(payEmpId, payment.payEmpId) &&
                Objects.equals(payTime, payment.payTime) &&
                Objects.equals(empId, payment.empId) &&
                Objects.equals(payEmp, payment.payEmp) &&
                Objects.equals(expense, payment.expense) &&
                Objects.equals(emp, payment.emp);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "pId=" + pId +
                ", payEmpId='" + payEmpId + '\'' +
                ", amount=" + amount +
                ", payTime=" + payTime +
                ", expId=" + expId +
                ", empId='" + empId + '\'' +
                ", payEmp=" + payEmp +
                ", expense=" + expense +
                ", emp=" + emp +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, payEmpId, amount, payTime, expId, empId, payEmp, expense, emp);
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getPayEmpId() {
        return payEmpId;
    }

    public void setPayEmpId(String payEmpId) {
        this.payEmpId = payEmpId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Employee getPayEmp() {
        return payEmp;
    }

    public void setPayEmp(Employee payEmp) {
        this.payEmp = payEmp;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Payment() {
    }
}
