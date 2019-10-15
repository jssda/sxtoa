package pers.jssd.service;

import pers.jssd.entity.Auditing;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface AuditService {

    /**
     * 审核
     *
     * @param auditing 一条审核记录
     */
    void audit(Auditing auditing);

    /**
     * 查找审核历史
     *
     * @param expId 查找的报销单
     * @return 返回找到的所有审核历史
     */
    List<Auditing> findAuditingByExpId(int expId);

    /**
     * 查找指定用户所有的审核记录
     * @param empId 指定的用户
     * @return 查到的所有审核信息的容器
     */
    List<Auditing> findAuditingByEmpId(String empId);
}
