package com.example.demo.controller;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entity.Book;
import com.example.demo.service.AdminService;
import com.example.demo.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
/**

 * Class Name: AdminController

 *

 * Purpose:

 * This controller handles administrative operations such as managing users and books.

 * Only users with the 'ADMIN' role are authorized to access these endpoints.

 *

 * Interface Description:

 * - GET /api/admin/users: Returns all users in the system.

 * - DELETE /api/admin/users/{id}: Deletes a user by ID.

 * - POST /api/admin/books: Adds a new book.

 * - PUT /api/admin/books/{id}: Updates an existing book.

 * - DELETE /api/admin/books/{id}: Deletes a book.

 *

 * Important Data:

 * - AdminService: handles user-related admin operations.

 * - BookService: manages CRUD operations for books.

 *

 * Development History:

 * - Designed by: Peilin Li,Yunyi jiang

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private BookService bookService;

    // download cover image to the local path
    private static final String UPLOAD_DIR = "demo/AddedBookCovers";  // reletave path
//    @Value("${file.upload-dir}")
//    private String uploadDir;

    // get all users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getAllUsers() {
        return adminService.getAllUsers().stream()
                .map(UserProfileResponse::new)
                .collect(Collectors.toList());
    }

    // delete user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok("user has been deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 📚 add book
    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")

//    // upload reletave path
    public ResponseEntity<?> addBook(@RequestParam("title") String title,
                                     @RequestParam("author") String author,
                                     @RequestParam("description") String description,
                                     @RequestParam("genre") String genre,
                                     @RequestParam("image") MultipartFile image) {

        // get filename
        String fileName = image.getOriginalFilename();

        // Get the current working directory and construct the file save path
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, UPLOAD_DIR, fileName);
        System.out.println(currentDirectory);
        System.out.println("xxxxxxx"+filePath);
        // ensure exist
        File directory = new File(currentDirectory, UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            // upload
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // save
            String imagePath = "/" + UPLOAD_DIR + "/" + fileName;
            System.out.println("xxxxxxxx"+imagePath);

            // create Book entity
            Book addedBook = new Book();
            addedBook.setTitle(title);
            addedBook.setAuthor(author);
            addedBook.setDescription(description);
            addedBook.setCoverImage(imagePath);  // set image path
            addedBook.setGenre(genre);

            // use bookService.addBook()  to insert data to database
            Book savedBook = bookService.addBook(addedBook);  // save
            System.out.println("Successfully saved: " + savedBook.getBookId());

            return ResponseEntity.ok(Collections.singletonMap("message", "Book uploaded successfully!"));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload the image.");

        }
    }

    // ✏️ uodate book
    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        try {
            Book updated = bookService.updateBook(id, book);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🗑️ delete book
    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("the Book has been deleted successfully! ");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

