package pers.jssd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 考勤管理实体类
 *
 * @author jssdjing@gmail.com
 */
public class Duty implements Serializable {

    private int dtId;
    private String empId;
    private Date dtDate;
    private String signInTime;
    private String signOutTime;

    private Employee employee;

    @Override
    public String toString() {
        return "Duty{" +
                "dtId=" + dtId +
                ", empId='" + empId + '\'' +
                ", dtDate=" + dtDate +
                ", signInTime='" + signInTime + '\'' +
                ", signOutTime='" + signOutTime + '\'' +
                ", employee=" + employee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duty duty = (Duty) o;
        return dtId == duty.dtId &&
                Objects.equals(empId, duty.empId) &&
                Objects.equals(dtDate, duty.dtDate) &&
                Objects.equals(signInTime, duty.signInTime) &&
                Objects.equals(signOutTime, duty.signOutTime) &&
                Objects.equals(employee, duty.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtId, empId, dtDate, signInTime, signOutTime, employee);
    }

    public int getDtId() {
        return dtId;
    }

    public void setDtId(int dtId) {
        this.dtId = dtId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getDtDate() {
        return dtDate;
    }

    public void setDtDate(Date dtDate) {
        this.dtDate = dtDate;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Duty(int dtId, String empId, Date dtDate, String signInTime, String signOutTime, Employee employee) {
        this.dtId = dtId;
        this.empId = empId;
        this.dtDate = dtDate;
        this.signInTime = signInTime;
        this.signOutTime = signOutTime;
        this.employee = employee;
    }

    public Duty() {
    }
}
