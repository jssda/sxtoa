package pers.jssd.dao;

import pers.jssd.entity.Auditing;
import pers.jssd.util.PageBean;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface AuditingDao {

    /**
     * 添加一个审核记录
     *
     * @param auditing 需要添加的审核记录
     * @return 返回添加的条数
     * @throws Exception 添加进入数据库中产生的异常
     */
    int insertAuditing(Auditing auditing) throws Exception;

    /**
     * 查询指定报销单中所有的审核记录
     *
     * @param expId 指定的报销单
     * @return 返回查到的审核记录
     * @throws SQLException 查询过程中产生的异常
     */
    List<Auditing> listAuditingByExpId(int expId) throws SQLException;

    /**
     * 查询指定用户的所有审核信息
     *
     * @param empId 指定的用户id
     * @return 返回查找到的所有用户的审核的容器
     * @throws SQLException 查询过程中产生的异常
     */
    List<Auditing> listAuditingByEmpId(String empId) throws SQLException;

    /**
     * 分页查询指定用户的所有审核信息
     *
     * @param empId    指定的用户id
     * @param startRow 分页开始的记录位置
     * @param endRow   分页结束的记录位置
     * @return 返回查找到的所有用户的审核的容器
     * @throws SQLException 查询过程中产生的异常
     */
    List<Auditing> listAuditingByEmpId(String empId, int startRow, int endRow) throws SQLException;

    /**
     * 查找指定条件下的所有审核信息
     *
     * @param empId 指定的用户id
     * @return 返回一共有多少条数据
     */
    int getAuditingSumBy(String empId) throws SQLException;
}
