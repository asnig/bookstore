package test;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo1 {
    QueryRunner qr = new TxQueryRunner();

    @Test
    public void findByUid() {
        String uid = "249C9CE0A7BC453EAB369F937428B255";
        try {
            String sql = "select * from orders where uid=?";
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class), uid);
            for (Order order : orderList) {
                System.out.println("order before===" + order);
            }
            for (Order order : orderList) {
                loadOrderItems(order);//为每个order对象添加它的所有订单条目
            }
            for (Order order : orderList) {
                System.out.println("order after===" + order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderItems(Order order) throws SQLException {
        String sql = "select * from orderitem i,book b where i.bid=b.bid and oid=?";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());//每个map对应一行记录
        /*
        循环遍历每个Map，使用map生成两个对象，然后建立关系（结果是一个OrderItem）
        mapList.get(0) --> {iid=AC310065765E4D3FA6861472DAC6939F,count=1,orderitemsubtotal=83.30,
        oid=0C4C538C8FCD4B0F8C0965B6ABC3432B,bid=5,bname=JavaWeb开发详解,price=83.3,author=孙鑫,
        image=book_img/22788412-1_l.jpg,cid=2}
         */
        List<OrderItem> orderItemList = toOrderItemList(mapList);
        order.setOrderItemList(orderItemList);
    }

    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        System.out.println("orderItem=============" + orderItem);
        orderItem.setBook(book);
        System.out.println("orderItem=============" + orderItem);
        return orderItem;
    }

    @Test
    public void fun1() {
        try {
            String sql = "select * from book where bid=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), 3);
            System.out.println(map);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
