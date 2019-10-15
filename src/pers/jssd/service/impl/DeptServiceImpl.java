package pers.jssd.service.impl;

import pers.jssd.dao.DeptDao;
import pers.jssd.dao.impl.DeptDaoImpl;
import pers.jssd.entity.Dept;
import pers.jssd.service.DeptService;
import pers.jssd.util.PageBean;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DeptServiceImpl implements DeptService {

    private DeptDao deptDao = new DeptDaoImpl();

    @Override
    public int addDept(Dept dept) {
        return deptDao.insertDept(dept);
    }

    @Override
    public List<Dept> findDepts() {
        return deptDao.listDepts();
    }

    @Override
    public int removeDept(int deptNo) {
        return deptDao.delDept(deptNo);
    }

    @Override
    public Dept findDeptById(int deptNo) {
        return deptDao.getDeptById(deptNo);
    }

    @Override
    public int updateDept(Dept dept) {
        return deptDao.updateDept(dept);
    }

    @Override
    public void findDepts(PageBean<Dept> pageBean) {

        // 设置每页显示的数据量
        pageBean.setSize(13);
        // 设置默认的显示页码数组长度
        pageBean.setDefaultNumberLength(5);

        int sum = deptDao.getDeptSum();
        // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
        pageBean.setTotalCount(sum);

        int startRow = pageBean.getStartRow();
        int endRow = pageBean.getEndRow();

        List<Dept> list = deptDao.listDepts(startRow, endRow);
        pageBean.setList(list);
    }
}
