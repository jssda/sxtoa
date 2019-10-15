package pers.jssd.dao;

import pers.jssd.entity.Employee;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface EmployeeDao {

    /**
     * 向数据库中添加一个用户的数据
     *
     * @param employee 需要添加的用户
     * @return 返回添加的条数
     */
    int insertEmployee(Employee employee);

    /**
     * 从数据库中查找所有的Employee雇员信息
     * @return 返回一个存储作用的雇员信息的list
     */
    List<Employee> listEmployees();

    /**
     * 从数据库中按照条件查找
     *
     * @param employee 查找的条件封装的employee对象
     * @return 返回所有找到的employee对象
     */
    List<Employee> listEmployeesBy(Employee employee);

    /**
     * 从数据库中更新一条Employee数据, 通过EmployeeId
     *
     * @param employee 需要更新的Employee
     * @return 返回更新的数据
     */
    int updateEmployee(Employee employee);

    /**
     * 在数据库中使用用户id删除一个用户
     *
     * @param empId 需要删除的用户id
     * @return 删除的条数
     */
    int deleteEmployee(String empId);

    /**
     * 在数据库中更新用户的密码
     *
     * @param empId 需要更新的用户id
     * @param newPwd 需要更新的新密码
     * @return 返回更新后的条数
     */
    int updatePwd(String empId, String newPwd);
}
