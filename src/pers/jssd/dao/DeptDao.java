package pers.jssd.dao;

import pers.jssd.entity.Dept;
import pers.jssd.util.PageBean;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface DeptDao {


    /**
     * 添加一个部门
     *
     * @param dept 需要添加的部门
     * @return 返回添加的条数
     */
    int insertDept(Dept dept);

    /**
     * 查询所有的department 存放入list并返回
     *
     * @return 返回存储所有的department的list容器
     */
    List<Dept> listDepts();

    /**
     * 从数据库中删除一条部门数据
     *
     * @param deptNo 删除部门数据的部门号
     * @return 返回删除部门数据的条数
     */
    int delDept(int deptNo);

    /**
     * 从数据库中通过deptNo查询一条数据并返回
     *
     * @param deptNo 需要查询的deptNo数据
     * @return 返回dept对象
     */
    Dept getDeptById(int deptNo);

    /**
     * 根据id从数据库中修改一个dept
     * @param dept 需要修改的dept
     * @return 返回修改的条数
     */
    int updateDept(Dept dept);

    /**
     * 取得部门一共有多少条记录
     *
     * @return 返回部门总的记录数
     */
    int getDeptSum();

    /**
     * 查询所有的分页数据, 并存储再pageBean中
     * @param pageBean 分页的所有信息
     */
    void listDepts(PageBean<Dept> pageBean);
}
