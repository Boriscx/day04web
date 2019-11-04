package cn.bao.dao.Impl;

import cn.bao.dao.BookDao;
import cn.bao.pojo.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl extends BaseDaoImpl implements BookDao {

    @Override
    public Book getById(int id) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE ID=?", id);
        try {
            if (re != null && re.next()) {
                return getBook(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getByTile(String title) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE TITLE=?", title);
        try {
            if (re != null && re.next()) {
                return getBook(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getByLink(String link) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE LINK=?", link);
        try {
            if (re != null && re.next()) {
                return getBook(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBySkuId(String skuId) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE SKUID=?", skuId);
        try {
            if (re != null && re.next()) {
                return getBook(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Book getBook(ResultSet re) throws Exception {
        String skuId = re.getString("SKUID");
        int linkId = re.getInt("LINKID");
        String author = re.getString("AUTHOR");
        int id = re.getInt("ID");
        String title = re.getString("TITLE");
        String context = re.getString("content");
        String link = re.getString("link");
        return new Book(id, linkId, author, skuId, title, context, link);

    }

    private List<Book> getBookList(ResultSet re) {
        ArrayList<Book> list = new ArrayList<>();
        try {
            while (true) {
                if (!(re != null && re.next())) break;
                list.add(getBook(re));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getList(int linkId, int page, int size) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE LINKID=? ", linkId, (page - 1) * size, size);
        return getBookList(re);
    }


    @Override
    public List<Book> getByContentLike(String content) {
        return this.getByContentLike(content, 1, 20);
    }

    @Override
    public List<Book> getByContentLike(String content, int page, int size) {
        content = "%" + content + "%";
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE CONTENT LIKE ? LIMIT ?,?", content, (page - 1) * size, size);
        return getBookList(re);
    }

    @Override
    public List<Book> getListByAuthor(String author, int page, int size) {
        ResultSet re = jdbcUtil.getResultSet("SELECT * FROM BOOK WHERE AUTHOR=? LIMIT ?,?", author, (page - 1) * size, size);
        return getBookList(re);
    }

    @Override
    public List<Book> getListByTitleLike(String title) {
        return this.getListByTitleLike(title, 1, 20);
    }

    @Override
    public List<Book> getListByTitleLike(String title, int page, int size) {
        title = "%" + title + "%";
        String sql = "SELECT * FROM BOOK WHERE TITLE LIKE ? LIMIT ?,?";
        return getBookList(jdbcUtil.getResultSet(sql, title, (page - 1) * size, size));
    }

    @Override
    public List<Book> getListByTitleContentLike(String title, String content, int page, int size) {
        title = "%" + title + "%";
        content = "%" + content + "%";
        String sql = "SELECT * FROM BOOK WHERE TITLE LIKE ? AND CONTENT LIKE ? LIMIT ?,?";
        return getBookList(jdbcUtil.getResultSet(sql, title, content, (page - 1) * size, size));
    }


    @Override
    public int insert(Book book) {
        String sql = "INSERT INTO BOOK VALUES (NULL,?,?,?,?,?,?)";
        return jdbcUtil.update(sql, book.getLinkId(), book.getAuthor(), book.getSkuId(), book.getTitle(), book.getContent(), book.getLink());

    }

    @Override
    public int delete(Book book) {
        return jdbcUtil.update("DELETE FROM BOOK WHERE ID=?",book.getId());
    }

    @Override
    public int update(Book book) {
        return delete(book)==1?-1:insert(book);
    }

    @Override
    public int inserts(List<Book> books) {
        StringBuilder sql = new StringBuilder("INSERT INTO BOOK VALUES");
        List<Object> list = new ArrayList<>();
        for (Book b : books){
            sql.append("(NULL,?,?,?,?,?,?)").append(",");
            list.add(b.getLinkId());
            list.add(b.getAuthor());
            list.add(b.getSkuId());
            list.add(b.getTitle());
            list.add(b.getContent());
            list.add(b.getLink());
        }
        return jdbcUtil.update(sql.toString(),list.toArray());
    }
}
