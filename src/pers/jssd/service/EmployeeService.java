package pers.jssd.service;

import pers.jssd.entity.Dept;
import pers.jssd.entity.Employee;
import pers.jssd.util.PageBean;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface EmployeeService {

    /**
     * 添加一个用户信息
     *
     * @param employee 需要添加的用户信息
     * @return 返回添加的条数
     */
    int addEmployee(Employee employee);

    /**
     * 查找作用的Employee雇员
     *
     * @return 返回存储所有的Employee雇员的List
     */
    List<Employee> findEmployees();

    /**
     * 按照条件查找
     *
     * @param employee 封装查找的条件的employee对象
     * @return 返回所有查找到的对象
     */
    List<Employee> findEmployeesBy(Employee employee);

    /**
     * 更新一个用户, 通过EmpId
     *
     * @param employee 需要更新信息的Employee
     * @return 更新的条数
     */
    int updateEmployee(Employee employee);

    /**
     * 根据用户id, 删除一个用户
     *
     * @param empId 需要删除的用户的id
     * @return 返回删除用户的条数
     */
    int deleteEmployee(String empId);

    /**
     * 更改用户密码
     *
     * @param empId 需要更改的用户id
     * @param newPwd 需要更改的新密码
     * @return 返回更新的数据条数
     */
    int updatePwd(String empId, String newPwd);

    /**
     * 检测数据库中是否有这位员工
     *
     * @param userName 登录的员工姓名
     * @param password 登录的员工密码
     *
     * @return 数据库中有这个员工则返回此员工对象
     */
    Employee login(String userName, String password);

    /**
     * 按照条件查询, 并分页
     *
     * @param pageBean 分页信息
     * @param mgr 查询数据的条件
     */
    void findEmployeesBy(PageBean<Employee> pageBean, Employee mgr);
}
