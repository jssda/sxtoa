package pers.jssd.service.impl;

import pers.jssd.dao.EmployeeDao;
import pers.jssd.dao.impl.EmployeeDaoImpl;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Employee;
import pers.jssd.service.EmployeeService;
import pers.jssd.util.PageBean;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public int addEmployee(Employee employee) {
        return employeeDao.insertEmployee(employee);
    }

    @Override
    public List<Employee> findEmployees() {
        return employeeDao.listEmployees();
    }

    @Override
    public List<Employee> findEmployeesBy(Employee employee) {
        return employeeDao.listEmployeesBy(employee);
    }

    @Override
    public int updateEmployee(Employee employee) {
        return employeeDao.updateEmployee(employee);
    }

    @Override
    public int deleteEmployee(String empId) {
        return employeeDao.deleteEmployee(empId);
    }

    @Override
    public int updatePwd(String empId, String newPwd) {
        return employeeDao.updatePwd(empId, newPwd);
    }

    @Override
    public Employee login(String userName, String password) {
        List<Employee> employees = employeeDao.listEmployeesBy(new Employee(userName, 3));
        if (employees.size() == 0) {
            return null;
        }

        Employee emp = employees.get(0);
        if (password == null || "".equals(password.trim()) || !password.equals(emp.getPassword())) {
            return null;
        }

        if (emp.getEmpId().equals(userName) && emp.getPassword().equals(password)) {
            return emp;
        }

        return null;
    }

    @Override
    public void findEmployeesBy(PageBean<Employee> pageBean, Employee mgr) {
        // 设置每页显示的数据量
        pageBean.setSize(4);
        // 设置默认的显示页码数组长度
        pageBean.setDefaultNumberLength(5);

        // 查询再指定条件下的数组总数
        int sum = employeeDao.getSumBy(mgr);

        // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
        pageBean.setTotalCount(sum);

        int startRow = pageBean.getStartRow();
        int endRow = pageBean.getEndRow();

        List<Employee> list = employeeDao.listEmployeesBy(mgr, startRow, endRow);
        pageBean.setList(list);
    }

}
