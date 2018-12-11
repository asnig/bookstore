package cn.itcast.bookstore.book.web.servlet;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookServlet", urlPatterns = "/BookServlet")

public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * 查询所有图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("bookList", bookService.findAll());
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 按分类查询图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByCategory(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        request.setAttribute("bookList", bookService.findByCategory(cid));
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 加载图书
     *
     * @param request
     * @param response
     * @return
     */
    public String load(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("book", bookService.load(request.getParameter("bid")));
        return "f:/jsps/book/desc.jsp";
    }
}
