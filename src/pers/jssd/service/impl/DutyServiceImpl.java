package pers.jssd.service.impl;

import pers.jssd.dao.DutyDao;
import pers.jssd.dao.impl.DutyDaoImpl;
import pers.jssd.entity.Duty;
import pers.jssd.service.DutyService;

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
}
