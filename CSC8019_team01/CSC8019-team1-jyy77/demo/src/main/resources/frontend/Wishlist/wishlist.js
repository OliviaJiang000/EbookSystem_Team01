/**
 * wishlist.js - Script for managing user's wishlist page
 * Authors: Amin, Amin Grace Brown, and Avin Jayatilake
 */

// -------- Wait for DOM to be fully loaded --------
document.addEventListener("DOMContentLoaded", function () { 
  // Dropdown hover fix
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

  // -------- Search Input Logic --------
  const searchInput = document.querySelector(".search-input");

  if (searchInput) {
    searchInput.addEventListener("keypress", function (e) {
      if (e.key === "Enter") {
        const value = searchInput.value.trim();
        if (value) {
          alert(`Search: ${value}`); // Display alert with search value
        }
      }
    });
  }

  // -------- Token Expiration Check Function --------
  function isTokenExpired(token) {
    if (!token) return true;
    const payload = token.split('.')[1];
    const decoded = JSON.parse(atob(payload));
    const exp = decoded.exp;
    const now = Math.floor(Date.now() / 1000);
    return exp < now;
  }

  // -------- Check if Token Exists and if it's Expired --------
  const token = localStorage.getItem('token');

  if (isTokenExpired(token)) {
    console.error('Token has expired. Please log in again.');
    window.location.href = '/login';
  } else {
    console.log('Token is valid. Proceeding with request...');

    // -------- Fetch Wishlist if Token is Valid --------
    fetch('http://localhost:8080/api/user/wishlist', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      },
    })
    .then(response => response.json())
    .then(data => {
      const wishlistGrid = document.getElementById('wishlist-grid');
      wishlistGrid.innerHTML = '';

      // -------- Display Books in Wishlist --------
      if (data && data.length > 0) {
        data.forEach(book => {
          const bookCard = document.createElement('div');
          bookCard.classList.add('book');

          bookCard.innerHTML = `
            <div class="book-cover" style="background-image: url('${book.coverImage}');"></div>
            <div class="book-content">
              <h2 class="book-title truncate-2-lines">${book.title}</h2>
              <p class="book-author truncate-2-lines">${book.author}</p>
              <div class="loan-info"><button>Borrow</button></div>
            </div>
          `;

          wishlistGrid.appendChild(bookCard);

          // -------- Borrow Button Handler --------
          const borrowBtn = bookCard.querySelector('.loan-info button');
          if (borrowBtn) {
            borrowBtn.addEventListener('click', function () {
              const token = localStorage.getItem('token');
              if (!token) {
                alert("You must be logged in to borrow books.");
                return;
              }

              fetch(`http://localhost:8080/borrow/borrow?bookId=${book.bookId}`, {
                method: 'POST',
                headers: {
                  'Authorization': `Bearer ${token}`
                }
              })
              .then(res => {
                if (!res.ok) {
                  return res.text().then(text => { throw new Error(text || 'Borrow failed'); });
                }
                return res.text();
              })
              .then(msg => {
                alert("Book borrowed successfully!");
              })
              .catch(err => {
                console.error("Error borrowing book:", err);
                alert("Failed to borrow: " + err.message);
              });
            });
          }
        });
      } else {
        wishlistGrid.innerHTML = '<p>Your wishlist is empty.</p>';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('wishlist-grid').innerHTML = '<p>Failed to load wishlist.</p>';
    });
  }
});
