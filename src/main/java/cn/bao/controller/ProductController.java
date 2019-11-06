package cn.bao.controller;

import cn.bao.pojo.Product;

import java.util.List;

public interface ProductController {
    /**
     * 获取章节没有被爬取完的书籍
     * @param maxBookCount  限制爬取的书籍的章节
     * @param page  获取的页数
     * @param size  每页书籍的数目
     * @return  章节没有被爬取完的书籍集合
     */
    List<Product> getList(int maxBookCount, int page, int size);

    /**
     * 更新书籍数量
     * @param product 新的书籍信息
     * @return  更新的条数,-1为数据有误或者所更新的书籍不存在
     */
    int updateCount(Product product);


}
