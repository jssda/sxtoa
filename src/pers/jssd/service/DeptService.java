package pers.jssd.service;

import pers.jssd.entity.Dept;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface DeptService {

    /**
     * 添加一个部门
     * @param dept 需要添加的部门对象
     * @return 返回成功添加的部门个数
     */
    int addDept(Dept dept);

    /**
     * 查找出所有的Department
     *
     * @return 返回一个存储所有Department的list容器
     */
    List<Dept> findDepts();

    /**
     * 删除一个部门
     *
     * @param deptNo 删除部门的部门号
     * @return 返回删除的部门条数
     */
    int removeDept(int deptNo);

    /**
     * 通过部门id查找一个部门
     *
     * @param deptNo 需要查找的部门id
     * @return 返回找到的部门对象
     */
    Dept findDeptById(int deptNo);

    /**
     * 根据id修改一个dept
     *
     * @param dept 需要修改的dept对象
     * @return 返回修改的条数
     */
    int updateDept(Dept dept);
}
