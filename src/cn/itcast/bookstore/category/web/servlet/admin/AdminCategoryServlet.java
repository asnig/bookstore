package cn.itcast.bookstore.category.web.servlet.admin;

import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.调用service方法,得到所有分类
        2.保存到request域,并转发
         */

        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }
}
