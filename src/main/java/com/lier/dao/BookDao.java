package com.lier.dao;


import com.lier.pojo.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookDao {

    public int addBook(Book book);

    public int deleteBookById(Integer id);

    public int updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    Integer queryForPageTotalCount();

    List<Book> queryForPageItems(@Param("begin") int begin, @Param("pageSize") int pageSize);

    Integer queryForPageTotalCountByPrice(@Param("min") int min, @Param("max")int max);

    List<Book> queryForPageItemsByPrice(@Param("begin") int begin, @Param("pageSize") int pageSize, @Param("min") int min, @Param("max") int max);

    
}
