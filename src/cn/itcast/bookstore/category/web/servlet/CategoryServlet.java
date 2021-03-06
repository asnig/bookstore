package cn.itcast.bookstore.category.web.servlet;

import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/jsps/left.jsp";
    }
}
