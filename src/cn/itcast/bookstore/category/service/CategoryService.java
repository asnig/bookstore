package cn.itcast.bookstore.category.service;

import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();


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
}
