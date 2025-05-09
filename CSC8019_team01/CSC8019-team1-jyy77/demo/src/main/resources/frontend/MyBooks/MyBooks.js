/**
 * MyBooks.js
 * Description: Handles dynamic display of a user's active loans, including return and review functionality.
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", function () {

  // ===== Dropdown Hover Behavior =====
  const dropdowns = document.querySelectorAll(".dropdown");

  dropdowns.forEach((dropdown) => {
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

  // ===== Token Validation & Borrowed Book Fetch =====

  /**
   * Checks if the JWT token has expired.
   * @param {string} token - JWT token stored in localStorage
   * @returns {boolean} true if expired
   */
  function isTokenExpired(token) {
    if (!token) return true;
    const payload = token.split(".")[1];
    const decoded = JSON.parse(atob(payload));
    const exp = decoded.exp;
    const now = Math.floor(Date.now() / 1000);
    return exp < now;
  }

  const token = localStorage.getItem("token");

  if (isTokenExpired(token)) {
    console.error("Token has expired. Please log in again.");
    window.location.href = "/login"; // Redirect to login
  } else {
    // Fetch active borrow records
    fetch("http://localhost:8080/borrow/history", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        const bookGrid = document.querySelector(".grid-book-box");
        bookGrid.innerHTML = "";

        // Filter for books that are still borrowed (not returned)
        const activeLoans = data.filter((record) => !record.isReturned);

        if (activeLoans.length === 0) {
          bookGrid.innerHTML = "<p>You have no active loans.</p>";
          return;
        }

        // Render book cards for each active loan
        activeLoans.forEach((record) => {
          const book = record.book;

          const bookCard = document.createElement("div");
          bookCard.className = "book";

          bookCard.innerHTML = `
            <div class="book-cover" style="background-image: url('${book.coverImage}')"></div>
            <div class="book-content">
              <h2 class="book-title truncate-2-lines">${book.title}</h2>
              <p class="book-author truncate-2-lines">${book.author}</p>
              <p>Borrowed: ${record.borrowDate}</p>
              <p>Due: ${record.dueDate}</p>
              <p>Status: Borrowed</p>
              <div class="review-button">
                <button onclick="storeBookIdAndRedirect(${book.bookId})">Write a Review</button>
              </div>
              <div class="loan-info">
                <button onclick="returnBook(${book.bookId})">Return</button>
              </div>
            </div>
          `;

          bookGrid.appendChild(bookCard);
        });
      })
      .catch((error) => {
        console.error("Error fetching borrow history:", error);
        const bookGrid = document.querySelector(".grid-book-box");
        bookGrid.innerHTML = "<p>Failed to load borrowed books.</p>";
      });
  }
});

// ===== Return Book Handler =====

/**
 * Sends request to return a borrowed book.
 * @param {number} bookId - ID of the book to return
 */
function returnBook(bookId) {
  const token = localStorage.getItem("token");

  fetch(`http://localhost:8080/borrow/return?bookId=${bookId}`, {
    method: "POST",
    headers: {
      Authorization: "Bearer " + token,
    },
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to return book");
      return res.text();
    })
    .then(() => {
      alert("✅ Book returned successfully!");
      location.reload(); // Refresh to show updated status
    })
    .catch((err) => alert("❌ Error: " + err.message));
}

/**
 * Stores the bookId in localStorage and redirects user to the review page.
 * @param {number} bookId - ID of the book to review
 */
function storeBookIdAndRedirect(bookId) {
  localStorage.setItem('reviewBookId', bookId);
  window.location.href = '../Reviews/reviews.html';
}
