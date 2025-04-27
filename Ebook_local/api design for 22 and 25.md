

# 

**1. api for 2.2

| Url                                       | Method | response                                                     | Description            |
| ----------------------------------------- | ------ | ------------------------------------------------------------ | ---------------------- |
| http://localhost:8080/books?page=2&size=3 | Get    | [{"bookId":7,"title":"Brave New World","author":"Aldous Huxley","genre":"Dystopian","coverImage":"brave_new_world_cover.jpg","description":"A utopian society with an authoritarian regime that suppresses individual freedom.","leftCopies":9,"loanDuration":null,"createdAt":null},{"bookId":8,"title":"The Hobbit","author":"J.R.R. Tolkien","genre":"Fantasy","coverImage":"hobbit_cover.jpg","description":"The story of Bilbo Baggins and his journey through Middle-earth.","leftCopies":14,"loanDuration":null,"createdAt":null},{"bookId":9,"title":"The Picture of Dorian Gray","author":"Oscar Wilde","genre":"Philosophical","coverImage":"dorian_gray_cover.jpg","description":"A man鈥檚 portrait ages while he remains young, a story of vanity and corruption.","leftCopies":5,"loanDuration":null,"createdAt":null}] | 获取所有书籍及书籍详情 |
| http://localhost:8080/books/1             | get    | {"bookId":1,"title":"The Great Gatsby","author":"F. Scott Fitzgerald","genre":"Novel","coverImage":"great_gatsby_cover.jpg","description":"A novel set in the Jazz Age, exploring themes of wealth, class, and love.","leftCopies":10,"loanDuration":null,"createdAt":null} | 通过id查找书籍详情     |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |
|                                           |        |                                                              |                        |



```mysql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,    -- 用户 ID，自增
    email VARCHAR(255) NOT NULL UNIQUE,         -- 用户邮箱，确保唯一
    name VARCHAR(255) NOT NULL,                 -- 用户姓名
    password_hash VARCHAR(255) NOT NULL         -- 密码哈希值
);

```



2. Books table

   ```mysql
   CREATE TABLE books (
       book_id INT AUTO_INCREMENT PRIMARY KEY,  -- 书籍ID，自增，主键
       title VARCHAR(255) NOT NULL,              -- 书名，不能为空
       author VARCHAR(255) NOT NULL,             -- 作者，不能为空
       genre VARCHAR(100),                       -- 书籍类型
       description TEXT,                         -- 书籍描述
       cover_image VARCHAR(255),                 -- 书籍封面图，存储文件路径或URL
       available_copies INT NOT NULL DEFAULT 10    -- 可借书籍数量，默认值为10
   );
   
   ```

   

3. borrow_records table

   ```mysql
   CREATE TABLE borrow_records (
       borrow_record_id INT AUTO_INCREMENT PRIMARY KEY,  -- 借阅记录ID，自增，主键
       user_id INT NOT NULL,                              -- 用户ID，不能为空
       book_id INT NOT NULL,                              -- 书籍ID，不能为空
       borrow_date DATE NOT NULL,                         -- 借书日期，不能为空
       due_date DATE NOT NULL,                            -- 到期日期，不能为空
       is_returned BOOLEAN DEFAULT FALSE,                 -- 是否归还，默认为 FALSE
       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,  -- 外键，引用 users 表的 user_id，删除用户时自动删除借阅记录
       FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE   -- 外键，引用 books 表的 book_id，删除书籍时自动删除借阅记录
   );
   
   ```

   

4. wishlists and wishlist_items table

   -- 保每个用户只有一条心愿单记录，并且每条心愿单可以包含多本书，同时每本书只能出现在心愿单中一次，你可以做如下设计：

   **修改表设计：**
    我们需要两个表：

   - `wishlists`：每个用户有一个心愿单，记录用户的心愿单信息。
   - `wishlist_items`：每个心愿单可以包含多本书，记录每个心愿单中包含的书籍。

   ```mysql
   CREATE TABLE wishlists (
       wishlist_id INT AUTO_INCREMENT PRIMARY KEY,  -- 心愿单ID，自增
       user_id INT NOT NULL,                        -- 用户ID
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间，默认为当前时间
       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,  -- 外键，关联用户
       CONSTRAINT unique_user_wishlist UNIQUE (user_id)  -- 确保每个用户只有一个心愿单
   );
   
   ```

   ```mysql
   CREATE TABLE wishlist_items (
       wishlist_item_id INT AUTO_INCREMENT PRIMARY KEY,  -- 心愿单项ID，自增
       wishlist_id INT NOT NULL,                         -- 心愿单ID
       book_id INT NOT NULL,                             -- 书籍ID
       CONSTRAINT fk_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlists(wishlist_id) ON DELETE CASCADE,  -- 外键，关联心愿单
       CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,  -- 外键，关联书籍
       CONSTRAINT unique_wishlist_book UNIQUE (wishlist_id, book_id)  -- 确保每本书只能出现在心愿单中一次
   );
   
   ```

    

5. reviews

   ```mysql
   CREATE TABLE review_records (
       review_record_id INT AUTO_INCREMENT PRIMARY KEY,  -- 评价记录ID，自增，主键
       user_id INT,                                      -- 用户ID
       book_id INT,                                      -- 书籍ID
       rating INT CHECK (rating BETWEEN 1 AND 5),         -- 评分，限制为1到5
       comment TEXT,                                     -- 评论内容
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 创建时间，默认当前时间
       CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,  -- 外键，关联用户
       CONSTRAINT fk_review__book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE   -- 外键，关联书籍
   );
   
   ```

   

# Insert data

```mysql
INSERT INTO users (email, name, password_hash) VALUES
('alice@example.com', 'Alice Johnson', 'hashed_password_1'),
('bob@example.com', 'Bob Smith', 'hashed_password_2'),
('charlie@example.com', 'Charlie Brown', 'hashed_password_3'),
('diana@example.com', 'Diana Ross', 'hashed_password_4'),
('ella@example.com', 'Ella Fitzgerald', 'hashed_password_5'),
('frank@example.com', 'Frank Sinatra', 'hashed_password_6'),
('grace@example.com', 'Grace Kelly', 'hashed_password_7'),
('harry@example.com', 'Harry Styles', 'hashed_password_8'),
('isla@example.com', 'Isla Fisher', 'hashed_password_9'),
('jack@example.com', 'Jack Nicholson', 'hashed_password_10');




INSERT INTO books (title, author, genre, description, cover_image, available_copies) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Novel', 'A novel set in the Jazz Age, exploring themes of wealth, class, and love.', 'great_gatsby_cover.jpg', 10),
('1984', 'George Orwell', 'Dystopian', 'A dystopian novel about totalitarianism and surveillance.', '1984_cover.jpg', 8),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 'A story of racial injustice and childhood innocence in the American South.', 'mockingbird_cover.jpg', 12),
('Moby-Dick', 'Herman Melville', 'Adventure', 'The tale of Captain Ahab’s obsessive quest to kill the white whale, Moby Dick.', 'moby_dick_cover.jpg', 6),
('Pride and Prejudice', 'Jane Austen', 'Romance', 'A story of love, class, and society in early 19th-century England.', 'pride_prejudice_cover.jpg', 10),
('The Catcher in the Rye', 'J.D. Salinger', 'Fiction', 'The life of a disenchanted teenager, Holden Caulfield.', 'catcher_rye_cover.jpg', 7),
('Brave New World', 'Aldous Huxley', 'Dystopian', 'A utopian society with an authoritarian regime that suppresses individual freedom.', 'brave_new_world_cover.jpg', 9),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 'The story of Bilbo Baggins and his journey through Middle-earth.', 'hobbit_cover.jpg', 14),
('The Picture of Dorian Gray', 'Oscar Wilde', 'Philosophical', 'A man’s portrait ages while he remains young, a story of vanity and corruption.', 'dorian_gray_cover.jpg', 5),
('The Odyssey', 'Homer', 'Epic', 'The epic journey of Odysseus as he returns home after the Trojan War.', 'odyssey_cover.jpg', 6);



INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date, is_returned) VALUES
(1, 1, '2025-03-01', '2025-03-15', FALSE),
(2, 2, '2025-03-02', '2025-03-16', FALSE),
(3, 3, '2025-03-03', '2025-03-17', FALSE),
(4, 4, '2025-03-04', '2025-03-18', TRUE),
(5, 5, '2025-03-05', '2025-03-19', FALSE),
(6, 6, '2025-03-06', '2025-03-20', TRUE),
(7, 7, '2025-03-07', '2025-03-21', FALSE),
(8, 8, '2025-03-08', '2025-03-22', TRUE),
(9, 9, '2025-03-09', '2025-03-23', FALSE),
(10, 10, '2025-03-10', '2025-03-24', TRUE);




INSERT INTO wishlists (user_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);


INSERT INTO wishlist_items (wishlist_id, book_id) VALUES
(1, 1),  -- 用户 1 的心愿单中添加书籍 1
(1, 2),  -- 用户 1 的心愿单中添加书籍 2
(1, 3),  -- 用户 1 的心愿单中添加书籍 3

(2, 4),  -- 用户 2 的心愿单中添加书籍 4
(2, 5),  -- 用户 2 的心愿单中添加书籍 5
(2, 6),  -- 用户 2 的心愿单中添加书籍 6

(3, 7),  -- 用户 3 的心愿单中添加书籍 7
(3, 8),  -- 用户 3 的心愿单中添加书籍 8
(3, 9),  -- 用户 3 的心愿单中添加书籍 9

(4, 10), -- 用户 4 的心愿单中添加书籍 10
(4, 1),  -- 用户 4 的心愿单中添加书籍 1
(4, 2),  -- 用户 4 的心愿单中添加书籍 2

(5, 3),  -- 用户 5 的心愿单中添加书籍 3
(5, 4),  -- 用户 5 的心愿单中添加书籍 4
(5, 5),  -- 用户 5 的心愿单中添加书籍 5

(6, 6),  -- 用户 6 的心愿单中添加书籍 6
(6, 7),  -- 用户 6 的心愿单中添加书籍 7
(6, 8),  -- 用户 6 的心愿单中添加书籍 8

(7, 9),  -- 用户 7 的心愿单中添加书籍 9
(7, 10), -- 用户 7 的心愿单中添加书籍 10
(7, 1),  -- 用户 7 的心愿单中添加书籍 1

(8, 2),  -- 用户 8 的心愿单中添加书籍 2
(8, 3),  -- 用户 8 的心愿单中添加书籍 3
(8, 4),  -- 用户 8 的心愿单中添加书籍 4

(9, 5),  -- 用户 9 的心愿单中添加书籍 5
(9, 6),  -- 用户 9 的心愿单中添加书籍 6
(9, 7),  -- 用户 9 的心愿单中添加书籍 7

(10, 8), -- 用户 10 的心愿单中添加书籍 8
(10, 9), -- 用户 10 的心愿单中添加书籍 9
(10, 10);-- 用户 10 的心愿单中添加书籍 10



INSERT INTO review_records (user_id, book_id, rating, comment) VALUES
(1, 1, 5, 'An absolutely fantastic novel with timeless themes of love and loss.'),
(2, 2, 4, 'A chilling portrayal of a dystopian future, though slightly difficult to digest at times.'),
(3, 3, 5, 'A beautifully written story that tackles deep social issues with grace.'),
(4, 4, 3, 'A bit long-winded for my taste, but a classic nonetheless.'),
(5, 5, 4, 'An insightful and thought-provoking romance novel. I loved the characters!'),
(6, 6, 2, 'The protagonist was a bit too whiny for my liking, but it was an interesting read.'),
(7, 7, 5, 'A compelling and imaginative read, I couldn’t put it down!'),
(8, 8, 4, 'A timeless classic that explores profound philosophical ideas.'),
(9, 9, 5, 'A masterful blend of adventure and tragedy. It was a wonderful read.'),
(10, 10, 4, 'A great exploration of vanity and corruption, though it felt a bit slow at times.');







```

