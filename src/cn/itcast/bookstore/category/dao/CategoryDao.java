package cn.itcast.bookstore.category.dao;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询所有分类
     *
     * @return
     */
    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加分类
     *
     * @param category
     */
    public void add(Category category) {
        try {
            String sql = "insert into category values(?,?)";
            qr.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 删除分类
     *
     * @param cid
     */
    public void delete(String cid) {
        try {
            String sql = "delete from category where cid=?";
            qr.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
