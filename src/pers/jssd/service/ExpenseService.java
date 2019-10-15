package pers.jssd.service;

import org.apache.commons.fileupload.FileItem;
import pers.jssd.entity.ExpImage;
import pers.jssd.entity.Expense;
import pers.jssd.entity.ExpenseItem;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface ExpenseService {


    /**
     * 添加一个expense 信息
     *
     * @param expense 需要添加的报销信息
     * @param photos  上传的文件FileItem对象
     * @param path    存储上传文件的路径
     * @return 成功添加, 返回true, 否则返回false
     */
    boolean add(Expense expense, List<FileItem> photos, String path);

    /**
     * 通过empId查找所有的报销信息
     *
     * @param empId 需要查找的EmpId条件
     * @return 返回一个存储所有Expense报销信息的list
     */
    List<Expense> findExpenseByEmpId(String empId);

    /**
     * 查询指定报销单的报销明细
     *
     * @param expId 执行的报销单id
     * @return 返回存储所有查询到的报销单明细List, 如果为空, 则查询失败
     */
    List<ExpenseItem> findItemByExpId(int expId);

    /**
     * 查询当前用户待审报销单
     *
     * @param nextAuditor 当前用户id
     * @return 返回所有当前用户待审的报销单
     */
    List<Expense> findExpenseByNextAuditor(String nextAuditor);

    /**
     * 查询所有的指定报销单的报销证明图片
     *
     * @param expId 报销单id
     * @return 返回所有查询到的报销单list容器
     */
    List<ExpImage> findExpImage(int expId);

}
