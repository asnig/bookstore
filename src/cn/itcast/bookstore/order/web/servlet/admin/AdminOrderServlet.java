package cn.itcast.bookstore.order.web.servlet.admin;

import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = "/admin/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 查询所有订单
     *
     * @param request
     * @param response
     * @return
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Order> orderList = orderService.findAll();
        request.setAttribute("orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * 根据订单状态查询订单
     *
     * @param request
     * @param response
     * @return
     */
    public String findByState(HttpServletRequest request, HttpServletResponse response) {
        String state = request.getParameter("state");
        List<Order> orderList = orderService.findByState(state);
        request.setAttribute("orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }
}
