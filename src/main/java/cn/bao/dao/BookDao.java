package cn.bao.dao;

import cn.bao.pojo.Book;

import java.util.List;

public interface BookDao {
    Book getById(int id);
    Book getByTile(String title);
    Book getByLink(String link);
    Book getBySkuId(String skuId);

    List<Book> getList(int linkId,int page,int size);

    List<Book> getByContentLike(String content);
    List<Book> getByContentLike(String content,int page,int size);

    List<Book> getListByAuthor(String author,int page,int size);

    List<Book> getListByTitleLike(String title);
    List<Book> getListByTitleLike(String title,int page,int size);
    List<Book> getListByTitleContentLike(String title,String content,int page,int size);

    int insert(Book book);
    int delete(Book book);
    int update(Book book);
    int inserts(List<Book> books);
}
