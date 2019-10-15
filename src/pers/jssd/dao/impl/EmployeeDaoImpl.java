package pers.jssd.dao.impl;

import pers.jssd.dao.EmployeeDao;
import pers.jssd.entity.Dept;
import pers.jssd.entity.Employee;
import pers.jssd.entity.Position;
import pers.jssd.util.DBUtil;
import pers.jssd.util.DBUtil2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public int insertEmployee(Employee employee) {
        // 如果插入时候,没有密码, 则插入一个初始密码(123456)
        if (employee.getPassword() == null || "".equals(employee.getPassword().trim())) {
            // 设置初始密码
            employee.setPassword("123456");
        }

        String sql = "insert into EMPLOYEE(empId, password, deptno, posId, mgrId, realName, sex, birthDate, hireDate, leaveDate, " +
                "onDuty, empType, phone, qq, emerContactPerson, idCard) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // 处理如果时间类型, 如果传递过来的时间是空的话, 插入一个null
        Date sqlLeaveDate = null;
        if (employee.getLeaveDate() != null) {
            sqlLeaveDate = new Date(employee.getLeaveDate().getTime());
        }

        Date sqlBirthDate = null;
        if (employee.getBirthDate() != null) {
            sqlBirthDate = new java.sql.Date(employee.getBirthDate().getTime());
        }

        Date sqlHireDate = null;
        if (employee.getBirthDate() != null) {
            sqlHireDate = new java.sql.Date(employee.getHireDate().getTime());
        }

        // 如果没有上级, 插入一个空
        Employee mgr = employee.getMgr();
        String mgrId = null;
        if (mgr != null) {
            mgrId = mgr.getEmpId();
            if (mgrId == null || "null".equals(mgrId) || "".equals(mgrId.trim())) {
                mgrId = null;
            }
        }

        // 处理参数
        Object[] params = new Object[]{
                employee.getEmpId(),
                employee.getPassword(),
                employee.getDept().getDeptNo(),
                employee.getPosition().getPosId(),
                mgrId,
                employee.getRealName(),
                employee.getSex(),
                sqlBirthDate,
                sqlHireDate,
                sqlLeaveDate,
                employee.getOnDuty(),
                employee.getEmpType(),
                employee.getPhone(),
                employee.getQq(),
                employee.getEmerContactPerson(),
                employee.getIdCard()
        };

        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Employee> listEmployees() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();

        try {

            String sql = "select e.*, d.deptName, p.pName, m.realName mgrName" +
                    " from employee e" +
                    " join dept d" +
                    " on e.deptno = d.deptno" +
                    " join position p" +
                    " on e.posId = p.posId" +
                    " left join employee m" +
                    " on e.mgrId = m.empId";

            //String sql = "select * from EMPLOYEE";

            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();

                employee.setEmpId(rs.getString("empId"));
                employee.setPassword(rs.getString("password"));

                // 添加部门
                Dept dept = new Dept(rs.getInt("deptNo"), rs.getString("deptName"), null);
                employee.setDept(dept);

                // 添加职位
                Position position = new Position(rs.getInt("posId"), rs.getString("pName"), null);
                employee.setPosition(position);

                // 添加领导
                Employee mgr = new Employee(rs.getString("mgrId"), rs.getString("mgrName"));
                employee.setMgr(mgr);

                employee.setRealName(rs.getString("realName"));
                employee.setSex(rs.getString("sex"));
                employee.setBirthDate(rs.getDate("birthDate"));
                employee.setHireDate(rs.getDate("hireDate"));
                employee.setLeaveDate(rs.getDate("leaveDate"));
                employee.setOnDuty(rs.getInt("onDuty"));
                employee.setEmpType(rs.getInt("empType"));
                employee.setPhone(rs.getString("phone"));
                employee.setQq(rs.getString("QQ"));
                employee.setEmerContactPerson(rs.getString("emerContactPerson"));
                employee.setIdCard(rs.getString("idCard"));

                employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return employees;
    }

    @Override
    public List<Employee> listEmployeesBy(Employee employee) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("select e.*, d.deptName, p.pName, m.realName mgrName" +
                    " from employee e" +
                    " join dept d" +
                    " on e.deptno = d.deptno" +
                    " join position p" +
                    " on e.posId = p.posId" +
                    " left join employee m" +
                    " on e.mgrId = m.empId where 1 = 1");

            // 添加用户id条件
            String empId = employee.getEmpId();
            if (empId != null && !"".equals(empId.trim())) {
                sql.append(" and e.empId = '").append(empId).append("'");
            }

            // 添加用户密码条件
            String password = employee.getPassword();
            if (password != null && !"".equals(password.trim())) {
                sql.append(" and e.password='").append(password).append("'");
            }

            // 添加部门号条件
            Dept dept = employee.getDept();
            int deptNo = -1;
            if (dept != null) {
                deptNo = dept.getDeptNo();
            }
            if (deptNo != -1) {
                sql.append(" and e.deptNo = '").append(deptNo).append("'");
            }

            // 添加职位条件
            Position position = employee.getPosition();
            int posId = -1;
            if (position != null) {
                posId = position.getPosId();
            }
            if (posId != -1) {
                sql.append(" and e.posId = '").append(posId).append("'");
            }

            // 添加上级条件
            Employee mgr = employee.getMgr();
            String mgrId = null;
            if (mgr != null) {
                mgrId = mgr.getEmpId();
            }
            if (mgrId != null && !"".equals(mgrId.trim())) {
                sql.append(" and e.mgrId=").append(mgrId);
            }

            String realName = employee.getRealName();
            if (realName != null && !"".equals(realName.trim())) {
                sql.append(" and e.realName like '%").append(realName).append("%'");
            }

            String sex = employee.getSex();
            if (sex != null && !"".equals(sex.trim())) {
                sql.append(" and e.sex='").append(sex).append("'");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 指定日期之后
            java.util.Date birthDate = employee.getBirthDate();
            if (birthDate != null) {
                String format = dateFormat.format(birthDate);
                sql.append(" and to_char(e.birthDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            java.util.Date hireDate = employee.getHireDate();
            if (hireDate != null) {
                String format = dateFormat.format(hireDate);
                sql.append(" and to_char(e.hireDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            int onDuty = employee.getOnDuty();
            if (onDuty != 3) {
                sql.append(" and e.onDuty = ").append(onDuty);
            }

            int empType = employee.getEmpType();
            if (empType != 0) {
                sql.append(" and e.empType = ").append(empType);
            }

            //System.out.println("sql = " + sql);

            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql.toString());
            rs = statement.executeQuery();

            while (rs.next()) {
                Employee temp = new Employee();

                temp.setEmpId(rs.getString("empId"));
                temp.setPassword(rs.getString("password"));

                // 添加部门
                Dept d = new Dept(rs.getInt("deptNo"), rs.getString("deptName"), null);
                temp.setDept(d);

                // 添加职位
                Position pos = new Position(rs.getInt("posId"), rs.getString("pName"), null);
                temp.setPosition(pos);

                // 添加领导
                Employee m = new Employee(rs.getString("mgrId"), rs.getString("mgrName"));
                temp.setMgr(m);

                temp.setRealName(rs.getString("realName"));
                temp.setSex(rs.getString("sex"));
                temp.setBirthDate(rs.getDate("birthDate"));
                temp.setHireDate(rs.getDate("hireDate"));
                temp.setLeaveDate(rs.getDate("leaveDate"));
                temp.setOnDuty(rs.getInt("onDuty"));
                temp.setEmpType(rs.getInt("empType"));
                temp.setPhone(rs.getString("phone"));
                temp.setQq(rs.getString("QQ"));
                temp.setEmerContactPerson(rs.getString("emerContactPerson"));
                temp.setIdCard(rs.getString("idCard"));

                employees.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return employees;
    }

    @Override
    public int updateEmployee(Employee employee) {
        String sql = "update EMPLOYEE set deptNo=?, posId=?, mgrId=?, realName=?, sex=?, birthDate=?, hireDate=?, " +
                "leaveDate=?, onDuty=?, empType=?, phone=?, qq=?, emerContactPerson=?, idCard=? where empId=?";

        // 处理如果时间类型, 如果传递过来的时间是空的话, 插入一个null
        Date sqlLeaveDate = null;
        if (employee.getLeaveDate() != null) {
            sqlLeaveDate = new Date(employee.getLeaveDate().getTime());
        }

        Date sqlBirthDate = null;
        if (employee.getBirthDate() != null) {
            sqlBirthDate = new java.sql.Date(employee.getBirthDate().getTime());
        }

        Date sqlHireDate = null;
        if (employee.getBirthDate() != null) {
            sqlHireDate = new java.sql.Date(employee.getHireDate().getTime());
        }

        // 如果没有上级, 插入一个空
        Employee mgr = employee.getMgr();
        String mgrId = null;
        if (mgr != null) {
            mgrId = mgr.getEmpId();
            if (mgrId == null || "null".equals(mgrId) || "".equals(mgrId.trim())) {
                mgrId = null;
            }
        }

        // 处理参数
        Object[] params = new Object[]{
                employee.getDept().getDeptNo(),
                employee.getPosition().getPosId(),
                mgrId,
                employee.getRealName(),
                employee.getSex(),
                sqlBirthDate,
                sqlHireDate,
                sqlLeaveDate,
                employee.getOnDuty(),
                employee.getEmpType(),
                employee.getPhone(),
                employee.getQq(),
                employee.getEmerContactPerson(),
                employee.getIdCard(),
                employee.getEmpId(),
        };

        System.out.println("sql = " + sql);
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteEmployee(String empId) {

        String sql = "delete employee where empId = ?";
        Object[] params = new Object[]{empId};
        return DBUtil.executeUpdate(sql, params);

    }

    @Override
    public int updatePwd(String empId, String newPwd) {
        String sql = "update employee set password=? where empId = ?";
        Object[] params = new Object[]{newPwd, empId};

        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Employee> listEmployeesBy(Employee employee, int startRow, int endRow) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("select *\n" +
                    "from (select ROWNUM rn, temp.*\n" +
                    "      from (select e.*, d.deptName, p.pName, m.realName mgrName\n" +
                    "            from employee e\n" +
                    "                     join dept d\n" +
                    "                          on e.deptno = d.deptno\n" +
                    "                     join position p\n" +
                    "                          on e.posId = p.posId\n" +
                    "                     left join employee m\n" +
                    "                               on e.mgrId = m.empId\n" +
                    "            where 1 = 1 ");

            // 添加用户id条件
            String empId = employee.getEmpId();
            if (empId != null && !"".equals(empId.trim())) {
                sql.append(" and e.empId = '").append(empId).append("'");
            }

            // 添加用户密码条件
            String password = employee.getPassword();
            if (password != null && !"".equals(password.trim())) {
                sql.append(" and e.password='").append(password).append("'");
            }

            // 添加部门号条件
            Dept dept = employee.getDept();
            int deptNo = -1;
            if (dept != null) {
                deptNo = dept.getDeptNo();
            }
            if (deptNo != -1) {
                sql.append(" and e.deptNo = '").append(deptNo).append("'");
            }

            // 添加职位条件
            Position position = employee.getPosition();
            int posId = -1;
            if (position != null) {
                posId = position.getPosId();
            }
            if (posId != -1) {
                sql.append(" and e.posId = '").append(posId).append("'");
            }

            // 添加上级条件
            Employee mgr = employee.getMgr();
            String mgrId = null;
            if (mgr != null) {
                mgrId = mgr.getEmpId();
            }
            if (mgrId != null && !"".equals(mgrId.trim())) {
                sql.append(" and e.mgrId=").append(mgrId);
            }

            String realName = employee.getRealName();
            if (realName != null && !"".equals(realName.trim())) {
                sql.append(" and e.realName like '%").append(realName).append("%'");
            }

            String sex = employee.getSex();
            if (sex != null && !"".equals(sex.trim())) {
                sql.append(" and e.sex='").append(sex).append("'");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 指定日期之后
            java.util.Date birthDate = employee.getBirthDate();
            if (birthDate != null) {
                String format = dateFormat.format(birthDate);
                sql.append(" and to_char(e.birthDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            java.util.Date hireDate = employee.getHireDate();
            if (hireDate != null) {
                String format = dateFormat.format(hireDate);
                sql.append(" and to_char(e.hireDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            int onDuty = employee.getOnDuty();
            if (onDuty != 3) {
                sql.append(" and e.onDuty = ").append(onDuty);
            }

            int empType = employee.getEmpType();
            if (empType != 0) {
                sql.append(" and e.empType = ").append(empType);
            }

            // 添加分页条件
            sql.append(" order by e.EMPID) temp\n" +
                    "      where ROWNUM <= ?)\n" +
                    "where rn > ?");

            //System.out.println("sql = " + sql);

            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql.toString());
            statement.setInt(1, endRow);
            statement.setInt(2, startRow);

            rs = statement.executeQuery();

            while (rs.next()) {
                Employee temp = new Employee();

                temp.setEmpId(rs.getString("empId"));
                temp.setPassword(rs.getString("password"));

                // 添加部门
                Dept d = new Dept(rs.getInt("deptNo"), rs.getString("deptName"), null);
                temp.setDept(d);

                // 添加职位
                Position pos = new Position(rs.getInt("posId"), rs.getString("pName"), null);
                temp.setPosition(pos);

                // 添加领导
                Employee m = new Employee(rs.getString("mgrId"), rs.getString("mgrName"));
                temp.setMgr(m);

                temp.setRealName(rs.getString("realName"));
                temp.setSex(rs.getString("sex"));
                temp.setBirthDate(rs.getDate("birthDate"));
                temp.setHireDate(rs.getDate("hireDate"));
                temp.setLeaveDate(rs.getDate("leaveDate"));
                temp.setOnDuty(rs.getInt("onDuty"));
                temp.setEmpType(rs.getInt("empType"));
                temp.setPhone(rs.getString("phone"));
                temp.setQq(rs.getString("QQ"));
                temp.setEmerContactPerson(rs.getString("emerContactPerson"));
                temp.setIdCard(rs.getString("idCard"));

                employees.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, statement, connection);
        }

        return employees;
    }

    @Override
    public int getSumBy(Employee employee) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int sum = 0;

        try {
            conn = DBUtil2.getConnection();
            StringBuilder sql = new StringBuilder("select count(*)\n" +
                    "            from employee e\n" +
                    "                     join dept d\n" +
                    "                          on e.deptno = d.deptno\n" +
                    "                     join position p\n" +
                    "                          on e.posId = p.posId\n" +
                    "                     left join employee m\n" +
                    "                               on e.mgrId = m.empId\n" +
                    "            where 1 = 1 ");

            // 添加用户id条件
            String empId = employee.getEmpId();
            if (empId != null && !"".equals(empId.trim())) {
                sql.append(" and e.empId = '").append(empId).append("'");
            }

            // 添加用户密码条件
            String password = employee.getPassword();
            if (password != null && !"".equals(password.trim())) {
                sql.append(" and e.password='").append(password).append("'");
            }

            // 添加部门号条件
            Dept dept = employee.getDept();
            int deptNo = -1;
            if (dept != null) {
                deptNo = dept.getDeptNo();
            }
            if (deptNo != -1) {
                sql.append(" and e.deptNo = '").append(deptNo).append("'");
            }

            // 添加职位条件
            Position position = employee.getPosition();
            int posId = -1;
            if (position != null) {
                posId = position.getPosId();
            }
            if (posId != -1) {
                sql.append(" and e.posId = '").append(posId).append("'");
            }

            // 添加上级条件
            Employee mgr = employee.getMgr();
            String mgrId = null;
            if (mgr != null) {
                mgrId = mgr.getEmpId();
            }
            if (mgrId != null && !"".equals(mgrId.trim())) {
                sql.append(" and e.mgrId=").append(mgrId);
            }

            String realName = employee.getRealName();
            if (realName != null && !"".equals(realName.trim())) {
                sql.append(" and e.realName like '%").append(realName).append("%'");
            }

            String sex = employee.getSex();
            if (sex != null && !"".equals(sex.trim())) {
                sql.append(" and e.sex='").append(sex).append("'");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 指定日期之后
            java.util.Date birthDate = employee.getBirthDate();
            if (birthDate != null) {
                String format = dateFormat.format(birthDate);
                sql.append(" and to_char(e.birthDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            java.util.Date hireDate = employee.getHireDate();
            if (hireDate != null) {
                String format = dateFormat.format(hireDate);
                sql.append(" and to_char(e.hireDate, 'yyyy-mm-dd') >= '").append(format).append("'");
            }

            int onDuty = employee.getOnDuty();
            if (onDuty != 3) {
                sql.append(" and e.onDuty = ").append(onDuty);
            }

            int empType = employee.getEmpType();
            if (empType != 0) {
                sql.append(" and e.empType = ").append(empType);
            }

            // 添加完条件, 查询出一共有多少条记录
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            rs.next();
            sum = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(rs, ps, conn);
        }
        return sum;
    }
}
