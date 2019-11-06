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
        if (id <= 0) return null;
        return bookDao.getById(id);
    }

    @Override
    public Book getByTile(String title) {
        if (title == null) return null;
        return bookDao.getByTile(title);
    }

    @Override
    public Book getByLink(String link) {
        if (link == null) return null;
        return bookDao.getByLink(link);
    }

    @Override
    public Book getBySkuId(String skuId) {
        if (skuId == null || !skuId.matches("[\\d_]+")) return null;
        return bookDao.getBySkuId(skuId);
    }

    @Override
    public List<Book> getList(int linkId, int page, int size) {
        if (linkId < 0) return null;
        if (page < 0) page = 1;
        if (size < 0) size = 20;
        return bookDao.getList(linkId, page, size);
    }

    @Override
    public List<Book> getByContentLike(String content) {
        return this.getByContentLike(content, 1, 20);
    }

    @Override
    public List<Book> getByContentLike(String content, int page, int size) {
        if (content == null) content = "";
        return bookDao.getByContentLike(content, page, size);
    }

    @Override
    public List<Book> getListByAuthor(String author, int page, int size) {
        if (author == null) author = "";
        if (page < 1) page = 1;
        if (size <= 0) size = 20;
        return bookDao.getListByAuthor(author, page, size);
    }

    @Override
    public List<Book> getListByTitleLike(String title) {
        return this.getListByTitleLike(title, 1, 20);
    }

    @Override
    public List<Book> getListByTitleLike(String title, int page, int size) {
        return getListByTitleContentLike(title, null, page, size);
    }

    @Override
    public List<Book> getListByTitleContentLike(String title, String content, int page, int size) {
        if (title == null) title = "";
        if (content == null) content = "";
        if (page < 1) page = 1;
        if (size <= 0) size = 20;
        return bookDao.getListByTitleContentLike(title, content, page, size);
    }

    @Override
    public int insert(Book book) {
        if (book == null) return -1;
        return bookDao.insert(book);
    }

    @Override
    public int delete(Book book) {
        if (book == null) return -1;
        return bookDao.delete(book);
    }

    @Override
    public int update(Book book) {
        if (book == null) return -1;

        return bookDao.update(book);
    }

    @Override
    public int inserts(List<Book> books) {
        if (books == null || books.size() == 0) return -1;
        return bookDao.inserts(books);
    }
}
