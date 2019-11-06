package cn.bao.controller.impl;

import cn.bao.controller.BookController;
import cn.bao.pojo.Book;
import cn.bao.pojo.Product;
import cn.bao.service.BookService;
import cn.bao.service.ProductService;
import cn.bao.service.impl.BookServiceImpl;
import cn.bao.service.impl.ProductServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookControllerImpl implements BookController {
    private BookService bookService = new BookServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private List<Product> products;
    private List<Book> books = new ArrayList<>();
    private LinkedList<Book> booklinks = new LinkedList<>();
    private List<Runnable> runnables = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(5);

    public void lunch() {
        products = productService.getList(300, 1, 200);
        pool.execute(() -> test());
        for (Product p : products) {
            pool.execute(createRunnable(p));
        }
    }

    private Runnable createRunnable(Product product) {
        return () -> {
            getBookListFromWebPage(product);
        };
    }

    private void getBookListFromWebPage(Product p) {
        try {
            Document document = Jsoup.connect(p.getLink()).get();
            getBookLike(document, p);
        } catch (Exception e) {
            System.out.println("getBookListFromWebPage error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getBookLike(Document document, Product p) {
        String author = document.getElementById("info").selectFirst("p").text();
        author = author.substring(author.indexOf("：") + 1);//作者
        ArrayList<Book> books = new ArrayList<>();

        Elements select = document.select("div.box_con div dd");
        for (Element e : select) {
            String href = "http://www.3hebao.co" + e.selectFirst("a").attr("href");
            String title = e.text();
            String skuId = href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf(".html"));
            Book book = new Book(p.getId(), author, skuId, title, "", href);
            synchronized (booklinks) {
                booklinks.add(book);
                booklinks.notifyAll();
            }
        }
        //p.setBooks(books);
        p.setCount(books.size());
        productService.update(p);
    }

    public void test() {
        while (true) {
            synchronized (booklinks) {
                while (booklinks.size() == 0) {
                    try {
                        booklinks.wait();
                        System.out.println("test wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getContent(booklinks.remove());
                //pool.execute(()->{getContent(booklinks.removeFirst());});
            }

        }
    }
    private static int nowLinkId = 0;
    private void getContent(Book book) {

        if (nowLinkId!=book.getLinkId()){
            System.out.println("----------------------------------");
            System.out.println("getContent linkId:" + book.getLinkId()+"\t");
            nowLinkId = book.getLinkId();
        }
        String content = "";
        try {
            Document document = Jsoup.connect(book.getLink()).get();
            Element element = document.getElementById("content");
            content = element.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content == null || "".equals(content)) {
            System.out.println("content is null");
            return;
        }else {
            printTime();
        }
        content = content.replaceAll("<[\\s\\S]*?>", "").
                replaceAll("[^\\w\\u4e00-\\u9fa5,.?!\"“”:：]+", "");
        book.setContent(content);
        bookService.insert(book);
    }

    private void printTime() {
        System.out.println(new Date().toLocaleString());
    }

    public static void main(String[] args) {
        new BookControllerImpl().lunch();
    }

}
