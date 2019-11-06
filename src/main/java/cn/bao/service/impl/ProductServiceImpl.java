package cn.bao.service.impl;

import cn.bao.dao.Impl.ProductDaoImpl;
import cn.bao.dao.ProductDao;
import cn.bao.pojo.Product;
import cn.bao.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public Product getById(int id) {
        return id < 0 ? null : productDao.getById(id);
    }

    @Override
    public Product getByTile(String title) {
        return title == null ? null : productDao.getByTile(title);
    }

    @Override
    public Product getByLink(String link) {
        return link == null ? null : productDao.getByLink(link);
    }


    @Override
    public List<Product> getList(String title, int page, int size) {
        return this.getList(title,null,page,size);
    }

    @Override
    public List<Product> getList(String title, String link, int page, int size) {
        if (page < 0) page = 1;
        if (size < 0) size = 20;
        if (title == null) title = "";
        if (link == null) link = "";
        return productDao.getList(title, link, page, size);
    }

    @Override
    public List<Product> getList(int maxBookCount, int page, int size) {
        if (maxBookCount<=0) maxBookCount = 200;
        if (page < 1 )page = 1;
        if (size <= 0 )size = 20;
        return productDao.getList(maxBookCount,page,size);
    }

    @Override
    public int insert(Product product) {
        if (product == null) return 0;
        if (getByLink(product.getLink())!=null) return 0;
        return productDao.insert(product);
    }

    @Override
    public int delete(Product product) {
        if (product == null ) return 0;
        return productDao.delete(product);
    }

    @Override
    public int update(Product product) {
        if (product == null) return 0;
        return productDao.insert(product);
    }

    @Override
    public int inserts(List<Product> products) {
        if (products==null||products.size()<=0)return 0;
        return productDao.inserts(products);
    }
}
