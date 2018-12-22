package cn.itcast.bookstore.user.web.filter;

import cn.itcast.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/jsps/cart/*", "/jsps/order/*",}, servletNames = {"OrderServlet", "CartServlet"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*
        1.从session中获取用户信息
        2.判断如果sesison中存在用户信息，放行！
        3.否则，保存错误信息，转发到login.jso显示
         */
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute("session_user");
        if (user != null) {
            chain.doFilter(req, resp);
        } else {
            request.setAttribute("msg", "你还没有登录！");
            request.getRequestDispatcher("jsps/user/login.jsp")
                    .forward(request, resp);
        }

    }

    public void init(FilterConfig config) {

    }

}
