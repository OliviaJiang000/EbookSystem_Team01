package com.example.demo.ServiceTest;

import com.example.demo.dto.WishlistBookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Wishlist;
import com.example.demo.entity.WishlistItem;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.WishlistItemRepository;
import com.example.demo.repository.WishlistRepository;
import com.example.demo.service.impl.WishlistServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WishlistServiceTest {

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookToWishlist() {
        Integer userId = 1;
        Integer bookId = 101;

        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setWishlistId(1);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));
        when(wishlistItemRepository.existsByWishlist_WishlistIdAndBookId(1, bookId)).thenReturn(false);

        wishlistService.addBookToWishlist(userId, bookId);

        verify(wishlistItemRepository, times(1)).save(any(WishlistItem.class));
    }

    @Test
    public void testRemoveBookFromWishlist() {
        Integer userId = 1;
        Integer bookId = 101;

        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setWishlistId(1);

        WishlistItem item = new WishlistItem();
        item.setBookId(bookId);
        item.setWishlist(wishlist);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));
        when(wishlistItemRepository.findByWishlistWishlistId(1)).thenReturn(List.of(item));

        wishlistService.removeBookFromWishlist(userId, bookId);

        verify(wishlistItemRepository, times(1)).delete(item);
    }

    @Test
    public void testGetWishlistBooks() {
        Integer userId = 1;
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);
        wishlist.setUserId(userId);

        WishlistItem item = new WishlistItem();
        item.setBookId(101);
        item.setWishlist(wishlist);

        Book book = new Book();
        book.setBookId(101);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setCoverImage("img.jpg");

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));
        when(wishlistItemRepository.findByWishlistWishlistId(1)).thenReturn(List.of(item));
        when(bookRepository.findById(101)).thenReturn(Optional.of(book));

        List<WishlistBookDto> result = wishlistService.getWishlistBooks(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBookId()).isEqualTo(101);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
    }
}
