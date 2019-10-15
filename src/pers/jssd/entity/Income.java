package pers.jssd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 收入实体类
 *
 * @author jssdjing@gmail.com
 */
public class Income implements Serializable {

    /**
     * 收入id
     */
    private int icId;

    /**
     * 收入金额
     */
    private int amount;

    /**
     * 收入时间
     */
    private Date icDate;

    /**
     * 收入类型
     */
    private String icType;

    /**
     * 收入说明
     */
    private String inDesc;

    /**
     * 录入id
     */
    private String userId;

    /**
     * 录入用户
     */
    private Employee user;

    @Override
    public String toString() {
        return "Income{" +
                "icId=" + icId +
                ", amount=" + amount +
                ", icDate=" + icDate +
                ", icType='" + icType + '\'' +
                ", inDesc='" + inDesc + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return icId == income.icId &&
                amount == income.amount &&
                Objects.equals(icDate, income.icDate) &&
                Objects.equals(icType, income.icType) &&
                Objects.equals(inDesc, income.inDesc) &&
                userId.equals(income.userId) &&
                Objects.equals(user, income.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icId, amount, icDate, icType, inDesc, userId, user);
    }

    public int getIcId() {
        return icId;
    }

    public void setIcId(int icId) {
        this.icId = icId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getIcDate() {
        return icDate;
    }

    public void setIcDate(Date icDate) {
        this.icDate = icDate;
    }

    public String getIcType() {
        return icType;
    }

    public void setIcType(String icType) {
        this.icType = icType;
    }

    public String getInDesc() {
        return inDesc;
    }

    public void setInDesc(String inDesc) {
        this.inDesc = inDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Income(int icId, int amount, Date icDate, String icType, String inDesc, String userId) {
        this.icId = icId;
        this.amount = amount;
        this.icDate = icDate;
        this.icType = icType;
        this.inDesc = inDesc;
        this.userId = userId;
    }

    public Income() {
    }
}
