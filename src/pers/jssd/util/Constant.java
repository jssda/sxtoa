package pers.jssd.util;

/**
 * @author jssdjing@gmail.com
 */
public class Constant {

    /**
     * 上一次审核结果是拒绝
     */
    public static final String LAST_RESULT_REFUSE = "拒绝";

    /**
     * 上一次审核结果是打回
     */
    public static final String LAST_RESULT_REJECT = "打回";

    /**
     * 上一次审核结果是通过
     */
    public static final String LAST_RESULT_ADOPT = "通过";

    /**
     * 报销单还在审核中
     */
    public static final String  EXPENSE_STATUS_AUDITING = "1";

    /**
     * 报销单结束, 审核完成
     */
    public static final String  EXPENSE_STATUS_ADOPT = "2";

    /**
     * 报销单结束, 审核驳回
     */
    public static final String  EXPENSE_STATUS_REJECT = "3";



    /**
     * 财务职位id
     */
    public static final int FINANCIAL_POSITION_ID = 7;

    /**
     * 财务经理的ID
     */
    public static final String FINANCIAL_EMPLOYEE_ID = "furong";

    /**
     * 总裁职位id
     */
    public static final int CEO_POSITION_ID = 1;




    /**
     * 需要总裁审核的金额临界值
     */
    public static final int MONEY_THRESHOLD = 2500;
}
