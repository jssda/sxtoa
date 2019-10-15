package pers.jssd.dao;

import pers.jssd.entity.Position;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface PositionDao {

    /**
     * 从数据库中查询所有的职位信息
     *
     * @return 返回存储所有职位信息的list容器
     */
    List<Position> getPositions();

    /**
     * 将新的职位信息添加到数据库
     *
     * @param position 新的职位信息
     * @return 返回添加的条数
     */
    int insertPosition(Position position);

    /**
     * 根据positionId, 从数据库中删除一条position数据
     *
     * @param posId 需要删除的positionId
     * @return 返回删除的条数
     */
    int delPosition(int posId);

    /**
     * 从数据库中查找一个position信息, 通过id
     * @param posId 查找的position的id
     * @return 返回一条position对象数据, 如果在数据库中没找到, 则返回一个null
     */
    Position getPositionById(int posId);

    /**
     * 更新数据库中的职位信息
     * @param position 需要更新的职位信息
     * @return 返回更新的条数
     */
    int updatePosition(Position position);
}
