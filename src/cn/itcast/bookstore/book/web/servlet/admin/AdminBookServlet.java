package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminBookServlet", urlPatterns = "/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * 查询所有图书
     *
     * @param request
     * @param responsep
     * @return
     */
    public String findAll(HttpServletRequest request, HttpServletResponse responsep) {
        request.setAttribute("bookList", bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }
}
