package pers.jssd.dao;

import pers.jssd.entity.ExpImage;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface ExpImageDao {

    /**
     * 添加一条图片记录
     *
     * @param image 需要添加的图片对象
     * @return 返回添加了几条记录
     * @throws Exception 添加过程中产生的异常
     */
    int insertExpImage(ExpImage image) throws Exception;


    List<ExpImage> listExpImages(int expId);
}
