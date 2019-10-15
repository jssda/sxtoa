package pers.jssd.test;

import org.junit.Test;
import pers.jssd.entity.Dept;
import pers.jssd.service.DeptService;
import pers.jssd.service.impl.DeptServiceImpl;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public class DeptTest {

    private DeptService deptService = new DeptServiceImpl();

    @Test
    public void addDeptTest() {
        Dept newDept = new Dept(4, "研发部", "1");
        int i = deptService.addDept(newDept);
        System.out.println("i = " + i);
    }

    @Test
    public void findDeptTest() {
        List<Dept> dept = deptService.findDepts();
        System.out.println(dept);
    }

    @Test
    public void removeDeptTest() {
        int deptNo = 0;
        int i = deptService.removeDept(deptNo);
        if (i == 1) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }


    @Test
    public void getDeptByIdTest() {
        int deptNo = 5;
        Dept deptById = deptService.findDeptById(deptNo);
        System.out.println("deptById = " + deptById);
    }

    @Test
    public void updateTest() {
        Dept dept = new Dept(4, "教研部", "335");
        int i = deptService.updateDept(dept);
        if (i == 0) {
            System.out.println("修改失败");
        } else {
            System.out.println("修改成功");
        }
    }
}
