/**
 * Book Details Page Script
 * Handles dynamic rendering of book details, authentication-based UI, borrowing, wishlist,
 * user navigation, and review fetching.
 *
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("token");
  const actionsContainer = document.getElementById("book-actions");

  // Display borrow and wishlist buttons if user is logged in, otherwise show login prompt
  if (actionsContainer) {
    if (token) {
      actionsContainer.innerHTML = `
        <button class="btn btn-borrow">Borrow</button>
        <button class="btn btn-wishlist">Add to Wishlist</button>
      `;

      const borrowBtn = actionsContainer.querySelector(".btn-borrow");
      const wishlistBtn = actionsContainer.querySelector(".btn-wishlist");

      // Borrow button logic
      borrowBtn.addEventListener("click", () => {
        fetch(`http://localhost:8080/borrow/borrow?bookId=${bookId}`, {
          method: "POST",
          headers: {
            "Authorization": "Bearer " + token
          }
        })
        .then(response => {
          if (!response.ok) throw new Error("Borrow failed");
          return response.text();
        })
        .then(() => alert("📚 Book borrowed successfully!"))
        .catch(err => alert("❌ Borrow failed: " + err.message));
      });

      // Wishlist button logic
      wishlistBtn.addEventListener("click", () => {
        fetch(`http://localhost:8080/api/user/wishlist/add?bookId=${bookId}`, {
          method: "POST",
          headers: {
            "Authorization": "Bearer " + token
          }
        })
        .then(response => {
          if (!response.ok) throw new Error("Add to wishlist failed");
          return response.text();
        })
        .then(() => alert("❤️ Book added to wishlist!"))
        .catch(err => alert("❌ Wishlist failed: " + err.message));
      });

    } else {
      actionsContainer.innerHTML = `
        <p class="login-prompt">
          Please sign in to add to your wishlist and borrow books.
        </p>
      `;
    }
  }

  // ========== Navigation Button Logic ==========
  window.addEventListener('load', function () {
    const loginButton = document.querySelector('.btn-login');
    const signUpButton = document.querySelector('.btn-signup');
    const signOutButton = document.getElementById('signout-btn');
    const settingButton = document.querySelector('.btn-settings');

    if (token) {
      loginButton.style.display = 'none';
      signUpButton.style.display = 'none';
      signOutButton.style.display = 'block';
    } else {
      loginButton.style.display = 'block';
      signUpButton.style.display = 'block';
      signOutButton.style.display = 'none';
      settingButton.style.display = 'none';

      loginButton.addEventListener('click', function (e) {
        e.preventDefault();
        window.location.href = '../Login/login.html';
      });

      signUpButton.addEventListener('click', function (e) {
        e.preventDefault();
        window.location.href = '../Register/register.html';
      });
    }

    // Logout logic
    signOutButton.addEventListener('click', function (e) {
      e.preventDefault();
      localStorage.removeItem('token');
      window.location.href = '../HomePage/HomePage/HomePage.html';
    });
  });

  // ========== Load Book Details ==========
  const urlParams = new URLSearchParams(window.location.search);
  const bookId = urlParams.get("bookId");

  if (bookId) {
    fetch(`http://localhost:8080/books/${bookId}`)
      .then(response => response.json())
      .then(data => {
        document.querySelector(".book-cover").src = data.coverImage;
        document.querySelector(".book-cover").alt = data.title;
        document.querySelector(".book-meta h1").textContent = data.title;
        document.querySelector(".author").textContent = "by " + data.author;
        document.querySelector(".category").textContent = "Category: " + data.genre;
        document.querySelector(".description").textContent = data.description;
      })
      .catch(error => {
        console.error("Error fetching book data:", error);
      });
  } else {
    console.error("No bookId provided in URL");
  }

  // ========== Home Link Navigation ==========
  document.getElementById("home-link").addEventListener("click", () => {
    window.location.href = "../HomePage/HomePage/HomePage.html";
  });

  // ========== Dropdown Hover Effects ==========
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

  // ========== Fetch and Display Reviews ==========
  if (bookId) {
    fetch(`http://localhost:8080/reviews/${bookId}`, {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
    .then(response => {
      if (!response.ok) throw new Error("Failed to fetch reviews");
      return response.json();
    })
    .then(reviews => {
      const container = document.getElementById("review-container");
      container.innerHTML = '';

      if (reviews.length === 0) {
        container.innerHTML = "<p>No reviews yet for this book.</p>";
        return;
      }

      reviews.forEach(review => {
        const div = document.createElement("div");
        div.className = "review";
        div.innerHTML = `<strong>User:</strong> “${review.comment}” - ${review.rating}★`;
        container.appendChild(div);
      });
    })
    .catch(err => {
      console.error("Error loading reviews:", err);
    });
  }
});
