package pers.jssd.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Duty;
import pers.jssd.entity.Employee;
import pers.jssd.service.DeptService;
import pers.jssd.service.DutyService;
import pers.jssd.service.impl.DeptServiceImpl;
import pers.jssd.service.impl.DutyServiceImpl;
import pers.jssd.util.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DutyServlet extends BaseServlet {

    private DutyService dutyService = new DutyServiceImpl();
    private DeptService deptService = new DeptServiceImpl();

    /**
     * 显示我的考勤
     *
     * @param req 请求
     * @param resp 响应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    public void toMyDuty(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 取得当前页
        PageBean<Duty> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        pageBean.setIndex(index);

        Employee employee = (Employee) req.getSession().getAttribute("currUser");
        String empId = employee.getEmpId();

        dutyService.findDuties(empId, pageBean);

        req.setAttribute("pageBean", pageBean);
        req.getRequestDispatcher("/duty/myDuty.jsp").forward(req, resp);
    }

    /**
     * 签到
     *
     * @param req 请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void signIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee currUser = (Employee) req.getSession().getAttribute("currUser");
        String empId = currUser.getEmpId();

        Duty duty = new Duty();
        duty.setEmpId(empId);

        PrintWriter writer = resp.getWriter();

        int n = dutyService.signIn(duty);
        if (n == 0) {
            writer.println("签到失败");
        } else if (n == 1) {
            writer.println("签到成功");
        } else {
            writer.println("不要重复签到");
        }
    }

    /**
     * 签退
     *
     * @param req 请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void signOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee currUser = (Employee) req.getSession().getAttribute("currUser");
        String empId = currUser.getEmpId();

        Duty duty = new Duty();
        duty.setEmpId(empId);

        PrintWriter writer = resp.getWriter();

        int n = dutyService.signOut(duty);
        if (n == 0) {
            writer.println("签退失败");
        } else if (n == 1) {
            writer.println("签退成功");
        } else {
            System.out.println("n = " + n);
            writer.println("没有签到");
        }
    }

    /**
     * 管理duty页面加载所有的部门
     *
     * @param req 请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void findAllDept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Dept> depts = deptService.findDepts();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        resp.getWriter().println(gson.toJson(depts));
    }

    /**
     * 根据条件查询考勤信息
     *
     * @param req 请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void findDuty(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PageBean<Duty> pageBean = new PageBean<>();
        String sIndex = req.getParameter("index");
        int index = 1;

        try {
            index = Integer.parseInt(sIndex);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        pageBean.setIndex(index);

        String strEmpId = req.getParameter("empId");
        String strDeptNo = req.getParameter("deptNo");
        int deptNo = 0;

        try {
            deptNo = Integer.parseInt(strDeptNo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String sDtDate = req.getParameter("dtDate");

        dutyService.findDuties(strEmpId, deptNo, sDtDate, pageBean);
        PrintWriter writer = resp.getWriter();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        Gson gson = gsonBuilder.create();

        String json = gson.toJson(pageBean);
        writer.println(json);
    }

    /**
     * 导出一个excel表格
     *
     * @param req 请求
     * @param resp 响应
     * @throws IOException IO异常
     */
    public void export(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strEmpId = req.getParameter("empId");
        String strDeptNo = req.getParameter("deptNo");
        int deptNo = 0;

        try {
            deptNo = Integer.parseInt(strDeptNo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String sDtDate = req.getParameter("dtDate");
        List<Duty> duties = dutyService.findDuties(strEmpId, deptNo, sDtDate);

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-disposition", "attachment;filename=duty.xls");//附件形式下载，文件名叫duty.xls
        //OutputStream outputStream = new FileOutputStream("D:/duty.xls");//保存到本地（服务器端）

        createExcel(duties, resp.getOutputStream());
    }

    /**
     * 创建Excel
     *
     * @param list 数据
     */
    private static void createExcel(List<Duty> list, OutputStream os) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("考勤表一");

        CellRangeAddress region = new CellRangeAddress(0, // first row
                0, // last row
                0, // first column
                2 // last column
        );
        sheet.addMergedRegion(region);

        HSSFRow hssfRow = sheet.createRow(0);
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("考勤列表");

        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headCell.setCellStyle(cellStyle);

        // 添加表头行
        hssfRow = sheet.createRow(1);

        // 添加表头内容
        headCell = hssfRow.createCell(0);
        headCell.setCellValue("用户名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("真实姓名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("所属部门");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(3);
        headCell.setCellValue("出勤日期");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(4);
        headCell.setCellValue("签到时间");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(5);
        headCell.setCellValue("签退时间");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 2);
            Duty duty = list.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(duty.getEmployee().getEmpId());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(duty.getEmployee().getRealName());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(duty.getEmployee().getDept().getDeptName());
            cell.setCellStyle(cellStyle);

            hssfRow.createCell(3);
            cell.setCellValue(duty.getDtDate());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(4);
            cell.setCellValue(duty.getSignInTime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(5);
            cell.setCellValue(duty.getSignOutTime());
            cell.setCellStyle(cellStyle);
        }

        // 保存Excel文件
        try {
            //OutputStream outputStream = new FileOutputStream("D:/students.xls");
            workbook.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
