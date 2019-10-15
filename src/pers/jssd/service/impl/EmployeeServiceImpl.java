package pers.jssd.service.impl;

import pers.jssd.dao.EmployeeDao;
import pers.jssd.dao.impl.EmployeeDaoImpl;
import pers.jssd.entity.Employee;
import pers.jssd.service.EmployeeService;

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

}
