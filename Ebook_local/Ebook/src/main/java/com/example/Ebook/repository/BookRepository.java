package com.example.Ebook.repository;
import org.springframework.data.jpa.repository.Query;
import com.example.Ebook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    // 新增方法1：查找所有不同的种类
    @Query("SELECT DISTINCT b.genre FROM Book b WHERE b.genre IS NOT NULL")
    List<String> findDistinctGenres();

    // 新增方法：根据种类查找书籍
    List<Book> findByGenre(String genre);

    // 搜索书名或作者中包含关键词的书籍
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);

}
