package cn.itcast.bookstore.order.dao;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加订单
     *
     * @param order
     */
    public void addOrder(Order order) {
        try {
            String sql = "insert into orders values(?,?,?,?,?,?)";
            /*
            处理util的Date转换为sql的Timestamp
             */
            Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
            Object[] parms = {order.getOid(), timestamp, order.getTotal(), order.getState(),
                    order.getOwner().getUid(), order.getAddress()};
            qr.update(sql, parms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 添加订单条目
     *
     * @param orderItemList
     */
    public void addOrderItemList(List<OrderItem> orderItemList) {
        try {
            /*
            把orderItemList转换为两位数组
            把一个OrderItem对象转换成一个一维数组
             */
            String sql = "insert into orderitem values(?,?,?,?,?)";
            Object[][] params = new Object[orderItemList.size()][];
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem item = orderItemList.get(i);
                params[i] = new Object[]{item.getIid(), item.getCount(), item.getSubtotal(),
                        item.getOrder().getOid(), item.getBook().getBid()
                };
            }
            qr.batch(sql, params);//执行批处理
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按uid查询订单
     *
     * @param uid
     * @return
     */
    public List<Order> findByUid(String uid) {
        try {
            String sql = "select * from orders where uid=?";
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class), uid);

            for (Order order : orderList) {
                loadOrderItems(order);//为每个order对象添加它的所有订单条目
            }
            return orderList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载指定的订单的所有的条目
     *
     * @param order
     * @throws SQLException
     */
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

    /**
     * 把很多个Map转换为很多个OrderItem
     *
     * @param mapList
     * @return
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    /**
     * 把一个Map转换为一个OrderItem对象
     *
     * @param map
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    /**
     * 加载订单
     *
     * @param oid
     * @return
     */
    public Order load(String oid) {
        try {
            /*
            1.得到当前用户的所有订单
             */
            String sql = "select * from orders where oid=?";
            Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
            /*
            2.为order加载它的所有订单条目
             */
            loadOrderItems(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据oid查询订单状态
     *
     * @param oid
     * @return
     */
    public int getStateByOid(String oid) {
        try {
            String sql = "select state from orders where oid=?";
            return (Integer) qr.query(sql, new ScalarHandler(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单状态
     *
     * @param oid
     * @param state
     * @return
     */
    public void updateState(String oid, int state) {
        try {
            String sql = "update orders set state=? where oid=?";
            qr.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有订单
     *
     * @return
     */
    public List<Order> findAll() {
        try {
            String sql = "select * from orders";
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class));
            for (Order order : orderList) {
                loadOrderItems(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据订单状态查询订单
     *
     * @param state
     * @return
     */
    public List<Order> findByState(String state) {
        try {
            String sql = "select * from orders where state=?";
            List<Order> orderList = qr.query(sql, new BeanListHandler<>(Order.class), state);
            for (Order order : orderList) {
                loadOrderItems(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
