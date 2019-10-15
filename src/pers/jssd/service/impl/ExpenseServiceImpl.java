package pers.jssd.service.impl;

import org.apache.commons.fileupload.FileItem;
import pers.jssd.dao.ExpImageDao;
import pers.jssd.dao.ExpenseDao;
import pers.jssd.dao.ExpenseItemDao;
import pers.jssd.dao.impl.ExpImageDaoImpl;
import pers.jssd.dao.impl.ExpenseDaoImpl;
import pers.jssd.dao.impl.ExpenseItemDaoImpl;
import pers.jssd.entity.ExpImage;
import pers.jssd.entity.Expense;
import pers.jssd.entity.ExpenseItem;
import pers.jssd.service.ExpenseService;
import pers.jssd.util.DBUtil;
import pers.jssd.util.DBUtil2;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jssdjing@gmail.com
 */
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseDao expenseDao = new ExpenseDaoImpl();
    private ExpenseItemDao expenseItemDao = new ExpenseItemDaoImpl();
    private ExpImageDao expImageDao = new ExpImageDaoImpl();

    @Override
    public boolean add(Expense expense, List<FileItem> photos, String path) {

        Connection connection = null;
        boolean flag = false;
        try {
            connection = DBUtil2.getConnection();
            connection.setAutoCommit(false);

            // 取得expense将要添加进去时候的序号
            int nextVal = expenseDao.getNextVal();
            expense.setExpId(nextVal);
            expenseDao.insertExpense(expense);

            List<ExpenseItem> expenseItems = expense.getExpenseItems();
            for (ExpenseItem expenseItem : expenseItems) {
                expenseItem.setExpId(nextVal);
                expenseItemDao.insertExpenseItem(expenseItem);
            }

            for (FileItem photo : photos) {
                ExpImage image = new ExpImage();
                image.setExpId(expense.getExpId());
                image.setRealName(photo.getName());
                image.setFileType(photo.getContentType());
                UUID uuid = UUID.randomUUID();
                String photoName = photo.getName();
                String fileName = uuid + photoName.substring(photoName.lastIndexOf("."));
                image.setFileName(fileName);
                expImageDao.insertExpImage(image);

                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, fileName);
                photo.write(file);
            }

            connection.commit();
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }

        return flag;
    }

    @Override
    public List<Expense> findExpenseByEmpId(String empId) {

        Connection connection = null;
        List<Expense> expenseList = null;

        try {
            connection = DBUtil2.getConnection();
            // 手动提交事务
            connection.setAutoCommit(false);

            // 通过持久层查询expense信息, 包括其中的employee的id, realName, mgrId. 不包括其中的报销详情项
            expenseList = expenseDao.listExpensesByEmpId(empId);

            // 没有已成, 提交
            connection.commit();
        } catch (Exception e) {
            try {
                // 如果有异常, 回滚
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }

        return expenseList;
    }

    @Override
    public List<ExpenseItem> findItemByExpId(int expId) {

        Connection connection = null;
        List<ExpenseItem> expenseItems = null;
        try {
            connection = DBUtil2.getConnection();
            // 手动提交事务
            connection.setAutoCommit(false);

            // 通过持久层查询expenseItem信息
            expenseItems = expenseItemDao.listExpenseItemsByExpId(expId);

            // 没有已成, 提交
            connection.commit();
        } catch (Exception e) {
            try {
                // 如果有异常, 回滚
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }
        return expenseItems;
    }

    @Override
    public List<Expense> findExpenseByNextAuditor(String nextAuditor) {
        Connection connection = null;
        List<Expense> expenseList = null;

        try {
            connection = DBUtil2.getConnection();
            // 手动提交事务
            connection.setAutoCommit(false);

            // 通过持久层查询expense信息, 包括其中的employee的id, realName, mgrId. 不包括其中的报销详情项
            expenseList = expenseDao.listExpensesByNextAudit(nextAuditor);

            // 没有已成, 提交
            connection.commit();
        } catch (Exception e) {
            try {
                // 如果有异常, 回滚
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil2.closeAll(null, null, connection);
        }

        return expenseList;
    }

    @Override
    public List<ExpImage> findExpImage(int expId) {
        Connection conn = DBUtil2.getConnection();
        List<ExpImage> expImages = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            expImages = expImageDao.listExpImages(expId);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }

        return expImages;
    }
}
