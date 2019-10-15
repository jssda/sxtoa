package pers.jssd.test;

import org.junit.Test;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Position;
import pers.jssd.service.EmployeeService;
import pers.jssd.service.impl.EmployeeServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class EmployeeTest {

    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    public void addEmployeeTest() {

        Employee employee = null;
        try {
            employee = new Employee(
                    "wang",
                    new Dept(5, null, null),
                    new Position(1, null, null),
                    new Employee("gaoqi", "123456", null, null),
                    "刘浩",
                    "男",
                    new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-06"),
                    new SimpleDateFormat("yyyy-MM-dd").parse("2015-04-06"),
                    null,
                    1,
                    2,
                    "15188875022",
                    "1624022009",
                    "傻逼刘浩算不算",
                    "130434199804062115"
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int i = employeeService.addEmployee(employee);
        System.out.println("i = " + i);
    }

    @Test
    public void findEmployeesBy() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Employee employee = new Employee();

        //employee.setSex("男");
        employee.setRealName("王");
        employee.setOnDuty(1);
        try {
            employee.setHireDate(dateFormat.parse("2015-09-11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Employee> employeesBy = employeeService.findEmployeesBy(employee);
        for (Employee employee1 : employeesBy) {
            System.out.println("employee1 = " + employee1);
        }
    }

    @Test
    public void updateEmployee() {
        Employee employee = null;
        try {
            employee = new Employee(
                    "jssd",
                    new Dept(5, null, null),
                    new Position(1, null, null),
                    new Employee("gaoqi", "123456", null, null),
                    "王京京",
                    "女",
                    new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-06"),
                    new SimpleDateFormat("yyyy-MM-dd").parse("2015-04-06"),
                    null,
                    1,
                    2,
                    "15188875022",
                    "1624022009",
                    "傻逼刘浩算不算",
                    "130434199804062115"
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int i = employeeService.updateEmployee(employee);
        System.out.println("i = " + i);
    }
}

