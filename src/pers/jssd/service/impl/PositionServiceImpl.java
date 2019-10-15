package pers.jssd.service.impl;

import pers.jssd.dao.PositionDao;
import pers.jssd.dao.impl.PositionDaoImpl;
import pers.jssd.entity.Position;
import pers.jssd.service.PositionService;
import pers.jssd.util.PageBean;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class PositionServiceImpl implements PositionService {

    private PositionDao positionDao = new PositionDaoImpl();

    @Override
    public List<Position> findPositions() {
        return positionDao.listPositions();
    }

    @Override
    public int addPosition(Position position) {
        return positionDao.insertPosition(position);
    }

    @Override
    public int removePosition(int posId) {
        return positionDao.delPosition(posId);
    }

    @Override
    public Position findPositionById(int posId) {
        return positionDao.getPositionById(posId);
    }

    @Override
    public int updatePosition(Position position) {
        return positionDao.updatePosition(position);
    }

    @Override
    public void findPositions(PageBean<Position> pageBean) {
        // 设置每页显示的数据量
        pageBean.setSize(13);
        // 设置默认的显示页码数组长度
        pageBean.setDefaultNumberLength(5);

        int sum = positionDao.getPositionSum();
        // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
        pageBean.setTotalCount(sum);

        int startRow = pageBean.getStartRow();
        int endRow = pageBean.getEndRow();

        List<Position> list = positionDao.listPositions(startRow, endRow);
        pageBean.setList(list);
    }
}
