package cn.bao.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {
    private int id;
    private String title;
    private String link;

    private int count;
    private int haveCount;

    private List<Book> books;

    public Product(String title, String link, int count, int haveCount) {
        this.title = title;
        this.link = link;
        this.count = count;
        this.haveCount = haveCount;
    }

    public Product(int id, String title, String link, int count) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.count = count;
    }

    public Product(int id, String title, String link, int count, int haveCount) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.count = count;
        this.haveCount = haveCount;
    }



    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Product setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Product setLink(String link) {
        this.link = link;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Product setCount(int count) {
        this.count = count;
        return this;
    }

    public int getHaveCount() {
        return haveCount;
    }

    public Product setHaveCount(int haveCount) {
        this.haveCount = haveCount;
        return this;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Product setBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                count == product.count &&
                haveCount == product.haveCount &&
                Objects.equals(title, product.title) &&
                Objects.equals(link, product.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, count, haveCount);
    }
}
