package cn.bao.controller.impl;

import cn.bao.controller.ProductController;
import cn.bao.pojo.Product;
import cn.bao.service.ProductService;
import cn.bao.service.impl.ProductServiceImpl;
import org.junit.Test;

import java.util.List;

public class ProductControllImpl implements ProductController {

    private ProductService productService = new ProductServiceImpl();

    @Override
    public List<Product> getList(int maxBookCount, int page, int size) {
        return productService.getList(maxBookCount,page,size);
    }

    @Override
    public int updateCount(Product product) {
        return productService.update(product);
    }

    @Test
    public void getTest(){
        List<Product> list = getList(200, 1, 300);
        for (Product p :list)
        {
            System.out.println(p.getLink()+"\t"+p.getCount()+"\t"+p.getHaveCount());
        }
    }
}
