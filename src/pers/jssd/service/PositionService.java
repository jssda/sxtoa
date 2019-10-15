package pers.jssd.service;

import pers.jssd.entity.Position;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface PositionService {

    /**
     * 查询所有的Position职位信息
     *
     * @return 返回一个存储所有职位信息的list
     */
    List<Position> findPositions();

    /**
     * 添加一个职位信息
     *
     * @param position 需要添加的职位对象
     * @return 返回添加的条数
     */
    int addPosition(Position position);

    /**
     * 根据职位id, 删除一个职位数据
     *
     * @param posId 需要删除的职位id
     * @return 返回删除了几条数据
     */
    int removePosition(int posId);

    /**
     * 通过部门id查找一个职位
     *
     * @param posId 查找的职位id
     * @return 返回一个职位信息, null则没找到
     */
    Position findPositionById(int posId);

    /**
     * 通过id 更新一个position职位信息
     *
     * @param position 需要更新数据的position
     * @return 返回更新的条数
     */
    int updatePosition(Position position);
}
