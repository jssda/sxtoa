package pers.jssd.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pers.jssd.entity.*;
import pers.jssd.service.DeptService;
import pers.jssd.service.InPayService;
import pers.jssd.service.impl.DeptServiceImpl;
import pers.jssd.service.impl.InPayServiceImpl;
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
public class InPayServlet extends BaseServlet {

    private DeptService deptService = new DeptServiceImpl();
    private InPayService inPayService = new InPayServiceImpl();

    /**
     * 展示添加收入得页面
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    public void toIncomeAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Dept> depts = deptService.findDepts();
        req.setAttribute("depts", depts);
        req.getRequestDispatcher("/inpay/incomeAdd.jsp").forward(req, resp);
    }

    /**
     * 录入一个收入
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     */
    public void incomeAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String deptNo = req.getParameter("deptNo");
        String amount = req.getParameter("amount");
        String icType = req.getParameter("icType");
        String icDateStr = req.getParameter("icDate");
        String icDesc = req.getParameter("icDesc");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        HttpSession session = req.getSession();
        Employee currUser = (Employee) session.getAttribute("currUser");

        try {
            Income income = new Income();
            income.setAmount(Integer.parseInt(amount));
            income.setIcType(icType);
            income.setIcDate(format.parse(icDateStr));
            income.setInDesc(icDesc);
            income.setUserId(currUser.getEmpId());
            income.setUser(currUser);

            int i = inPayService.addIncome(income);
            if (i != 0) {
                resp.sendRedirect(req.getContextPath() + "/inpay/incomeList.jsp");
            } else {
                req.setAttribute("error", "保存错误");
                req.getRequestDispatcher("/inpay/incomeAdd.jsp").forward(req, resp);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有得Income列表
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     */
    public void findIncomeBy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 取得当前页
        PageBean<Income> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        pageBean.setIndex(index);

        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String icType = req.getParameter("icType");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = null;
            if (startTime != null && !"".equals(startTime.trim())) {
                start = format.parse(startTime);
            }
            Date end = null;
            if (endTime != null && !"".equals(endTime.trim())) {
                end = format.parse(endTime);
            }
            inPayService.findIncomeBy(start, end, icType, pageBean);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            resp.getWriter().println(gson.toJson(pageBean));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有的支出数据
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    public void findPaymentBy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 取得当前页
        PageBean<Payment> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        pageBean.setIndex(index);

        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String payEmpId = req.getParameter("payEmpId");
        String paymentType = req.getParameter("paymentType");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = null;
            if (startTime != null && !"".equals(startTime.trim())) {
                start = format.parse(startTime);
            }
            Date end = null;
            if (endTime != null && !"".equals(endTime.trim())) {
                end = format.parse(endTime);
            }
            inPayService.findPaymentBy(start, end, payEmpId, paymentType, pageBean);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            resp.getWriter().println(gson.toJson(pageBean));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看收入统计
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void showIncomeStatic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 查找到所有得收入列表
        List<Income> incomeList = inPayService.findIncomeBy(null, null, "0");

        String json = inPayService.getIncomeStaticStr();

        resp.getWriter().println(json);
    }

    /**
     * 查看支出统计
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void showPaymentStatic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String val = req.getParameter("val");
        String str = inPayService.getPaymentStaticStr(val);

        resp.getWriter().println(str);
    }
}