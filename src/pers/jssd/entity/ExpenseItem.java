package pers.jssd.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 报销单明细表实体类
 *
 * @author jssdjing@gmail.com
 */
public class ExpenseItem implements Serializable {

    private int itemId;
    private int expId; // 属于的报销单的id
    private String type; // 报销类型
    private int amount; // 明细金额
    private String itemDesc;// 报销明细描述

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseItem that = (ExpenseItem) o;
        return itemId == that.itemId &&
                expId == that.expId &&
                amount == that.amount &&
                Objects.equals(type, that.type) &&
                Objects.equals(itemDesc, that.itemDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, expId, type, amount, itemDesc);
    }

    @Override
    public String toString() {
        return "ExpenseItem{" +
                "itemId=" + itemId +
                ", expId=" + expId +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public ExpenseItem(int itemId, int expId, String type, int amount, String itemDesc) {
        this.itemId = itemId;
        this.expId = expId;
        this.type = type;
        this.amount = amount;
        this.itemDesc = itemDesc;
    }

    public ExpenseItem() {
    }
}
