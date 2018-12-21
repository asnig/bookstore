package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminBookServlet", urlPatterns = "/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    /**
     * 加载图书
     *
     * @param request
     * @param responsep
     * @return
     */
    public String load(HttpServletRequest request, HttpServletResponse responsep) {
        Book book = bookService.load(request.getParameter("bid"));
        request.setAttribute("categoryList", categoryService.findAll());
        request.setAttribute("book", book);
        return "f:/adminjsps/admin/book/desc.jsp";
    }

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
