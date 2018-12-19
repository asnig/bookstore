package cn.itcast.bookstore.order.web.servlet;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.bookstore.order.service.OrderException;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 加载订单
     *
     * @param request
     * @param response
     * @return
     */
    public String load(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("order", orderService.load(request.getParameter("oid")));
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * 我的订单
     *
     * @param request
     * @param response
     * @return
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList", orderList);
        return "f:/jsps/order/list.jsp";
    }

    /**
     * 添加订单
     * 把session中的车用来生成Order对象
     *
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        //从session中获取cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //把cart转换为Order对象
        Order order = new Order();
        order.setOid(CommonUtils.uuid());//设置编号
        order.setOrdertime(new Date());//设置下单时间
        order.setState(1);//设置订单状态为1,表示未付款
        User user = (User) request.getSession().getAttribute("session_user");
        order.setOwner(user);//设置订单所有者
        order.setTotal(cart.getTotal());//设置订单的合计，从cart中获取

        //创建订单条目集合
        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem oi = new OrderItem();//创建订单条目

            oi.setIid(CommonUtils.uuid());//设置条目id
            oi.setCount(cartItem.getCount());//设置条目的数量
            oi.setBook(cartItem.getBook());//设置条目的图书
            oi.setSubtotal(cartItem.getSubtotal());//设置条目的小计
            oi.setOrder(order);//设置所属订单
            orderItemList.add(oi);
        }

        //把所有的订单条目添加到订单中
        order.setOrderItemList(orderItemList);

        //清空购物车
        cart.clear();

        orderService.add(order);

        request.setAttribute("order", order);
        return "f:/jsps/order/desc.jsp";

    }

    /**
     * 确认收货
     *
     * @param request
     * @param response
     * @return
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "恭喜，交易成功！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }
}
