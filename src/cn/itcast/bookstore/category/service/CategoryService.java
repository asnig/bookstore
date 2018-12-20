package cn.itcast.bookstore.category.service;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.web.servlet.admin.CategoryException;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();


    /**
     * 查询所有分类
     *
     * @return
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 添加分类
     *
     * @param category
     */
    public void add(Category category) {
        categoryDao.add(category);
    }

    /**
     * 删除分类
     *
     * @param cid
     */
    public void delete(String cid) throws CategoryException {
        //获取该分类下图书的本数
        int count = bookDao.getCountByCid(cid);
        //如果该分类还有图书，不能删除，抛出异常
        if (count > 0) throw new CategoryException("该分类下还有图书，不能删除！");
        //删除该图书分类
        categoryDao.delete(cid);
    }

    /**
     * 根据cid加载指定分类
     *
     * @param cid
     * @return
     */
    public Category load(String cid) {
        return categoryDao.findByCid(cid);
    }

    /**
     * 修改分类
     *
     * @param category
     */
    public void edit(Category category) {
        categoryDao.edit(category);
    }
}
