package pers.jssd.servlet;

import pers.jssd.entity.Auditing;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Expense;
import pers.jssd.service.AuditService;
import pers.jssd.service.impl.AuditServiceImpl;
import pers.jssd.util.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class AuditServlet extends BaseServlet {

    private AuditService auditService = new AuditServiceImpl();

    /**
     * 查看报销单审核历史
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void toAuditHistory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int expId = Integer.parseInt(req.getParameter("expId"));
            List<Auditing> auditingByExpId = auditService.findAuditingByExpId(expId);

            req.setAttribute("auditingList", auditingByExpId);
            req.getRequestDispatcher("/expense/auditHistory.jsp").forward(req, resp);

        } catch (NumberFormatException | ServletException e) {
            req.setAttribute("error", e.toString());
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

    /**
     * 查看本人的所有审核信息
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void toMyAudit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 取得当前页
        PageBean<Auditing> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        pageBean.setIndex(index);

        HttpSession session = req.getSession();
        Employee currUser = (Employee) session.getAttribute("currUser");
        auditService.findAuditingByEmpId(currUser.getEmpId(), pageBean);

        req.setAttribute("pageBean", pageBean);
        req.getRequestDispatcher("/expense/myAudit.jsp").forward(req, resp);
    }

    /**
     * 审核报销单
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 重定向产生的异常
     */
    public void audit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int expId = Integer.parseInt(req.getParameter("expId"));
        String result = req.getParameter("result");
        String opinion = req.getParameter("opinion");

        HttpSession session = req.getSession();
        Employee currUser = (Employee) session.getAttribute("currUser");
        String currUserId = currUser.getEmpId();

        Auditing auditing = new Auditing();
        auditing.setExpId(expId);
        auditing.setEmpId(currUserId);
        auditing.setEmployee(currUser);
        auditing.setResult(result);
        auditing.setAuditDesc(opinion);
        auditing.setTime(new Date());

        try {
            auditService.audit(auditing);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
