package pers.jssd.service.impl;

import pers.jssd.dao.DeptDao;
import pers.jssd.dao.impl.DeptDaoImpl;
import pers.jssd.entity.Dept;
import pers.jssd.service.DeptService;

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
}
