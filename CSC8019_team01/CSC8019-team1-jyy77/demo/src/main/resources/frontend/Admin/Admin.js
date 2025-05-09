/**
 * Admin Panel JavaScript Functionality
 * Description: Handles book review moderation, CRUD operations for books and users,
 *              search, pagination, upload form handling, and section switching.
 * 
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

// ======================= Review Moderation =======================

let currentButton;
let actionType;

/**
 * Opens a confirmation popup to approve or dismiss a review.
 * @param {HTMLElement} button - The clicked button
 * @param {string} type - Either 'approve' or 'dismiss'
 */
function handleAction(button, type) {
    currentButton = button;
    actionType = type;

    const popupMessage = type === 'approve'
        ? 'Are you sure you want to approve this review?'
        : 'Are you sure you want to dismiss this review?';

    document.getElementById('popup-message').textContent = popupMessage;
    document.getElementById('popup').style.display = 'flex';
}

/**
 * Confirms and applies the review action (approve or dismiss).
 */
function confirmAction() {
    const statusCell = currentButton.closest('tr').querySelector('.review-status');

    if (actionType === 'approve') {
        statusCell.textContent = 'Approved';
        statusCell.classList.remove('dismissed');
        statusCell.classList.add('approved');
    } else if (actionType === 'dismiss') {
        statusCell.textContent = 'Dismissed';
        statusCell.classList.remove('approved');
        statusCell.classList.add('dismissed');
    }

    closePopup();
}

/**
 * Closes the confirmation popup.
 */
function closePopup() {
    document.getElementById('popup').style.display = 'none';
}

// ======================= Book Editing =======================

/**
 * Opens the edit popup and populates fields with the selected book.
 */
function editBook(book) {
    document.getElementById('editBookId').value = book.bookId;
    document.getElementById('editTitle').value = book.title;
    document.getElementById('editAuthor').value = book.author;
    document.getElementById('editGenre').value = book.genre;
    document.getElementById('editDescription').value = book.description;

    document.getElementById('editBookPopup').style.display = 'block';
}

/**
 * Closes the edit popup.
 */
function closeEditPopup() {
    document.getElementById('editBookPopup').style.display = 'none';
}

/**
 * Submits the updated book data to the backend.
 */
document.getElementById('editBookForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const bookId = document.getElementById('editBookId').value;
    const updatedBook = {
        title: document.getElementById('editTitle').value,
        author: document.getElementById('editAuthor').value,
        genre: document.getElementById('editGenre').value,
        description: document.getElementById('editDescription').value
    };

    fetch(`http://localhost:8080/books/${bookId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedBook)
    })
    .then(response => {
        if (!response.ok) throw new Error('Update failed');
        return response.json();
    })
    .then(data => {
        closeEditPopup();
        fetchBooks(); // Refresh book list
    })
    .catch(error => {
        console.error('Error updating book:', error);
        alert('Error updating book.');
    });
});

// ======================= Book Deletion =======================

/**
 * Deletes a book by its ID after confirmation.
 */
function deleteBook(bookId) {
    if (!confirm("Are you sure you want to delete this book?")) return;

    fetch(`http://localhost:8080/api/admin/books/${bookId}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(response => {
        if (!response.ok) throw new Error("Delete failed");
        alert("Book deleted successfully.");
        fetchBooks();
    })
    .catch(error => {
        console.error("Error deleting book:", error);
        alert("Failed to delete the book.");
    });
}

// ======================= Book Upload =======================

/**
 * Handles the submission of the book upload form.
 */
document.getElementById('uploadBookForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const title = document.getElementById('uploadTitle').value;
    const author = document.getElementById('uploadAuthor').value;
    const description = document.getElementById('uploadDescription').value;
    const genre = document.getElementById('uploadGenre').value;
    const imageFile = document.getElementById('uploadImage').files[0];

    const token = localStorage.getItem("token");
    if (!token) {
        alert('You must be logged in as an admin to upload a book.');
        return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('author', author);
    formData.append('description', description);
    formData.append('genre', genre);
    formData.append('image', imageFile);
    formData.append('coverImage', imageFile.name);

    fetch('http://localhost:8080/api/admin/books', {
        method: 'POST',
        headers: {
            "Authorization": "Bearer " + token
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Admin added book successfully!');
            fetchBooks();
        } else {
            alert(data.message);
        }
    })
    .catch(error => {
        console.error('Error uploading book:', error);
        alert('Error uploading book.');
    });
});

// ======================= Section Switching =======================

/**
 * Shows the selected admin panel section and hides others.
 */
function showSection(id) {
    document.querySelectorAll('.section').forEach(sec => sec.style.display = 'none');
    document.getElementById(id).style.display = 'block';
}

// ======================= User Management =======================

let users = [];
let currentPage = 1;
const rowsPerPage = 5;

/**
 * Fetches users and sets up pagination when DOM loads.
 */
document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");
    if (!token) return alert("Not logged in");

    document.querySelector(".logout").addEventListener("click", function () {
        localStorage.removeItem("token");
        window.location.href = "../Login/login.html";
    });

    fetch("http://localhost:8080/api/admin/users", {
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => res.json())
    .then(data => {
        users = data;
        displayUsers();
        setupPagination();
    });

    fetchBooks();
});

/**
 * Displays a paginated user table.
 */
function displayUsers() {
    const tbody = document.querySelector("#userTable tbody");
    tbody.innerHTML = "";
    const filteredUsers = getFilteredUsers();
    const start = (currentPage - 1) * rowsPerPage;
    const paginatedUsers = filteredUsers.slice(start, start + rowsPerPage);

    paginatedUsers.forEach(user => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td><button class="delete-btn">Delete</button></td>
        `;
        tr.querySelector(".delete-btn").addEventListener("click", () => deleteUser(user.id));
        tbody.appendChild(tr);
    });
}

/**
 * Deletes a user by ID.
 */
function deleteUser(id) {
    if (!confirm("Are you sure you want to delete this user?")) return;

    fetch(`http://localhost:8080/api/admin/users/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => {
        if (res.ok) {
            alert("User deleted.");
            users = users.filter(u => u.id !== id);
            currentPage = 1;
            displayUsers();
            setupPagination();
        } else {
            return res.text().then(msg => {
                throw new Error(msg || "Delete failed");
            });
        }
    })
    .catch(err => alert("Network error: " + err.message));
}

/**
 * Sets up pagination buttons based on the user count.
 */
function setupPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";
    const pageCount = Math.ceil(getFilteredUsers().length / rowsPerPage);

    for (let i = 1; i <= pageCount; i++) {
        const btn = document.createElement("button");
        btn.textContent = i;
        btn.onclick = () => {
            currentPage = i;
            displayUsers();
        };
        if (i === currentPage) btn.classList.add("active");
        pagination.appendChild(btn);
    }
}

/**
 * Filters users based on the search input.
 */
function filterUsers() {
    currentPage = 1;
    displayUsers();
    setupPagination();
}

/**
 * Gets the filtered list of users by username.
 */
function getFilteredUsers() {
    const query = document.getElementById("searchInput").value.toLowerCase();
    return users.filter(u => u.username.toLowerCase().includes(query));
}

// ======================= Book Display & Search =======================

const searchInput = document.getElementById("bookSearchInput");
const tbody = document.getElementById("bookTableBody");

/**
 * Fetches all books and renders them.
 */
function fetchBooks() {
    fetch('http://localhost:8080/books')
        .then(response => response.json())
        .then(data => renderBooks(data))
        .catch(error => console.error('Error fetching books:', error));
}

/**
 * Searches books by keyword.
 */
function searchBooks(keyword) {
    fetch(`http://localhost:8080/books/search?keyword=${encodeURIComponent(keyword)}`)
        .then(response => {
            if (!response.ok) throw new Error("Search request failed");
            return response.json();
        })
        .then(data => renderBooks(data))
        .catch(error => console.error("Error searching books:", error));
}

/**
 * Renders a list of book rows in the table.
 */
function renderBooks(books) {
    tbody.innerHTML = "";
    if (books.length === 0) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 2;
        td.textContent = "No books found.";
        tr.appendChild(td);
        tbody.appendChild(tr);
    } else {
        books.forEach(book => {
            const tr = document.createElement("tr");

            const titleTd = document.createElement("td");
            titleTd.textContent = book.title;
            tr.appendChild(titleTd);

            const actionsTd = document.createElement("td");
            const editBtn = document.createElement("button");
            editBtn.textContent = "Edit";
            editBtn.onclick = () => editBook(book);

            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "Delete";
            deleteBtn.onclick = () => deleteBook(book.bookId);

            actionsTd.appendChild(editBtn);
            actionsTd.appendChild(deleteBtn);
            tr.appendChild(actionsTd);

            tbody.appendChild(tr);
        });
    }
}

// ======================= Book Search Input with Debounce =======================

let debounceTimer;
searchInput.addEventListener("input", () => {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => {
        const keyword = searchInput.value.trim();
        if (keyword === "") {
            fetchBooks();
        } else {
            searchBooks(keyword);
        }
    }, 300);
});
