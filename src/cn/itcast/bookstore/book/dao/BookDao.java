package cn.itcast.bookstore.book.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

}
