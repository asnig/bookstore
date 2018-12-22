package cn.itcast.bookstore.book.service;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

import java.util.List;

public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     *
     * @return
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * 按分类查询图书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    /**
     * 根据bid查询图书
     *
     * @param bid
     * @return
     */
    public Book load(String bid) {
        return bookDao.findByBid(bid);
    }

    /**
     * 添加图书
     *
     * @param book
     */
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * 删除图书
     *
     * @param bid
     */
    public void delete(String bid) {
        bookDao.delete(bid);
    }

    /**
     * 编辑图书
     *
     * @param book
     */
    public void edit(Book book) {
        bookDao.edit(book);
    }
}
