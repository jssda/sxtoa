package pers.jssd.service.impl;

import pers.jssd.dao.DutyDao;
import pers.jssd.dao.impl.DutyDaoImpl;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Duty;
import pers.jssd.service.DutyService;
import pers.jssd.util.PageBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DutyServiceImpl implements DutyService {

    private DutyDao dutyDao = new DutyDaoImpl();

    @Override
    public int signIn(Duty duty) {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int n = 0;

        duty.setDtDate(date);
        duty.setSignInTime(format.format(date));
        if (dutyDao.getDuty(duty.getEmpId(), duty.getDtDate())) {
            n = 2;
        } else {
            n = dutyDao.insertDuty(duty);
        }

        return n;
    }

    @Override
    public int signOut(Duty duty) {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int n = 0;

        duty.setDtDate(date);
        duty.setSignOutTime(format.format(date));
        if (!dutyDao.getDuty(duty.getEmpId(), duty.getDtDate())) {
            n = 2;
        } else {
            n = dutyDao.updateDuty(duty);
        }

        return n;
    }

    @Override
    public List<Duty> findDuties(String empId, int deptNo, String sDtDate) {
        return dutyDao.listDutiesBy(empId, deptNo, sDtDate);
    }

    @Override
    public List<Duty> findDuties(String empId) {
        return dutyDao.listDuties(empId);
    }

    @Override
    public void findDuties(String empId, int deptNo, String sDtDate, PageBean<Duty> pageBean) {
        // 设置每页显示的数据量
        pageBean.setSize(13);
        // 设置默认的显示页码数组长度
        pageBean.setDefaultNumberLength(5);

        int sum = dutyDao.getDutySum(empId, deptNo, sDtDate);
        // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
        pageBean.setTotalCount(sum);

        int startRow = pageBean.getStartRow();
        int endRow = pageBean.getEndRow();

        List<Duty> list = dutyDao.listDutiesBy(empId, deptNo, sDtDate, startRow, endRow);
        pageBean.setList(list);
    }

    @Override
    public void findDuties(String empId, PageBean<Duty> pageBean) {
        // 设置每页显示的数据量
        pageBean.setSize(13);
        // 设置默认的显示页码数组长度
        pageBean.setDefaultNumberLength(5);

        int sum = dutyDao.getDutySum(empId, 0, null);
        // 设置总页数, 设置总页数的同时, 会自动计算出一共有多少页,
        pageBean.setTotalCount(sum);

        int startRow = pageBean.getStartRow();
        int endRow = pageBean.getEndRow();

        List<Duty> list = dutyDao.listDuties(empId, startRow, endRow);
        pageBean.setList(list);
    }
}
