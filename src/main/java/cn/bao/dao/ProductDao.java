package cn.bao.dao;

import cn.bao.pojo.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    Product getById(int id);
    Product getByTile(String title);
    Product getByLink(String link);

    List<Product> getList(String title,String link,int page,int size);
    List<Product> getList(int maxBookCount,int page ,int size);

    int insert(Product product);
    int delete(Product product);
    int update(Product product);
    int inserts(List<Product> products);

}
