package com.example.demo.dto;

/**
 * Data Transfer Object representing a book in a user's wishlist.
 * Contains basic information such as book ID, title, author, and cover image URL.
 *
 * Author: Guanyuan Wang
 */
public class WishlistBookDto {
    private int bookId;         // Unique identifier for the book
    private String title;       // Title of the book
    private String author;      // Author of the book
    private String coverImage;  // URL or path to the book's cover image

    /**
     * Constructs a WishlistBookDto with all required fields.
     *
     * @param bookId      the ID of the book
     * @param title       the title of the book
     * @param author      the author of the book
     * @param coverImage  the cover image URL or path
     */
    public WishlistBookDto(int bookId, String title, String author, String coverImage) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.coverImage = coverImage;
    }

    /**
     * Gets the ID of the book.
     * @return bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the ID of the book.
     * @param bookId the book ID
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the title of the book.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * @param author the book's author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the cover image URL or path.
     * @return coverImage
     */
    public String getCoverImage() {
        return coverImage;
    }

    /**
     * Sets the cover image URL or path.
     * @param coverImage the cover image
     */
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
