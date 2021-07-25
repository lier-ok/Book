package com.lier.service;

import com.lier.pojo.Book;
import com.lier.pojo.Page;

import java.util.List;

/**
 * @Author lier
 * @date 2021/7/23 - 13:41
 * @Decription
 * @since jdk1.8
 */

public interface BookService {

    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
