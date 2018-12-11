package cn.itcast.bookstore.book.service;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

import java.util.List;

public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * 按分类查询图书
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }
}
