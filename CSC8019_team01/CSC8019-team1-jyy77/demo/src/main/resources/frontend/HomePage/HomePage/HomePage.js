/**
 * Home Page JavaScript for eBook Library
 * Handles authentication-based UI rendering, genre filtering, dynamic book loading with pagination,
 * search functionality, and dropdown interactivity.
 *
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem('token');
    const loginButton = document.querySelector('.btn-login');
    const signUpButton = document.querySelector('.btn-signup');
    const signOutButton = document.getElementById('signout-btn');
    const settingButton = document.querySelector('.btn-settings');

    // ===== Authentication UI handling =====
    if (token) {
        if (loginButton) loginButton.style.display = 'none';
        if (signUpButton) signUpButton.style.display = 'none';
        if (signOutButton) signOutButton.style.display = 'block';
    } else {
        if (loginButton) loginButton.style.display = 'block';
        if (signUpButton) signUpButton.style.display = 'block';
        if (signOutButton) signOutButton.style.display = 'none';
        if (settingButton) settingButton.style.display = 'none';

        // Redirect to login or signup
        loginButton?.addEventListener('click', (event) => {
            event.preventDefault();
            window.location.href = '../../Login/login.html';
        });
        signUpButton?.addEventListener('click', (event) => {
            event.preventDefault();
            window.location.href = '../../Register/register.html';
        });
    }

    // Sign out functionality
    signOutButton?.addEventListener('click', (event) => {
        event.preventDefault();
        localStorage.removeItem('token');
        window.location.href = '../../HomePage/HomePage/HomePage.html';
    });

    // ===== Book loading and pagination logic =====
    const grid = document.getElementById("wishlist-grid");
    const loadMoreButton = document.getElementById("load-more");
    let currentPage = 1;
    const pageSize = 21;
    let selectedGenre = null;

    /**
     * Loads books for the current page and genre filter.
     */
    function loadBooks(page) {
        const url = selectedGenre
            ? `http://localhost:8080/books/genre/${encodeURIComponent(selectedGenre)}?page=${page}&size=${pageSize}`
            : `http://localhost:8080/books?page=${page}&size=${pageSize}`;

        fetch(url)
            .then(res => res.json())
            .then(books => {
                if (!Array.isArray(books)) {
                    console.error("Invalid data format:", books);
                    return;
                }

                books.forEach(book => {
                    const bookDiv = document.createElement("div");
                    bookDiv.classList.add("book");
                    bookDiv.style.cursor = "pointer";
                    bookDiv.addEventListener("click", () => {
                        window.location.href = `../../BookDetails/bookDetails.html?bookId=${book.bookId}`;
                    });

                    bookDiv.innerHTML = `
                        <div class="book-cover">
                            <img src="${book.coverImage}" alt="${book.title}" />
                        </div>
                        <div class="book-content">
                            <h2 class="book-title truncate-2-lines">${book.title}</h2>
                            <h3 class="book-author truncate-2-lines">${book.author}</h3>
                            <p class="book-category">Category: ${book.genre}</p>
                            <p class="book-description truncate-8-lines">${book.description}</p>
                        </div>
                    `;
                    grid.appendChild(bookDiv);
                });
            })
            .catch(error => {
                console.error("Error loading books:", error);
            });
    }

    // Initial book load
    loadBooks(currentPage);

    // Load more books when pagination button clicked
    loadMoreButton?.addEventListener("click", () => {
        currentPage++;
        loadBooks(currentPage);
    });

    // ===== Genre Dropdown Handling =====
    const dropdownMenu = document.getElementById('genre-dropdown-menu');
    fetch('http://localhost:8080/books/genres')
        .then(response => response.json())
        .then(genres => {
            dropdownMenu.innerHTML = '';

            genres.forEach(genre => {
                const item = document.createElement('a');
                item.href = "#";
                item.className = 'dropdown-item';
                item.textContent = genre;

                // Genre click logic
                item.addEventListener("click", (e) => {
                    e.preventDefault();
                    selectedGenre = genre;
                    currentPage = 1;
                    grid.innerHTML = "";
                    document.getElementById("genre-title").textContent = ` ${genre}`;
                    loadBooks(currentPage);
                    dropdownMenu.classList.remove("show");
                });

                dropdownMenu.appendChild(item);
            });

            // Option to reset to all genres
            const clearItem = document.createElement('a');
            clearItem.href = "#";
            clearItem.className = 'dropdown-item';
            clearItem.textContent = 'All Genres';
            clearItem.addEventListener("click", (e) => {
                e.preventDefault();
                selectedGenre = null;
                currentPage = 1;
                grid.innerHTML = "";
                document.getElementById("genre-title").textContent = "";
                loadBooks(currentPage);
            });
            dropdownMenu.appendChild(clearItem);
        })
        .catch(error => {
            console.error('Failed to fetch genres:', error);
        });

    // ===== Dropdown Hover Interactions =====
    const dropdowns = document.querySelectorAll(".dropdown");
    dropdowns.forEach(dropdown => {
        const toggle = dropdown.querySelector(".dropdown-toggle");
        const menu = dropdown.querySelector(".dropdown-menu");
        let hideTimeout;

        if (toggle && menu) {
            dropdown.addEventListener("mouseenter", () => {
                clearTimeout(hideTimeout);
                menu.classList.add("show");
            });

            dropdown.addEventListener("mouseleave", () => {
                hideTimeout = setTimeout(() => {
                    menu.classList.remove("show");
                }, 300);
            });
        }
    });

    // ===== Home Link resets filters and reloads books =====
    const homeLink = document.querySelector('.navbar-nav .nav-link');
    homeLink?.addEventListener('click', (e) => {
        e.preventDefault();
        selectedGenre = null;
        currentPage = 1;
        grid.innerHTML = "";
        document.getElementById("genre-title").textContent = "";
        loadBooks(currentPage);
    });

    // ===== Search Functionality =====
    const searchInput = document.querySelector(".search-input");
    if (searchInput) {
        searchInput.addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                const value = searchInput.value.trim();
                if (value) {
                    grid.innerHTML = "";
                    fetch(`http://localhost:8080/books/search?keyword=${encodeURIComponent(value)}`)
                        .then((response) => {
                            if (!response.ok) throw new Error("Search failed.");
                            return response.json();
                        })
                        .then((books) => {
                            if (!Array.isArray(books)) {
                                console.error("Unexpected search result format:", books);
                                return;
                            }

                            if (books.length === 0) {
                                grid.innerHTML = "<p>No books found for that search.</p>";
                                return;
                            }

                            books.forEach(book => {
                                const bookDiv = document.createElement("div");
                                bookDiv.classList.add("book");

                                bookDiv.innerHTML = `
                                    <div class="book-cover">
                                        <img src="${book.coverImage}" alt="${book.title}" />
                                    </div>
                                    <div class="book-content">
                                        <h2 class="book-title truncate-2-lines">${book.title}</h2>
                                        <h3 class="book-author truncate-2-lines">${book.author}</h3>
                                        <p class="book-category">Category: ${book.genre}</p>
                                        <p class="book-description truncate-8-lines">${book.description}</p>
                                    </div>
                                `;
                                grid.appendChild(bookDiv);
                            });
                        })
                        .catch((error) => {
                            console.error("Search request failed:", error);
                            grid.innerHTML = "<p>Error fetching search results.</p>";
                        });
                }
            }
        });
    }
});
