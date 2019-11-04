package cn.bao.service;

import cn.bao.pojo.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    Product getById(int id) throws SQLException;
    Product getByTile(String title);
    Product getByLink(String link);
    List<Product> getList(String objects);
    List<Product> getList(String title,int page,int size);
    List<Product> getList(String title,String link,int page,int size);
    int insert(Product product);
    int delete(Product product);
    int update(Product product);
    int inserts(List<Product> products);
}
