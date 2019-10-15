package pers.jssd.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 职位信息实体类
 *
 * @author jssdjing@gmail.com
 */
public class Position implements Serializable {

    private int posId; // 职位id
    private String pName; // 职位名
    private String pDesc; // 职位描述

    @Override
    public String toString() {
        return "Position{" +
                "posId=" + posId +
                ", pName='" + pName + '\'' +
                ", pDesc='" + pDesc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return posId == position.posId &&
                Objects.equals(pName, position.pName) &&
                Objects.equals(pDesc, position.pDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posId, pName, pDesc);
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public Position(int posId) {
        this.posId = posId;
    }

    public Position(int posId, String pName, String pDesc) {
        this.posId = posId;
        this.pName = pName;
        this.pDesc = pDesc;
    }

    public Position() {
    }
}
