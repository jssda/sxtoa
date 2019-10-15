package pers.jssd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 员工表实体类
 *
 * @author jssdjing@gmail.com
 */
public class Employee implements Serializable {

    public Employee(String empId, String password, Dept dept, Position position) {
        this.empId = empId;
        this.password = password;
        this.dept = dept;
        this.position = position;
    }

    public Employee(String empId, Dept dept, Position position, Employee mgr, String realName, String sex, Date birthDate, Date hireDate, Date leaveDate, int onDuty, int empType, String phone, String qq, String emerContactPerson, String idCard) {
        this.empId = empId;
        this.dept = dept;
        this.position = position;
        this.mgr = mgr;
        this.realName = realName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.onDuty = onDuty;
        this.empType = empType;
        this.phone = phone;
        this.qq = qq;
        this.emerContactPerson = emerContactPerson;
        this.idCard = idCard;
    }

    private String empId;
    private String password;
    private Dept dept;
    private Position position;
    private Employee mgr;
    private String realName;
    private String sex;
    private Date birthDate;
    private Date hireDate;
    private Date leaveDate;
    private int onDuty; //0-离职  1-在职
    private int empType; // 1.普通员工  2.管理人员 含经理、总监、总裁等  3.管理
    private String phone;
    private String qq;

    public Employee(String empId, int onDuty) {
        this.empId = empId;
        this.onDuty = onDuty;
    }

    private String emerContactPerson; // 紧急联系人
    private String idCard; // 身份证号

    private List<Employee> subordinates; // 下级

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", password='" + password + '\'' +
                ", dept=" + dept +
                ", position=" + position +
                ", mgr=" + mgr +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthDate=" + birthDate +
                ", hireDate=" + hireDate +
                ", leaveDate=" + leaveDate +
                ", onDuty=" + onDuty +
                ", empType=" + empType +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", emerContactPerson='" + emerContactPerson + '\'' +
                ", idCard='" + idCard + '\'' +
                ", subordinates=" + subordinates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return onDuty == employee.onDuty &&
                empType == employee.empType &&
                Objects.equals(empId, employee.empId) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(dept, employee.dept) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(mgr, employee.mgr) &&
                Objects.equals(realName, employee.realName) &&
                Objects.equals(sex, employee.sex) &&
                Objects.equals(birthDate, employee.birthDate) &&
                Objects.equals(hireDate, employee.hireDate) &&
                Objects.equals(leaveDate, employee.leaveDate) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(qq, employee.qq) &&
                Objects.equals(emerContactPerson, employee.emerContactPerson) &&
                Objects.equals(idCard, employee.idCard) &&
                Objects.equals(subordinates, employee.subordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, password, dept, position, mgr, realName, sex, birthDate, hireDate, leaveDate, onDuty, empType, phone, qq, emerContactPerson, idCard, subordinates);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Employee getMgr() {
        return mgr;
    }

    public void setMgr(Employee mgr) {
        this.mgr = mgr;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Employee(String empId, String realName) {
        this.empId = empId;
        this.realName = realName;
    }

    public Employee(String empId) {
        this.empId = empId;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(int onDuty) {
        this.onDuty = onDuty;
    }

    public int getEmpType() {
        return empType;
    }

    public void setEmpType(int empType) {
        this.empType = empType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmerContactPerson() {
        return emerContactPerson;
    }

    public void setEmerContactPerson(String emerContactPerson) {
        this.emerContactPerson = emerContactPerson;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Employee(String empId, String password, Dept dept, Position position, Employee mgr, String realName, String sex, Date birthDate, Date hireDate, Date leaveDate, int onDuty, int empType, String phone, String qq, String emerContactPerson, String idCard) {
        this.empId = empId;
        this.password = password;
        this.dept = dept;
        this.position = position;
        this.mgr = mgr;
        this.realName = realName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.onDuty = onDuty;
        this.empType = empType;
        this.phone = phone;
        this.qq = qq;
        this.emerContactPerson = emerContactPerson;
        this.idCard = idCard;
    }

    public Employee(String empId, String password, Dept dept, Position position, Employee mgr, String realName, String sex, Date birthDate, Date hireDate, Date leaveDate, int onDuty, int empType, String phone, String qq, String emerContactPerson, String idCard, List<Employee> subordinates) {
        this.empId = empId;
        this.password = password;
        this.dept = dept;
        this.position = position;
        this.mgr = mgr;
        this.realName = realName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.onDuty = onDuty;
        this.empType = empType;
        this.phone = phone;
        this.qq = qq;
        this.emerContactPerson = emerContactPerson;
        this.idCard = idCard;
        this.subordinates = subordinates;
    }

    public Employee() {
    }
}
