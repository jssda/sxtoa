package pers.jssd.servlet;

import pers.jssd.entity.Dept;
import pers.jssd.entity.Position;
import pers.jssd.service.PositionService;
import pers.jssd.service.impl.PositionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class PositionServlet extends BaseServlet {

    private PositionService positionService = new PositionServiceImpl();

    // 查询所有职业
    public void findPositions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Position> positions = positionService.findPositions();
        req.setAttribute("positions", positions);
        req.getRequestDispatcher("/system/positionList.jsp").forward(req, resp);
    }

    // 添加一个职位信息
    public void addPosition(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String posId = req.getParameter("posId");
        String pName = req.getParameter("pName");
        String pDesc = req.getParameter("pDesc");

        Position position = new Position(Integer.parseInt(posId), pName, pDesc);
        int i = positionService.addPosition(position);
        if (i > 0) {
            resp.sendRedirect(req.getContextPath() + "/servlet/positionServlet?method=findPositions");
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/positionAdd.jsp").forward(req, resp);
        }
    }

    // 删除根据id一个position数据
    public void removePosition(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String posId = req.getParameter("posId");
        int i = positionService.removePosition(Integer.parseInt(posId));
        if (i > 0) {
            resp.sendRedirect(req.getContextPath() + "/servlet/positionServlet?method=findPositions");
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/positionList.jsp").forward(req, resp);
        }
    }

    // 通过posId 查找一条职位信息
    public void findPositionById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String posId = req.getParameter("posId");

        if (posId == null || posId.trim().equals("")) {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/positionServlet?method=findPositions").forward(req, resp);
            return;
        }

        Position position = positionService.findPositionById(Integer.parseInt(posId));
        if (position != null) {
            position.setPosId(Integer.parseInt(posId));
            req.setAttribute("position", position);
            req.getRequestDispatcher("/system/positionUpdate.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/servlet/positionServlet?method=findPositions").forward(req, resp);
        }
    }

    public void updatePosition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取客户端传递的参数
        String posId = req.getParameter("posId");
        String pName = req.getParameter("pName");
        String pDesc = req.getParameter("pDesc");

        Position position = new Position(Integer.parseInt(posId), pName, pDesc);

        int i = positionService.updatePosition(position);
        if (i == 0) { // 修改失败
            req.setAttribute("error", true);
            req.getRequestDispatcher("/system/positionUpdate.jsp").forward(req, resp);
        } else { // 修改成功
            resp.sendRedirect(req.getContextPath() + "/servlet/positionServlet?method=findPositions");
        }
    }
}