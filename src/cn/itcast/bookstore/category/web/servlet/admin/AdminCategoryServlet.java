package cn.itcast.bookstore.category.web.servlet.admin;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();
    private BookService bookService = new BookService();

    /**
     * 删除分类
     *
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取参数：cid
        2.调用service方法，传递参数
            > 如果抛出异常，保存并转发
        3.调用findAll()
         */
        String cid = request.getParameter("cid");
        try {
            categoryService.delete(cid);
            return findAll(request, response);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
    }

    /**
     * 添加分类
     *
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());

        categoryService.add(category);
        return findAll(request, response);
    }
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.调用service方法,得到所有分类
        2.保存到request域,并转发
         */

        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }
}
