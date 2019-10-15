package pers.jssd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author jssdjing@gmail.com
 */
public class Auditing implements Serializable {

    private int auditId;
    private int expId;
    private String empId;
    private String result;
    private String auditDesc;
    private Date time;

    private Employee employee;
    private Expense expense;

    public Auditing() {
    }

    @Override
    public String toString() {
        return "Auditing{" +
                "auditId=" + auditId +
                ", expId=" + expId +
                ", empId='" + empId + '\'' +
                ", result='" + result + '\'' +
                ", auditDesc='" + auditDesc + '\'' +
                ", time=" + time +
                ", employee=" + employee +
                ", expense=" + expense +
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
        Auditing auditing = (Auditing) o;
        return auditId == auditing.auditId &&
                expId == auditing.expId &&
                Objects.equals(empId, auditing.empId) &&
                Objects.equals(result, auditing.result) &&
                Objects.equals(auditDesc, auditing.auditDesc) &&
                Objects.equals(time, auditing.time) &&
                Objects.equals(employee, auditing.employee) &&
                Objects.equals(expense, auditing.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auditId, expId, empId, result, auditDesc, time, employee, expense);
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Auditing(int auditId, int expId, String empId, String result, String auditDesc, Date time) {
        this.auditId = auditId;
        this.expId = expId;
        this.empId = empId;
        this.result = result;
        this.auditDesc = auditDesc;
        this.time = time;
    }
}
