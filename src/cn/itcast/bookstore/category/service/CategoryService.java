package cn.itcast.bookstore.category.service;

import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();


    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
