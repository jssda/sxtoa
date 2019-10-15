package pers.jssd.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author jssdjing@gmail.com
 */
public class Dept implements Serializable, Comparable<Dept> {
    private int deptNo;
    private String deptName;
    private String location;

    @Override
    public String toString() {
        return "Dept{" +
                "deptNo=" + deptNo +
                ", deptName='" + deptName + '\'' +
                ", location='" + location + '\'' +
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
        Dept dept = (Dept) o;
        return deptNo == dept.deptNo &&
                Objects.equals(deptName, dept.deptName) &&
                Objects.equals(location, dept.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptNo, deptName, location);
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Dept(int deptNo) {
        this.deptNo = deptNo;
    }

    public Dept(int deptNo, String deptName, String location) {
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.location = location;
    }

    public Dept() {
    }

    @Override
    public int compareTo(Dept o) {
        return deptNo - o.deptNo;
    }
}
