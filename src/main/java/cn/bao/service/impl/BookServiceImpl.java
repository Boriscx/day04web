package cn.bao.service.impl;

import cn.bao.dao.BookDao;
import cn.bao.dao.Impl.BookDaoImpl;
import cn.bao.pojo.Book;
import cn.bao.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public Book getById(int id) {
        if (id<=0) return null;
        return bookDao.getById(id);
    }

    @Override
    public Book getByTile(String title) {
        if (title == null) return null;
        return bookDao.getByTile(title);
    }

    @Override
    public Book getByLink(String link) {
        if (link == null)return null;
        return bookDao.getByLink(link);
    }

    @Override
    public Book getBySkuId(String skuId) {
        if (skuId == null || skuId.matches("[^\\d_]+")) return null;
        return bookDao.getBySkuId(skuId);
    }

    @Override
    public List<Book> getList(int linkId, int page, int size) {
        return null;
    }

    @Override
    public List<Book> getByContentLike(String content) {
        return null;
    }

    @Override
    public List<Book> getByContentLike(String content, int page, int size) {
        return null;
    }

    @Override
    public List<Book> getListByAuthor(String author, int page, int size) {
        return null;
    }

    @Override
    public List<Book> getListByTitleLike(String title) {
        return null;
    }

    @Override
    public List<Book> getListByTitleLike(String title, int page, int size) {
        return null;
    }

    @Override
    public List<Book> getListByTitleContentLike(String title, String content, int page, int size) {
        return null;
    }

    @Override
    public int insert(Book book) {
        return 0;
    }

    @Override
    public int delete(Book book) {
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public int inserts(List<Book> books) {
        return 0;
    }
}
