package pers.jssd.service;

import pers.jssd.entity.Duty;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface DutyService {

    /**
     * 签到
     *
     * @param duty 考勤实体类
     * @return 返回签到状态, 0为签到失败, 1:签到成功, 2:签到失败
     */
    int signIn(Duty duty);

    /**
     * 签退
     *
     * @param duty 考勤实体类
     * @return 返回签退的状态, 0为签退失败, 1:签退成功, 2:没有签到
     */
    int signOut(Duty duty);


    /**
     * 根据条件查询考勤
     *
     * @param empId 查询的员工id
     * @param deptNo 查询的部门编号
     * @param sDtDate 查询的指定时间之后
     * @return 返回查询的所有考勤信息
     */
    List<Duty> findDuties(String empId, int deptNo, String sDtDate);


    /**
     * 根据用户id查询此用户所有的考勤信息
     *
     * @param empId 需要查询的用户id
     * @return 返回查询到的所有用户考勤信息
     */
    List<Duty> findDuties(String empId);
}
