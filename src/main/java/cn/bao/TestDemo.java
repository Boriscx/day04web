package cn.bao;

import cn.bao.dao.BookDao;
import cn.bao.dao.Impl.BookDaoImpl;
import cn.bao.pojo.Book;
import cn.bao.pojo.Product;
import cn.bao.service.ProductService;
import cn.bao.service.impl.ProductServiceImpl;
import org.junit.Test;

import java.util.List;

public class TestDemo {
    @Test
    public void test(){
        ProductService productService = new ProductServiceImpl();
        List<Product> h = productService.getList(null, 1, 10);
        System.out.println(h.size());
    }

    @Test
    public void testBook(){
        BookDao bookDao = new BookDaoImpl();
        List<Book> l = bookDao.getListByTitleContentLike("", "", 1, 20);
        System.out.println(l==null?0:l.size());
    }

    @Test
    public void test2(){
        String sss = "89798798798746";
        System.out.println(sss.matches("[\\d_]*"));
    }
}
