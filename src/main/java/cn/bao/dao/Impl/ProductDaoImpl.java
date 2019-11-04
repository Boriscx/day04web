package cn.bao.dao.Impl;

import cn.bao.dao.ProductDao;
import cn.bao.pojo.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

    private Product getProduct(ResultSet re) throws Exception {
        int id = re.getInt("ID");
        String title = re.getString("TITLE");
        String link = re.getString("LINK");
        int count = re.getInt("COUNT");
        int haveCount = re.getInt("HAVECOUNT");
        return new Product(id, title, link, count, haveCount);
    }

    @Override
    public Product getById(int id) {
        ResultSet re = jdbcUtil.getResultSet("SELECT P.*, COUNT(B.LINKID) HAVECOUNT " +
                "FROM " +
                "PRODUCT P " +
                "LEFT JOIN BOOK B ON P.ID = B.LINKID " +
                "GROUP BY P.ID " +
                "HAVING P.ID=?", id);
        try {
            if (re != null && re.next()) {
                return getProduct(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getByTile(String title) {
        ResultSet re = jdbcUtil.getResultSet("SELECT P.*, COUNT(B.LINKID) HAVECOUNT " +
                "FROM " +
                "PRODUCT P " +
                "LEFT JOIN BOOK B ON P.ID = B.LINKID " +
                "GROUP BY P.ID " +
                "HAVING P.TITLE=?", title);
        try {
            if (re != null && re.next()) {
                return getProduct(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getByLink(String link) {
        ResultSet re = jdbcUtil.getResultSet("SELECT P.*, COUNT(B.LINKID) HAVECOUNT " +
                "FROM PRODUCT P LEFT JOIN BOOK B ON P.ID = B.LINKID " +
                "GROUP BY P.ID " +
                "HAVING P.LINK=?", link);
        if (re != null)
            try {
                if (re.next()) {
                    return getProduct(re);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public List<Product> getList(String objects) {
        return this.getList(objects, objects, 1, 50);
    }

    @Override
    public List<Product> getList(String title, int page, int size) {
        return this.getList(title, "", page, size);
    }

    @Override
    public List<Product> getList(String title, String link, int page, int size) {
        title = "%" + title + "%";
        link = "%" + link + "%";
        String sql = "SELECT P.*, COUNT(B.LINKID) HAVECOUNT " +
                "FROM PRODUCT P LEFT JOIN BOOK B ON P.ID = B.LINKID " +
                "WHERE P.TITLE LIKE ? AND P.LINK LIKE ? " +
                "GROUP BY P.ID " +
                "LIMIT ?,?";
        ArrayList<Product> list = new ArrayList<>();
        ResultSet re = jdbcUtil.getResultSet(sql, title, link, (page - 1) * size, size);
        if (re != null)
            try {
                while (re.next()) {
                    list.add(getProduct(re));
                }
                return list;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        return null;
    }

    @Override
    public int insert(Product product) {
        String sql = "INSERT PRODUCT VALUES(NULL,?,?,?)";
        return jdbcUtil.update(sql, product.getTitle(), product.getLink(), product.getCount());
    }

    @Override
    public int delete(Product product) {
        String sql = "DELETE FROM PRODUCT WHERE ID=?";
        return jdbcUtil.update(sql, product.getId());
    }

    @Override
    public int update(Product product) {
        String sql = "UPDATE PRODUCT SET TITLE=?,LINK=?,COUNT=? WHERE ID=?";
        return jdbcUtil.update(sql, product.getTitle(), product.getLink(), product.getCount(), product.getId());
    }

    @Override
    public int inserts(List<Product> products) {
        StringBuilder sb = new StringBuilder("INSERT INTO PRODUCT VALUES");
        ArrayList<Object> list = new ArrayList<>();
        for (Product p : products) {
            sb.append("(NULL,?,?,0)").append(",");
            list.add(p.getTitle());
            list.add(p.getLink());
        }
        sb.delete(sb.lastIndexOf(","), sb.length() - 1);
        return jdbcUtil.update(sb.toString(), list.toArray());
    }
}
