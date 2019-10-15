package pers.jssd.util;

import java.util.List;

/**
 * 分页工具类
 * 使用方法:
 * <pre>
 *      1. 设置每页的大小, 默认为5
 *      2. 设置当前页index
 *      3. 设置存储显示的页码数组长度, 默认为10个页码
 *      4. 设置所有的记录数(totalCount)
 *      5. 设置所有的记录之后, 会默认根据size计算出所有的页数, 同时, 计算出所有的页数之后, 会自动计算出应该以当前页面显示的页码数组,
 *          存储到number中
 *
 *      Oracle 分页语句示例(需要大于startRow, 小于等于endRow):
 *                  select * from (
*                           select ROWNUM rn, d.*
 *                          from (select * from dept order by DEPTNO) d where ROWNUM <= ?
 *                    ) where rn > ?
 * </pre>
 *
 * @param <T> 本页中存储的记录类型
 * @author jssd
 */
public class PageBean<T> {

    /**
     * 每页显示的记录数, 默认为5条记录
     */
    private int size = 5;

    /**
     * 当前的页号
     */
    private int index = 1;

    /**
     * 所有的记录总数
     */
    private int totalCount = 0;

    /**
     * 总页数
     */
    private int totalPageCount = 1;

    /**
     * 以当前页为中心的页码数组, 默认页码数组的长度为defaultNumberLength
     */
    private int[] numbers;

    /**
     * 所有的当前页面数据的容器list
     */
    protected List<T> list;

    /**
     * 页码容器的数组默认长度
     */
    private int defaultNumberLength = 10;

    /**
     * 得到开始记录在总记录中的位置
     *
     * @return 返回开始的记录在总记录中的位置
     */
    public int getStartRow() {
        return (index - 1) * size;
    }

    /**
     * 得到本页的结束记录在所有记录中的位置
     *
     * @return 返回结束记录在所有记录中的位置
     */
    public int getEndRow() {
        return index * size;
    }

    /**
     * 取得每页的页数大小
     *
     * @return Returns the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * The size to set.
     *
     * @param size 设置每页大小
     */
    public void setSize(int size) {
        if (size > 0) {
            this.size = size;
        }
    }

    /**
     * 取得当前页数
     *
     * @return Returns the currentPageNo.
     */
    public int getIndex() {
        if (totalPageCount == 0) {

            return 0;
        }

        return index;
    }

    /**
     * 设置当前页
     *
     * @param index The currentPageNo to set.
     */
    public void setIndex(int index) {
        if (index > 0) {
            this.index = index;
        }
    }

    /**
     * 取得所有的数据大小
     *
     * @return Returns the totalCount.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置所有记录的条数
     *
     * @param totalCount The totalCount to set.
     */
    public void setTotalCount(int totalCount) {
        if (totalCount >= 0) {
            this.totalCount = totalCount;
            setTotalPageCountByRs();//根据总记录数计算总总页数
        }
    }

    /**
     * 取得所有的页数
     *
     * @return 返回所有的页数多少
     */
    public int getTotalPageCount() {
        return this.totalPageCount;
    }

    /**
     * 根据总记录数计算总页
     * 5
     * 20    4
     * 23    5
     */
    private void setTotalPageCountByRs() {
        if (this.size > 0 && this.totalCount > 0 && this.totalCount % this.size == 0) {
            this.totalPageCount = this.totalCount / this.size;
        } else if (this.size > 0 && this.totalCount > 0 && this.totalCount % this.size > 0) {
            this.totalPageCount = (this.totalCount / this.size) + 1;
        } else {
            this.totalPageCount = 0;
        }
        setNumbers(totalPageCount);//获取展示页数集合
    }

    /**
     * 取得以当前页为主的页码数组
     *
     * @return 返回页码数组
     */
    public int[] getNumbers() {
        return numbers;
    }

    /**
     * 设置显示页数集合
     * <p>
     * 默认显示10个页码
     * 41  42  43  44    [45 ]   46  47  48  49  50
     * <p>
     * <p>
     * [1] 2  3 4  5 6 7 8  9  10
     * <p>
     * 41  42  43  44    45    46  47  [48]  49  50
     *
     * @param totalPageCount 所有页数
     */
    private void setNumbers(int totalPageCount) {
        if (totalPageCount > 0) {
            //!.当前数组的长度
            int[] numbers = new int[Math.min(totalPageCount, defaultNumberLength)];//页面要显示的页数集合
            int k = 0;
            //
            //1.数组长度<10   1 2 3 4 ....   7
            //2.数组长度>=10
            //     当前页<=6  1 2 3 4    10
            //     当前页>=总页数-5           ......12 13 14 15
            //     其他                                5  6  7 8   9 当前页(10)  10  11 12  13
            for (int i = 0; i < totalPageCount; i++) {
                //保证当前页为集合的中心
                if ((i >= index - (numbers.length / 2 + 1) || i >= totalPageCount - numbers.length) && k < numbers.length) {
                    numbers[k] = i + 1;
                    k++;
                } else if (k >= numbers.length) {
                    break;
                }
            }

            this.numbers = numbers;
        }

    }

    /**
     * 设置页码数据
     *
     * @param numbers 页码数组
     */
    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * 取得所有本页数据
     *
     * @return 返回所有本页数据的容器list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页所有数据
     *
     * @param list 本页所有数据的容器
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 设置存储显示的页码长度, 默认为10条记录
     *
     * @param defaultNumberLength 设置显示的页码长度
     */
    public void setDefaultNumberLength(int defaultNumberLength) {
        this.defaultNumberLength = defaultNumberLength;
    }
}
