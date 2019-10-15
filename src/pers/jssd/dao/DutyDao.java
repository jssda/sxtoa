package pers.jssd.dao;

import pers.jssd.entity.Duty;

import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface DutyDao {

    /**
     * 从数据库中查询一条数据
     *
     * @param empId 查询的empId
     * @param dtDate 查询的时间
     * @return true 则查询到了数据, false则没有查询到数据
     */
    boolean getDuty(String empId, Date dtDate);

    /**
     * 向数据库中插入一条考勤数据
     *
     * @param duty 需要插入的考勤数据
     * @return 返回插入的条数
     */
    int insertDuty(Duty duty);

    /**
     * 从数据库中更新数据, 更新考勤表的签退时间
     *
     * @param duty 更新的考勤类
     * @return 返回更新的条数
     */
    int updateDuty(Duty duty);

    /**
     * 根据条件从数据库中查询信息
     *
     * @param empId 查询的员工id
     * @param deptNo 查询的部门编号
     * @param sDtDate 查询的时间信息
     * @return 返回查询到的考勤信息
     */
    List<Duty> listDutiesBy(String empId, int deptNo, String sDtDate);

    /**
     * 根据用户id查询所有的考勤信息
     *
     * @param empId 需要查询的用户id条件
     * @return 返回查询到的此用户所有的考勤信息
     */
    List<Duty> listDuties(String empId);
}
