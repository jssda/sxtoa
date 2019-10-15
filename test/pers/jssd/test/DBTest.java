package pers.jssd.test;

import org.junit.Ignore;
import org.junit.Test;
import pers.jssd.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库测试类
 *
 * @author jssdjing@gmail.com
 */
public class DBTest {

    /**
     * 添加一个id为3的教学部到DEPT表进行数据库测试
     */
    @Ignore
    @Test
    public void testConnection() {
        String sql = "insert into DEPT(DEPTNO, DEPTNAME, LOCATION) values (?, ?, ?)";
        Object[] objs = new Object[]{3, "教学部", "3"};
        int i = DBUtil.executeUpdate(sql, objs);
        System.out.println("i = " + i);
    }

}
