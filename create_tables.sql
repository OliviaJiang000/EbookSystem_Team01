use EbookJyy;


CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,    -- 用户 ID，自增
    email VARCHAR(255) NOT NULL UNIQUE,         -- 用户邮箱，确保唯一
    name VARCHAR(255) NOT NULL,                 -- 用户姓名
    password_hash VARCHAR(255) NOT NULL         -- 密码哈希值
);


CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,  -- 书籍ID，自增，主键
    title VARCHAR(255) NOT NULL,              -- 书名，不能为空
    author VARCHAR(255) NOT NULL,             -- 作者，不能为空
    genre VARCHAR(100),                       -- 书籍类型
    description TEXT,                         -- 书籍描述
    cover_image VARCHAR(255),                 -- 书籍封面图，存储文件路径或URL
    available_copies INT NOT NULL DEFAULT 10    -- 可借书籍数量，默认值为10
);

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


CREATE TABLE wishlists (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,  -- 心愿单ID，自增
    user_id INT NOT NULL,                        -- 用户ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间，默认为当前时间
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,  -- 外键，关联用户
    CONSTRAINT unique_user_wishlist UNIQUE (user_id)  -- 确保每个用户只有一个心愿单
);

CREATE TABLE wishlist_items (
    wishlist_item_id INT AUTO_INCREMENT PRIMARY KEY,  -- 心愿单项ID，自增
    wishlist_id INT NOT NULL,                         -- 心愿单ID
    book_id INT NOT NULL,                             -- 书籍ID
    CONSTRAINT fk_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlists(wishlist_id) ON DELETE CASCADE,  -- 外键，关联心愿单
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,  -- 外键，关联书籍
    CONSTRAINT unique_wishlist_book UNIQUE (wishlist_id, book_id)  -- 确保每本书只能出现在心愿单中一次
);


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



