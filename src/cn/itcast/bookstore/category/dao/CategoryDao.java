package cn.itcast.bookstore.category.dao;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
