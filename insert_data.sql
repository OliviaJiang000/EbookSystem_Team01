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






