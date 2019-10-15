package pers.jssd.servlet;

import pers.jssd.entity.Dept;
import pers.jssd.service.DeptService;
import pers.jssd.service.impl.DeptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DeptServlet extends BaseServlet {

    private DeptService service = new DeptServiceImpl();

    /**
     * 添加部门方法
     *
     * @param req 请求
     * @param resp 响应
     */
    public void addDept(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取客户端传递的参数
        String deptNo = req.getParameter("deptNo");
        String deptName = req.getParameter("deptName");
        String location = req.getParameter("location");

        Dept dept = new Dept(Integer.parseInt(deptNo), deptName, location);
        int i = service.addDept(dept);
        if (i == 0) { // 添加失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/deptAdd.jsp").forward(req, resp);
        } else { // 添加成功
            resp.sendRedirect(req.getContextPath() + "/servlet/deptServlet?method=findDepts");
        }
    }

    /**
     * 查找所有的department
     *
     * @param req 请求
     * @param resp 响应
     */
    public void findDepts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Dept> deptList = service.findDepts();

        req.setAttribute("deptList", deptList);
        req.getRequestDispatcher("/system/deptList.jsp").forward(req, resp);
    }

    public void findDeptById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deptNo = req.getParameter("deptNo");

        Dept dept = service.findDeptById(Integer.parseInt(deptNo));
        if (dept != null) {
            dept.setDeptNo(Integer.parseInt(deptNo));
            req.setAttribute("dept", dept);
            req.getRequestDispatcher("/system/deptUpdate.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/deptServlet?method=findDepts").forward(req, resp);
        }
    }

    /**
     * 删除部门方法
     *
     * @param req 请求
     * @param resp 响应
     */
    public void removeDept(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取客户端传递的参数
        String deptNo = req.getParameter("deptNo");

        int i = service.removeDept(Integer.parseInt(deptNo));
        if (i == 0) { // 删除失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/deptServlet?method=findDepts").forward(req, resp);
        } else { // 删除成功
            resp.sendRedirect(req.getContextPath() + "/servlet/deptServlet?method=findDepts");
        }
    }

    /**
     * 更改dept方法
     *
     * @param req 请求
     * @param resp 响应
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取客户端传递的参数
        String deptNo = req.getParameter("deptNo");
        String deptName = req.getParameter("deptName");
        String location = req.getParameter("location");

        Dept dept = new Dept(Integer.parseInt(deptNo), deptName, location);

        int i = service.updateDept(dept);
        if (i == 0) { // 修改失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/deptUpdate.jsp").forward(req, resp);
        } else { // 修改成功
            resp.sendRedirect(req.getContextPath() + "/servlet/deptServlet?method=findDepts");
        }
    }
}
