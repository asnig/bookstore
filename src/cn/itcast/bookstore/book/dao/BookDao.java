package cn.itcast.bookstore.book.dao;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();


    /**
     * 查询所有图书
     *
     * @return
     */
    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按分类查询图书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=?";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据bid查询图书
     *
     * @param bid
     * @return
     */
    public Book findByBid(String bid) {
        try {
            /*
            我们需要在Book对象中保存Category对象
             */
            String sql = "select * from book where bid=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            /*
            使用一个Map，映射出两个对象，再给这两个对象建立关系！
             */
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询指定分类下的图书本数
     *
     * @param cid
     * @return
     */
    public int getCountByCid(String cid) {
        try {
            String sql = "select count(*) from book where cid=?";
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
