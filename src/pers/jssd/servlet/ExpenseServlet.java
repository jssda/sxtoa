package pers.jssd.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import pers.jssd.entity.*;
import pers.jssd.service.ExpenseService;
import pers.jssd.service.impl.ExpenseServiceImpl;
import pers.jssd.util.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author jssdjing@gmail.com
 */
public class ExpenseServlet extends BaseServlet {

    private ExpenseService expenseService = new ExpenseServiceImpl();

    /**
     * 添加一个报销单
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet已成
     * @throws IOException      IO异常
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 取得session中的报销人员
        HttpSession session = req.getSession();
        Employee currUser = (Employee) session.getAttribute("currUser");
        Expense expense = new Expense();

        // 取得报销人
        expense.setEmpId(currUser.getEmpId());
        expense.setEmployee(currUser);

        // 取得下一个审批人的id
        expense.setNextAuditor(currUser.getMgr().getEmpId());

        // 取得报销时间
        expense.setExpTime(new Date());

        // 上传图片处理
        try {
            // 取得所有报销明细
            List<ExpenseItem> items = new ArrayList<>();

            List<String> expItems = new ArrayList<>();
            List<String> amounts = new ArrayList<>();
            List<String> itemDescs = new ArrayList<>();
            List<FileItem> photos = new ArrayList<>();

            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            List<FileItem> fileItems = servletFileUpload.parseRequest(req);
            servletFileUpload.setHeaderEncoding("utf-8"); // 解决表单头部乱码问题
            //servletFileUpload.setFileSizeMax(16*1024); // 单个文件大小不能超过16kb
            //servletFileUpload.setSizeMax(16*1024*5); // 所有上传的数据不能超过一定的文件大小

            for (FileItem fileItem : fileItems) {

                // 处理各个格式
                switch (fileItem.getFieldName()) {
                    case "totalAmount":
                        // 取得报销金额
                        try {
                            expense.setTotalAmount(Integer.parseInt(fileItem.getString()));
                        } catch (NumberFormatException e) {
                            req.setAttribute("error", "报销金额格式错误");
                            req.getRequestDispatcher("/expense/expenseAdd.jsp").forward(req, resp);
                            return;
                        }
                        break;
                    case "expDesc":
                        // 取得报销说明
                        expense.setExpDesc(fileItem.getString("utf-8"));
                        break;
                    case "expItem":
                        expItems.add(fileItem.getString());
                        break;
                    case "amount":
                        amounts.add(fileItem.getString());
                        break;
                    case "itemDesc":
                        itemDescs.add(fileItem.getString("utf-8"));
                        break;
                    case "photo": // 如果是上传文件的话
                        photos.add(fileItem);
                        break;
                    default:
                        System.out.println(fileItem.getFieldName());
                        break;
                }
            }

            // 处理报销项
            for (int i = 0; i < expItems.size(); i++) {
                ExpenseItem item = new ExpenseItem();
                item.setType(expItems.get(i));

                try {
                    item.setAmount(Integer.parseInt(amounts.get(i)));
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "报销明细金额格式错误");
                    req.getRequestDispatcher("/expense/expenseAdd.jsp").forward(req, resp);
                    return;
                }

                item.setItemDesc(itemDescs.get(i));
                items.add(item);
            }
            expense.setExpenseItems(items);

            // 取得上传路径
            String path = req.getServletContext().getRealPath("/upload/");

            // 处理业务
            boolean flag = expenseService.add(expense, photos, path);

            // 响应客户端请求
            if (!flag) {
                req.setAttribute("error", "报销失败");
                req.getRequestDispatcher("/expense/expenseAdd.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/servlet/expenseServlet?method=toMyExpense");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.toString());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    /**
     * 显示审核的时候的图片
     *
     * @param req  请求
     * @param resp 响应
     */
    public void toExpenseImg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sExpId = req.getParameter("expId");

        int expId = 0;
        try {
            expId = Integer.parseInt(sExpId);
        } catch (NumberFormatException e) {
            req.setAttribute("error", e.toString());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        List<ExpImage> expImages = expenseService.findExpImage(expId);
        req.setAttribute("expImages", expImages);
        req.getRequestDispatcher("/expense/expenseImg.jsp").forward(req, resp);
    }

    /**
     * 展示我自己的报销信息到浏览器页面
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException 产生的servlet异常
     * @throws IOException      产生的IO异常
     */
    public void toMyExpense(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 取得当前页
        PageBean<Expense> pageBean = new PageBean<>();
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

        expenseService.findExpenseByEmpId(currUser.getEmpId(), pageBean);

        req.setAttribute("pageBean", pageBean);
        req.getRequestDispatcher("/expense/myExpense.jsp").forward(req, resp);
    }

    /**
     * 展示所有的报销明细项
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    public void toExpenseDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int expId = Integer.parseInt(req.getParameter("expId"));

        List<ExpenseItem> expenseitemList = expenseService.findItemByExpId(expId);

        if (expenseitemList == null) {
            req.setAttribute("error", "出错了");
            req.getRequestDispatcher("/expense/myExpense.jsp").forward(req, resp);
        } else {
            req.setAttribute("expenseItemList", expenseitemList);
            req.getRequestDispatcher("/expense/expenseDetail.jsp").forward(req, resp);
        }

    }

    /**
     * 显示待审页面
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException servlet异常
     * @throws IOException      IO异常
     */
    public void showToAudit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee currUser = (Employee) req.getSession().getAttribute("currUser");
        // 取得当前用户id, 当前用户需要审核报销单
        String nextAuditor = currUser.getEmpId();

        List<Expense> expenseList = expenseService.findExpenseByNextAuditor(nextAuditor);

        if (expenseList == null) {
            resp.getWriter().println("出错了!!");
        } else {
            req.setAttribute("expenseList", expenseList);
            req.getRequestDispatcher("/expense/toAudit.jsp").forward(req, resp);
        }
    }
}
