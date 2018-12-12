package cn.itcast.bookstore.cart.domain;

import cn.itcast.bookstore.book.domain.Book;

import java.math.BigDecimal;

/**
 * 购物车条目类
 */
public class CartItem {
    private Book book;
    private int count;

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }

    /**
     * 计算合计，用BigDecimal解决二进制计算误差问题
     *
     * @return
     */
    public double getSubtotal() {
        BigDecimal d1 = new BigDecimal(book.getPrice() + "");
        BigDecimal d2 = new BigDecimal(count + "");
        return d1.multiply(d2).doubleValue();

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
