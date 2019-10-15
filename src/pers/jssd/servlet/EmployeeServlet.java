package pers.jssd.servlet;

import pers.jssd.entity.Dept;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Position;
import pers.jssd.service.DeptService;
import pers.jssd.service.EmployeeService;
import pers.jssd.service.PositionService;
import pers.jssd.service.impl.DeptServiceImpl;
import pers.jssd.service.impl.EmployeeServiceImpl;
import pers.jssd.service.impl.PositionServiceImpl;
import pers.jssd.util.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class EmployeeServlet extends BaseServlet {

    private EmployeeService employeeService = new EmployeeServiceImpl();
    private DeptService deptService = new DeptServiceImpl();
    private PositionService positionService = new PositionServiceImpl();

    /**
     * 员工退出
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException IO异常
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/system/login.jsp");
    }


    /**
     * 员工登录检测
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String verifyCode = req.getParameter("verifyCode");

        // 如果验证码错误
        if (verifyCode == null || "".equals(verifyCode.trim()) || !session.getAttribute("randStr").equals(verifyCode)) {
            req.setAttribute("error", "验证码错误");
            req.getRequestDispatcher("/system/login.jsp").forward(req, resp);
            return;
        }

        Employee employee = employeeService.login(userName, password);
        if (employee == null) { // 登录失败
            req.setAttribute("error", "用户名或者密码错误");
            req.getRequestDispatcher("/system/login.jsp").forward(req, resp);
        } else {
            session.setAttribute("currUser", employee);
            resp.sendRedirect(req.getContextPath() + "/main.jsp");
        }
    }

    /**
     * 添加员工信息
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void addEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 新建对象, 使用EmployeeService对象添加
        Employee employee = getEmpFromReq(req, resp);

        int i = employeeService.addEmployee(employee);
        if (i == 0) { // 添加失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/empAdd.jsp").forward(req, resp);
        } else { // 添加成功
            resp.sendRedirect(req.getContextPath() + "/servlet/employeeServlet?method=findEmployees");
        }
    }


    /**
     * 查找所有员工
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void findEmployees(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = employeeService.findEmployees();

        List<Dept> depts = deptService.findDepts();
        req.setAttribute("depts", depts);

        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/system/empList.jsp").forward(req, resp);
    }


    /**
     * 显示添加员工的界面
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void showAddEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 查找出所有的部门
        List<Dept> depts = deptService.findDepts();
        req.setAttribute("depts", depts);

        // 查找出所有的职位
        List<Position> positions = positionService.findPositions();
        req.setAttribute("positions", positions);

        // 查找出所有管理人员
        Employee mgr = new Employee();
        mgr.setEmpType(2);
        mgr.setOnDuty(1);
        List<Employee> mgrs = employeeService.findEmployeesBy(mgr);
        req.setAttribute("mgrs", mgrs);

        req.getRequestDispatcher("/system/empAdd.jsp").forward(req, resp);
    }


    /**
     * 根据条件查询员工
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void findEmployeesBy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 查看分页当前页
        PageBean<Employee> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;
        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        pageBean.setIndex(index);

        // 记录查询条件的一些数据, 提供查询到数据之后的条件回显
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Employee mgr = new Employee();
        String employeeName = req.getParameter("employeeName");
        req.setAttribute("employeeName", employeeName);
        String deptNo = req.getParameter("deptNo");
        req.setAttribute("deptNo", deptNo);
        String onDuty = req.getParameter("onDuty");
        req.setAttribute("onDuty", onDuty);
        String hireDate = req.getParameter("hireDate");
        req.setAttribute("hireDate", hireDate);

        mgr.setRealName(employeeName);

        if (deptNo != null && !"".equals(deptNo.trim())) {
            mgr.setDept(new Dept(Integer.parseInt(deptNo)));
        }

        if (onDuty != null && !"".equals(onDuty.trim())) {
            mgr.setOnDuty(Integer.parseInt(onDuty));
        } else {
            mgr.setOnDuty(3);
        }

        try {
            if (hireDate != null && !"".equals(hireDate.trim()))
                mgr.setHireDate(dateFormat.parse(hireDate));
        } catch (ParseException e) {
            System.err.println("时间格式转换错误");
            e.printStackTrace();
        }

        // 查询数据
        employeeService.findEmployeesBy(pageBean, mgr);

        List<Dept> depts = deptService.findDepts();
        req.setAttribute("depts", depts);

        req.setAttribute("pageBean", pageBean);

        req.getRequestDispatcher("/system/empList.jsp").forward(req, resp);
    }


    /**
     * 查看用户信息
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void viewUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("empId");
        Employee emp = new Employee();
        emp.setEmpId(empId);
        emp.setOnDuty(3);

        List<Employee> employeesBy = employeeService.findEmployeesBy(emp);
        Employee employee = employeesBy.get(0);

        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/system/empInfo.jsp").forward(req, resp);
    }

    /**
     * 展示修改用户的界面
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void toUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("empId");
        Employee emp = new Employee();
        emp.setEmpId(empId);
        emp.setOnDuty(3);

        List<Employee> employeesBy = employeeService.findEmployeesBy(emp);
        Employee employee = employeesBy.get(0);

        req.setAttribute("employee", employee);


        // 查找出所有的部门
        List<Dept> depts = deptService.findDepts();
        req.setAttribute("depts", depts);

        // 查找出所有的职位
        List<Position> positions = positionService.findPositions();
        req.setAttribute("positions", positions);

        // 查找出所有管理人员
        Employee mgr = new Employee();
        mgr.setEmpType(2);
        mgr.setOnDuty(1);
        List<Employee> mgrs = employeeService.findEmployeesBy(mgr);
        req.setAttribute("mgrs", mgrs);

        req.getRequestDispatcher("/system/empUpdate.jsp").forward(req, resp);
    }


    /**
     * 更新用户信息
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = getEmpFromReq(req, resp);

        int i = employeeService.updateEmployee(employee);
        if (i == 0) { // 更新失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/empUpdate.jsp").forward(req, resp);
        } else { // 更新成功
            resp.sendRedirect(req.getContextPath() + "/servlet/employeeServlet?method=findEmployees");
        }

    }


    /**
     * 根据用户id删除一个用户
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 取得用户Id
        String empId = req.getParameter("empId");

        System.out.println(empId);
        int i = employeeService.deleteEmployee(empId);
        if (i == 0) { // 删除失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/employeeServlet?method=findEmployees").forward(req, resp);
        } else { // 删除成功
            resp.sendRedirect(req.getContextPath() + "/servlet/employeeServlet?method=findEmployees");
        }
    }


    /**
     * 转到更改用户密码的页面
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void toUpdatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("empId");
        Employee employee = employeeService.findEmployeesBy(new Employee(empId, 3)).get(0);
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/personal/pwdUpdate.jsp").forward(req, resp);
    }


    /**
     * 更改用户的密码
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("empId");
        String oldPwd = req.getParameter("oldPwd");
        String newPwd = req.getParameter("newPwd");

        Employee employee = employeeService.findEmployeesBy(new Employee(empId, 3)).get(0);
        if (oldPwd == null || "".equals(oldPwd.trim()) || !oldPwd.equals(employee.getPassword())) {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/personal/pwdUpdate.jsp").forward(req, resp);
        } else {
            int i = employeeService.updatePwd(empId, newPwd);
            if (i == 0) {
                System.out.println("更新失败");
                req.setAttribute("error", true);
                req.getRequestDispatcher("/personal/pwdUpdate.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/servlet/employeeServlet?method=viewUserInfo&empId=" + empId);
            }
        }

    }

    /**
     * 从请求参数中取得信息并封装成一个employee对象
     *
     * @param req 请求
     * @param resp 响应
     */

    private Employee getEmpFromReq(HttpServletRequest req, HttpServletResponse resp) {
        // 时间格式化
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

        String empId = req.getParameter("empId");
        String realName = req.getParameter("realName");
        String sex = req.getParameter("sex");

        // 处理日期类型
        Date birthDate = null;
        try {
            birthDate = dataFormat.parse(req.getParameter("birthDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date hireDate = null;
        try {
            hireDate = dataFormat.parse(req.getParameter("hireDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date leaveDate = null;
        try {
            leaveDate = dataFormat.parse(req.getParameter("leaveDate"));
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        String sOnDuty = req.getParameter("onDuty");
        int onDuty = Integer.parseInt(sOnDuty);
        String sEmpType = req.getParameter("empType");
        int empType = Integer.parseInt(sEmpType);

        // 处理映射对象类型
        String deptNo = req.getParameter("deptNo");
        Dept dept = null;
        if (deptNo != null && !"".equals(deptNo.trim())) {
            dept = new Dept();
            dept.setDeptNo(Integer.parseInt(deptNo));
        }

        String posId = req.getParameter("posId");
        Position position = null;
        if (posId != null && !"".equals(posId.trim())) {
            position = new Position();
            position.setPosId(Integer.parseInt(posId));
        }

        String mgrId = req.getParameter("mgrId");
        Employee mgr = null;
        if (mgrId != null && !"".equals(mgrId.trim())) {
            mgr = new Employee();
            mgr.setEmpId(mgrId);
        }

        String phone = req.getParameter("phone");
        String qq = req.getParameter("qq");
        String ecp = req.getParameter("ecp");
        String idCard = req.getParameter("idCard");

        // 新建对象, 使用EmployeeService对象添加
        return new Employee(empId, dept, position, mgr, realName, sex, birthDate, hireDate, leaveDate, onDuty, empType, phone
                , qq, ecp, idCard);
    }

}
