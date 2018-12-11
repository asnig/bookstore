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

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
