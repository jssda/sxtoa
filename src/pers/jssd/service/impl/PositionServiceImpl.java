package pers.jssd.service.impl;

import pers.jssd.dao.PositionDao;
import pers.jssd.dao.impl.PositionDaoImpl;
import pers.jssd.entity.Position;
import pers.jssd.service.PositionService;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class PositionServiceImpl implements PositionService {

    private PositionDao positionDao = new PositionDaoImpl();

    @Override
    public List<Position> findPositions() {
        return positionDao.getPositions();
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
}
