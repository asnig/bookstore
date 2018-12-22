package cn.itcast.bookstore.order.service;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    /**
     * 支付方法
     *
     * @param oid
     */
    public void zhiFu(String oid) {
        /*
        1.如果订单的状态
            如果状态为1，那么执行下面的代码
            如果状态不为1，那么本方法什么都不做
         */
        int state = orderDao.getStateByOid(oid);
        if (state == 1) {
            orderDao.updateState(oid, 2);
        }
    }

    /**
     * 添加订单
     * 需要处理事务
     *
     * @param order
     */
    public void add(Order order) {
        try {
            //开启事务
            JdbcUtils.beginTransaction();
            orderDao.addOrder(order);//插入订单
            orderDao.addOrderItemList(order.getOrderItemList());//插入订单中的所有条目
            //提交事务
            JdbcUtils.commitTransaction();
        } catch (Exception e) {
            //回滚事务
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 我的订单
     *
     * @param uid
     * @return
     */
    public List<Order> myOrders(String uid) {
        return orderDao.findByUid(uid);
    }

    /**
     * 加载订单
     *
     * @param oid
     * @return
     */
    public Order load(String oid) {
        return orderDao.load(oid);
    }

    /**
     * 确认收货
     *
     * @param oid
     * @throws OrderException
     */
    public void confirm(String oid) throws OrderException {
        /*
        校验订单状态，如果不是3，抛出异常
         */
        int state = orderDao.getStateByOid(oid);//获取订单状态
        if (state != 3) throw new OrderException("订单确定失败，你不是什么还东西！");
        /*
        修改订单状态为4
         */
        orderDao.updateState(oid, 4);
    }


    /**
     * 查询所有订单
     *
     * @return
     */
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    /**
     * 根据订单状态查询订单
     *
     * @param state
     * @return
     */
    public List<Order> findByState(String state) {
        return orderDao.findByState(state);
    }
}
